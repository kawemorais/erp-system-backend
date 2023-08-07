package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.repositories.CategoriaProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaProdutoService {

    private final CategoriaProdutoRepository categoriaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper mapper;

    public CategoriaProdutoService(CategoriaProdutoRepository categoriaProdutoRepository, ProdutoRepository produtoRepository, ModelMapper mapper) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }


    public List<CategoriaProdutoResponseDTO> listarTodasCategoriasProduto(){
        return categoriaProdutoRepository.findAll()
                .stream()
                .map(categoriaProduto -> mapper.map(categoriaProduto, CategoriaProdutoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CategoriaProdutoResponseDTO listarCategoriaProdutoPorId(Long id){
        CategoriaProduto categoriaProduto = retornaCategoriaProdutoSeExistente(id);

        return mapper.map(categoriaProduto, CategoriaProdutoResponseDTO.class);
    }

    public CategoriaProdutoResponseDTO criarCategoriaProduto(CategoriaProdutoRequestDTO categoriaProdutoRequest){

        if (categoriaProdutoRepository.findByCodigo(categoriaProdutoRequest.getCodigo()).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Ja existe categoria produto com este codigo");
        }

        CategoriaProduto categoriaProdutoSalva = categoriaProdutoRepository.save(mapper.map(categoriaProdutoRequest, CategoriaProduto.class));

        return mapper.map(categoriaProdutoSalva, CategoriaProdutoResponseDTO.class);
    }

    public CategoriaProdutoResponseDTO alterarCategoriaProdutoPorId(Long id, CategoriaProdutoRequestDTO categoriaProdutoRequest){

        if (categoriaProdutoRepository.findByCodigo(categoriaProdutoRequest.getCodigo()).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Ja existe categoria produto com este codigo");
        }

        CategoriaProduto categoriaProduto = retornaCategoriaProdutoSeExistente(id);

        categoriaProduto.setCodigo(categoriaProdutoRequest.getCodigo());
        categoriaProduto.setNome(categoriaProdutoRequest.getNome());

        return mapper.map(categoriaProdutoRepository.save(categoriaProduto), CategoriaProdutoResponseDTO.class);
    }

    public void deletarCategoriaProdutoPorId(Long id){

        CategoriaProduto categoriaProduto = retornaCategoriaProdutoSeExistente(id);

        if(!produtoRepository.findAllByCategoriaProduto(categoriaProduto).isEmpty()){
            throw new ExcecaoSolicitacaoInvalida("Existem produtos com essa categoria. Operação não pode ser finalizada");
        }

        categoriaProdutoRepository.delete(categoriaProduto);
    }

    private CategoriaProduto retornaCategoriaProdutoSeExistente(Long id){
        return categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro categoria produto não encontrada"));
    }

}
