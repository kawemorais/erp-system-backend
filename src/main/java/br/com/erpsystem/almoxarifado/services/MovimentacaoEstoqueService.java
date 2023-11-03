package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.movimentacaoEstoqueDTO.MovimentacaoEstoqueResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.MovimentacaoEstoque;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.enums.TipoMovimentacaoEstoque;
import br.com.erpsystem.almoxarifado.repositories.MovimentacaoEstoqueRepository;
import br.com.erpsystem.pessoa.models.Funcionario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final ProdutoService produtoService;
    private final ModelMapper mapper;


    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, ProdutoService produtoService, ModelMapper mapper) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.produtoService = produtoService;
        this.mapper = mapper;
    }


    public List<MovimentacaoEstoqueResponseDTO> listarTodasMovimentacaoEstoque(){
        return movimentacaoEstoqueRepository.findAll().stream()
                .map(movimentacaoEstoque -> mapper.map(movimentacaoEstoque, MovimentacaoEstoqueResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<MovimentacaoEstoqueResponseDTO> listarMovimentacaoEstoquePorProduto(Long produtoId){

        Produto produto = produtoService.retornaProdutoSeExistente(produtoId);

        return movimentacaoEstoqueRepository.findAllByProduto(produto).stream()
                .map(movimentacaoEstoque -> mapper.map(movimentacaoEstoque, MovimentacaoEstoqueResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void salvarMovimentacaoEstoque(Produto produto, Almoxarifado almoxarifado, Double quantidade,
                                          TipoMovimentacaoEstoque tipoMovimentacao, String observacao, Optional<Funcionario> funcionario){

        String nomePessoa;
        Funcionario funcionarioObject = null;

        if (funcionario.isPresent()){
            nomePessoa = funcionario.get().getNome();
            funcionarioObject = funcionario.get();
        } else {
            nomePessoa = "SISTEMA";
        }

        MovimentacaoEstoque movimentacaoEstoque = MovimentacaoEstoque.builder()
                .produto(produto)
                .almoxarifado(almoxarifado)
                .quantidade(quantidade)
                .tipoMovimentacao(tipoMovimentacao)
                .observacao(observacao)
                .nomePessoa(nomePessoa)
                .funcionario(funcionarioObject)
                .dataHoraMovimentacao(LocalDateTime.now())
                .build();

        movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }
}
