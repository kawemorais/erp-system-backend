package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.almoxarifado.repositories.CategoriaProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.UnidadeRepository;
import br.com.erpsystem.pessoa.models.Fornecedor;
import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.pessoa.repositories.FornecedorRepository;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import br.com.erpsystem.sistema.models.enums.StatusSistema;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;
    private final CategoriaProdutoRepository categoriaProdutoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ModelMapper mapper;

    @Autowired
    @Lazy
    private EstoqueProdutoService estoqueProdutoService;


    public ProdutoService(ProdutoRepository produtoRepository, UnidadeRepository unidadeRepository, CategoriaProdutoRepository categoriaProdutoRepository, FornecedorRepository fornecedorRepository, FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
        this.categoriaProdutoRepository = categoriaProdutoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public List<ProdutoResponseDTO> listarTodosProdutos(){
        int statusId = StatusSistema.NORMAL.getStatusId();
        return produtoRepository.findAllByStatusEquals(statusId).stream()
                .map(produto -> mapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    public ProdutoResponseDTO listarProdutoPorId(Long id){
        Produto produto = retornaProdutoSeExistente(id);

        return mapper.map(produto, ProdutoResponseDTO.class);
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequest){
        verificaCodigoProdutoExistente(produtoRequest.getCodigo());

        Unidade unidade = retornaUnidadeSeExistente(produtoRequest.getFkUnidade());

        CategoriaProduto categoriaProduto = retornaCategoriaProdutoSeExistente(produtoRequest.getFkCategoriaProduto());

        Funcionario funcionario = retornaFuncionarioSeExistente(produtoRequest.getFkPessoaCriacao());

        Produto novoProduto = Produto.builder()
                .codigo(produtoRequest.getCodigo())
                .nome(produtoRequest.getNome())
                .unidade(unidade)
                .categoriaProduto(categoriaProduto)
                .descricao(produtoRequest.getDescricao())
                .peso(produtoRequest.getPeso())
                .dataCriacao((LocalDateTime.now()))
                .dataAlteracao((LocalDateTime.now()))
                .pessoaCriacao(funcionario)
                .isAtivo(Boolean.TRUE)
                .precoUltimaCompra(BigDecimal.ZERO)
                .custoProduto(BigDecimal.ZERO)
                .status(StatusSistema.NORMAL.getStatusId())
                .build();

        if(produtoRequest.getFkFornecedor() != null) {
            Fornecedor fornecedor = retornaFornecedorSeExistente(produtoRequest.getFkFornecedor());
            novoProduto.setFornecedor(fornecedor);
        }

        Produto produtoSalvo = produtoRepository.save(novoProduto);

        return mapper.map(produtoSalvo, ProdutoResponseDTO.class);

    }

    public List<ProdutoResponseDTO> criarProdutosPorImportacao(List<Produto> produtos){
        return produtos.stream()
                .map(produto -> {
                    Produto produtoSalvo = produtoRepository.save(produto);
                    return mapper.map(produtoSalvo, ProdutoResponseDTO.class);
                })
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO alterarProdutoPorId(Long id, ProdutoRequestDTO produtoRequest){
        Produto produto = retornaProdutoSeExistente(id);

        if (!Objects.equals(produtoRequest.getCodigo(), produto.getCodigo())) {
            verificaCodigoProdutoExistente(produtoRequest.getCodigo());
        }

        Unidade unidade = retornaUnidadeSeExistente(produtoRequest.getFkUnidade());

        CategoriaProduto categoriaProduto = retornaCategoriaProdutoSeExistente(produtoRequest.getFkCategoriaProduto());


        produto.setCodigo(produtoRequest.getCodigo());
        produto.setNome(produtoRequest.getNome());
        produto.setUnidade(unidade);
        produto.setCategoriaProduto(categoriaProduto);

        produto.setDescricao(produtoRequest.getDescricao());
        produto.setPeso(produtoRequest.getPeso());
        produto.setDataAlteracao(LocalDateTime.now());
        produto.setIsAtivo(produtoRequest.getIsAtivo());

        if(produtoRequest.getFkFornecedor() != null) {
            Fornecedor fornecedor = retornaFornecedorSeExistente(produtoRequest.getFkFornecedor());
            produto.setFornecedor(fornecedor);
        }

        Produto produtoSalvo = produtoRepository.save(produto);

        return mapper.map(produtoSalvo, ProdutoResponseDTO.class);
    }

    public void salvarFichaTecnicaProduto(Produto produto){
        produtoRepository.save(produto);
    }

    public void deletarProdutoPorId(Long id){
        Produto produto = retornaProdutoSeExistente(id);

        if(verificaSeProdutoTemEstoque(produto.getId())){
            throw new ExcecaoSolicitacaoInvalida("Este produto contém estoque. Operação não pode ser finalizada!");
        }

        produto.setStatus(StatusSistema.EXCLUIDO.getStatusId());
        produtoRepository.save(produto);
    }

    protected Produto retornaProdutoSeExistente(Long id){
        return produtoRepository.findByIdAndStatusEquals(id, StatusSistema.NORMAL.getStatusId())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Produto não encontrado"));
    }

    private Unidade retornaUnidadeSeExistente(Long id){
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Unidade não encontrada"));
    }

    private CategoriaProduto retornaCategoriaProdutoSeExistente(Long id){
        return categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Categoria produto não encontrado"));
    }

    private Fornecedor retornaFornecedorSeExistente(Long id){
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Fornecedor não encontrado"));
    }

    protected Funcionario retornaFuncionarioSeExistente(Long id){
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Funcionario não encontrado"));
    }

    protected void verificaCodigoProdutoExistente(String codigo){
        if (produtoRepository.findByCodigo(codigo).isPresent()) {
            throw new ExcecaoSolicitacaoInvalida("Ja existe produto com este codigo");
        }
    }

    private boolean verificaSeProdutoTemEstoque(Long id){
       return estoqueProdutoService.listarTodosEstoquesPorParametro("produto", id).size() > 0;
    }

    public void atualizaPrecoCompraProduto(Long id, BigDecimal valorUnitarioCompra) {
        Produto produto = retornaProdutoSeExistente(id);
        produto.setPrecoUltimaCompra(valorUnitarioCompra);

        produtoRepository.save(produto);
    }

    public void atualizaValorCustoProduto(Long id){
        Produto produto = retornaProdutoSeExistente(id);

        BigDecimal valorCustoProduto = BigDecimal.ZERO;

        if (Optional.ofNullable(produto.getFichaTecnica()).isPresent()){
            valorCustoProduto = produto.getFichaTecnica().getCustoTotal();
        } else {
            List<EstoqueProduto> estoques = produto.getEstoques();

            if(!estoques.isEmpty()){
                BigDecimal valorAcumuladoEstoque = BigDecimal.ZERO;

                for (EstoqueProduto estoque : estoques){
                    valorAcumuladoEstoque = valorAcumuladoEstoque.add(estoque.getValorUnitarioProdutoEstoque());
                }

                valorCustoProduto = valorAcumuladoEstoque.divide(BigDecimal.valueOf(estoques.size()), RoundingMode.HALF_EVEN);
            }
        }

        produto.setCustoProduto(valorCustoProduto);
        produtoRepository.save(produto);
    }
}
