package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.fichaTecnicaDTO.FichaTecnicaRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.fichaTecnicaDTO.FichaTecnicaResponseDTO;
import br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO.ItemFichaTecnicaRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO.ItemFichaTecnicaResponseDTO;
import br.com.erpsystem.almoxarifado.models.FichaTecnica;
import br.com.erpsystem.almoxarifado.models.ItemFichaTecnica;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.repositories.FichaTecnicaRepository;
import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ProdutoService produtoService;
    private final ItemFichaTecnicaService itemFichaTecnicaService;
    private final FuncionarioRepository funcionarioRepository;

    private final ModelMapper mapper;

    public FichaTecnicaService(FichaTecnicaRepository fichaTecnicaRepository, ProdutoService produtoService, ItemFichaTecnicaService itemFichaTecnicaService, FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.produtoService = produtoService;
        this.itemFichaTecnicaService = itemFichaTecnicaService;
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public FichaTecnicaResponseDTO listarFichaTecnicaPorId(Long id){
        FichaTecnica fichaTecnica = retornaFichaTecnicaSeExistente(id);

        return mapper.map(fichaTecnica, FichaTecnicaResponseDTO.class);
    }

    public FichaTecnicaResponseDTO criarFichaTecnica(FichaTecnicaRequestDTO fichaTecnicaRequest){

        Produto produto = produtoService.retornaProdutoSeExistente(fichaTecnicaRequest.getFkProduto());

        if (produto.getFichaTecnica() != null){
            throw new ExcecaoSolicitacaoInvalida("Este produto já tem uma ficha técnica");
        }

        Funcionario funcionario = retornaFuncionarioSeExistente(fichaTecnicaRequest.getFkPessoaCriacao());

        FichaTecnica fichaTecnicaCriada = criarFichaTecnicaVazia(fichaTecnicaRequest.getNome(), funcionario);

        produto.setFichaTecnica(fichaTecnicaCriada);

        produtoService.salvarFichaTecnicaProduto(produto);

        List<ItemFichaTecnicaRequestDTO> itensFichaTecnicaRequest = fichaTecnicaRequest.getItensFichaTecnica();

        if (itensFichaTecnicaRequest.size() > 0){

            List<ItemFichaTecnicaResponseDTO> itensFichaTecnicaCriado = new ArrayList<>();

            for (ItemFichaTecnicaRequestDTO item : itensFichaTecnicaRequest){
                item.setFkFichaTecnica(fichaTecnicaCriada.getId());
                ItemFichaTecnicaResponseDTO itemFichaTecnicaResponseDTO = salvarItemFichaTecnica(item);
                itensFichaTecnicaCriado.add(itemFichaTecnicaResponseDTO);
            }

            List<ItemFichaTecnica> listaItemFichaTecnica = itensFichaTecnicaCriado.stream()
                    .map(item -> mapper.map(item, ItemFichaTecnica.class))
                    .collect(Collectors.toList());


            BigDecimal valorTotalFichaTecnica = calculaValorTotalFichaTecnica(listaItemFichaTecnica);

            fichaTecnicaCriada.setItensFichaTecnica(listaItemFichaTecnica);
            fichaTecnicaCriada.setCustoTotal(valorTotalFichaTecnica);

        }
        fichaTecnicaRepository.save(fichaTecnicaCriada);
        return mapper.map(fichaTecnicaCriada, FichaTecnicaResponseDTO.class);
    }

    private FichaTecnica criarFichaTecnicaVazia(String nome, Funcionario funcionario) {
        FichaTecnica fichaTecnica = FichaTecnica.builder()
                .nome(nome)
                .custoTotal(BigDecimal.ZERO)
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .pessoaCriacao(funcionario)
                .build();

        return fichaTecnicaRepository.saveAndFlush(fichaTecnica);
    }


    private BigDecimal calculaValorTotalFichaTecnica(List<ItemFichaTecnica> itensFichaTecnica){

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemFichaTecnica itemFichaTecnica : itensFichaTecnica){
            valorTotal = valorTotal.add(itemFichaTecnica.getSubTotal());
        }

        return valorTotal;
    }

    private ItemFichaTecnicaResponseDTO salvarItemFichaTecnica(ItemFichaTecnicaRequestDTO itemFichaTecnicaRequest){
        return itemFichaTecnicaService.salvarItemFichaTecnica(itemFichaTecnicaRequest);
    }

    private FichaTecnica retornaFichaTecnicaSeExistente(Long id){
        return fichaTecnicaRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Ficha tecnica não encontrada"));
    }

    private Funcionario retornaFuncionarioSeExistente(Long id){
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Funcionario não encontrado"));
    }
}
