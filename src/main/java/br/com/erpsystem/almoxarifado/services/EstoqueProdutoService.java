package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoAddQuantidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoAtualizarRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.enums.TipoMovimentacaoEstoque;
import br.com.erpsystem.almoxarifado.repositories.AlmoxarifadoRepository;
import br.com.erpsystem.almoxarifado.repositories.EstoqueProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueProdutoService {

    private final EstoqueProdutoRepository estoqueProdutoRepository;
    private final AlmoxarifadoRepository almoxarifadoRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper mapper;
    private final ProdutoService produtoService;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public EstoqueProdutoService(EstoqueProdutoRepository estoqueProdutoRepository, AlmoxarifadoRepository almoxarifadoRepository, ProdutoRepository produtoRepository, ModelMapper mapper, ProdutoService produtoService, MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.estoqueProdutoRepository = estoqueProdutoRepository;
        this.almoxarifadoRepository = almoxarifadoRepository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
        this.produtoService = produtoService;
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }


    public List<EstoqueProdutoResponseDTO> listarTodosEstoquesPorParametro(String parametro, Long fkParametro){

        if(!parametro.equalsIgnoreCase("almoxarifado") && !parametro.equalsIgnoreCase("produto")){
            throw new ExcecaoSolicitacaoInvalida("Parametro inválido");
        }

        if(parametro.equalsIgnoreCase("almoxarifado")){
            Almoxarifado almoxarifado = retornaAlmoxarifadoSeExistente(fkParametro);

            return estoqueProdutoRepository.findAllByAlmoxarifado(almoxarifado).stream()
                    .map(estoqueProduto -> mapper.map(estoqueProduto, EstoqueProdutoResponseDTO.class))
                    .toList();
        }

        if(parametro.equalsIgnoreCase("produto")){
            Produto produto = retornaProdutoSeExistente(fkParametro);

            return estoqueProdutoRepository.findAllByProduto(produto).stream()
                    .map(estoqueProduto -> mapper.map(estoqueProduto, EstoqueProdutoResponseDTO.class))
                    .toList();
        }

        return new ArrayList<>();
    }

    public EstoqueProdutoResponseDTO buscarEstoqueProdutoPorId(Long id){
        EstoqueProduto estoqueProduto = retornaEstoqueProdutoSeIdExistente(id);

        return mapper.map(estoqueProduto, EstoqueProdutoResponseDTO.class);
    }

    public EstoqueProdutoResponseDTO criarNovoEstoqueProduto(EstoqueProdutoRequestDTO estoqueProdutoRequest){

        validaRequisicaoEstoque(estoqueProdutoRequest.getFkAlmoxarifado(), estoqueProdutoRequest.getFkProduto());

        Double quantidade = estoqueProdutoRequest.getQuantidade();
        BigDecimal valorUnitario = estoqueProdutoRequest.getValorUnitarioCompra();

        Produto produto = retornaProdutoSeExistente(estoqueProdutoRequest.getFkProduto());

        EstoqueProduto estoqueProduto = EstoqueProduto.builder()
                .produto(produto)
                .almoxarifado(retornaAlmoxarifadoSeExistente(estoqueProdutoRequest.getFkAlmoxarifado()))
                .quantidade(quantidade)
                .valorUnitarioProdutoEstoque(valorUnitario)
                .valorTotalProdutoEstoque(calculaValorTotalEstoque(quantidade, valorUnitario))
                .estoqueMinimo(estoqueProdutoRequest.getEstoqueMinimo())
                .estoqueMaximo(estoqueProdutoRequest.getEstoqueMaximo())
                .estoquePontoPedido(estoqueProdutoRequest.getEstoquePontoPedido())
                .locCorredor(estoqueProdutoRequest.getLocCorredor())
                .locPrateleira(estoqueProdutoRequest.getLocPrateleira())
                .locBox(estoqueProdutoRequest.getLocBox())
                .build();

        EstoqueProduto estoqueProdutoSalvo = estoqueProdutoRepository.save(estoqueProduto);

        produtoService.atualizaPrecoCompraProduto(produto.getId(), estoqueProdutoRequest.getValorUnitarioCompra());
        produtoService.atualizaValorCustoProduto(produto.getId());

        movimentacaoEstoqueService.salvarMovimentacaoEstoque(estoqueProduto.getProduto(), estoqueProduto.getAlmoxarifado(),
                estoqueProdutoRequest.getQuantidade(), TipoMovimentacaoEstoque.ENTRADA, "",
                Optional.empty());

        return mapper.map(estoqueProdutoSalvo, EstoqueProdutoResponseDTO.class);

    }

    public EstoqueProdutoResponseDTO atualizarEstoqueProdutoPorId(Long id,
                                                                  EstoqueProdutoAtualizarRequestDTO estoqueProdutoRequest){

        EstoqueProduto estoqueProduto = retornaEstoqueProdutoSeIdExistente(id);

        BigDecimal valorTotalEmEstoque = calculaValorTotalEstoque(estoqueProdutoRequest.getQuantidade(),
                                                                    estoqueProdutoRequest.getValorUnitarioCompra());

        estoqueProduto.setQuantidade(estoqueProdutoRequest.getQuantidade());
        estoqueProduto.setValorUnitarioProdutoEstoque(estoqueProdutoRequest.getValorUnitarioCompra());
        estoqueProduto.setValorTotalProdutoEstoque(valorTotalEmEstoque);

        estoqueProduto.setEstoqueMinimo(estoqueProdutoRequest.getEstoqueMinimo());
        estoqueProduto.setEstoqueMaximo(estoqueProdutoRequest.getEstoqueMaximo());
        estoqueProduto.setEstoquePontoPedido(estoqueProdutoRequest.getEstoquePontoPedido());

        estoqueProduto.setLocCorredor(estoqueProdutoRequest.getLocCorredor());
        estoqueProduto.setLocPrateleira(estoqueProdutoRequest.getLocPrateleira());
        estoqueProduto.setLocBox(estoqueProdutoRequest.getLocBox());

        EstoqueProduto estoqueSalvo = estoqueProdutoRepository.save(estoqueProduto);

        produtoService.atualizaValorCustoProduto(estoqueSalvo.getProduto().getId());

        movimentacaoEstoqueService.salvarMovimentacaoEstoque(estoqueProduto.getProduto(), estoqueProduto.getAlmoxarifado(),
                estoqueProdutoRequest.getQuantidade(), TipoMovimentacaoEstoque.AJUSTE, estoqueProdutoRequest.getObservacao(),
                Optional.empty());

        return mapper.map(estoqueSalvo, EstoqueProdutoResponseDTO.class);

    }

    public EstoqueProdutoResponseDTO adicionarQuantidadeEstoque(Long id,
                                                                EstoqueProdutoAddQuantidadeRequestDTO estoqueProdutoRequest){

        EstoqueProduto estoqueProduto = retornaEstoqueProdutoSeIdExistente(id);

        BigDecimal valorTotalProdutoEstoqueAtual = estoqueProduto.getValorTotalProdutoEstoque();
        BigDecimal valorUnitarioCompraRequest = estoqueProdutoRequest.getValorUnitarioCompra();
        Double quantidadeRequest = estoqueProdutoRequest.getQuantidade();

        Double novaQuantidadeProduto = estoqueProduto.getQuantidade() + quantidadeRequest;

        BigDecimal novoValorUnitario = calculaValorUnitarioProdutoEmEstoque(valorTotalProdutoEstoqueAtual, novaQuantidadeProduto);

        BigDecimal novoValorTotalProdutoEmEstoque = calculaValorTotalProdutoEmEstoque(valorTotalProdutoEstoqueAtual,
                valorUnitarioCompraRequest, quantidadeRequest);


        estoqueProduto.setQuantidade(novaQuantidadeProduto);
        estoqueProduto.setValorUnitarioProdutoEstoque(novoValorUnitario);
        estoqueProduto.setValorTotalProdutoEstoque(novoValorTotalProdutoEmEstoque);

        EstoqueProduto estoqueSalvo = estoqueProdutoRepository.save(estoqueProduto);

        Produto produto = estoqueSalvo.getProduto();

        movimentacaoEstoqueService.salvarMovimentacaoEstoque(estoqueProduto.getProduto(), estoqueProduto.getAlmoxarifado(),
                estoqueProdutoRequest.getQuantidade(), TipoMovimentacaoEstoque.ENTRADA, "",
                Optional.empty());

        produtoService.atualizaPrecoCompraProduto(produto.getId(), estoqueProdutoRequest.getValorUnitarioCompra());
        produtoService.atualizaValorCustoProduto(produto.getId());

        return mapper.map(estoqueSalvo, EstoqueProdutoResponseDTO.class);

    }

    public void deletarEstoqueProdutoPorId(Long id){
        EstoqueProduto estoqueProduto = retornaEstoqueProdutoSeIdExistente(id);
        estoqueProdutoRepository.delete(estoqueProduto);
    }

    private Almoxarifado retornaAlmoxarifadoSeExistente(Long fkAlmoxarifado){
        return almoxarifadoRepository.findById(fkAlmoxarifado)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Almoxarifado invalido"));
    }

    private Produto retornaProdutoSeExistente(Long fkProduto){
        return produtoRepository.findById(fkProduto)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Produto invalido"));
    }

    private EstoqueProduto retornaEstoqueProdutoSeIdExistente(Long id){
        return estoqueProdutoRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Estoque produto não encontrado"));
    }

    private void validaRequisicaoEstoque(Long fkAlmoxarifado, Long fkProduto){

        Almoxarifado almoxarifado = retornaAlmoxarifadoSeExistente(fkAlmoxarifado);

        Produto produto = retornaProdutoSeExistente(fkProduto);

        if(estoqueProdutoRepository.findByAlmoxarifadoAndProduto(almoxarifado, produto).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Ja existe estoque do produto "
                    + produto.getNome() + " no almoxarifado "
                    + almoxarifado.getNome());
        }
    }

    private BigDecimal calculaValorTotalEstoque(Double quantidade, BigDecimal valorUnitario){
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    private BigDecimal calculaValorTotalProdutoEmEstoque(BigDecimal valorTotalAtual, BigDecimal precoUltimaCompra,
                                                         Double quantidade){

        BigDecimal valorTotalUltimaCompra = precoUltimaCompra.multiply(BigDecimal.valueOf(quantidade));

        return valorTotalAtual.add(valorTotalUltimaCompra);
    }

    private BigDecimal calculaValorUnitarioProdutoEmEstoque(BigDecimal valorTotalAtual, Double novaQuantidade){
        return valorTotalAtual.divide(BigDecimal.valueOf(novaQuantidade), RoundingMode.HALF_EVEN);
    }

}
