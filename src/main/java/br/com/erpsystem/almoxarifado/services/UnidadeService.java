package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeResponseDTO;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.UnidadeRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper mapper;

    public UnidadeService(UnidadeRepository unidadeRepository, ProdutoRepository produtoRepository, ModelMapper mapper) {
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    public List<UnidadeResponseDTO> listarTodasUnidades(){
        return unidadeRepository.findAll()
                .stream()
                .map(unidade -> mapper.map(unidade, UnidadeResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UnidadeResponseDTO listarUnidadePorId(Long id){
        Unidade unidade = retornaUnidadeSeExistente(id);

        return mapper.map(unidade, UnidadeResponseDTO.class);
    }

    public UnidadeResponseDTO criarUnidade(UnidadeRequestDTO unidadeRequest){

        if (unidadeRepository.findByNome(unidadeRequest.getNome()).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Ja existe unidade com este nome");
        }

        Unidade unidadeSalva = unidadeRepository.save(mapper.map(unidadeRequest, Unidade.class));

        return mapper.map(unidadeSalva, UnidadeResponseDTO.class);
    }

    public UnidadeResponseDTO alterarUnidadePorId(Long id, UnidadeRequestDTO unidadeRequest){

        Unidade unidade = retornaUnidadeSeExistente(id);

        unidade.setNome(unidadeRequest.getNome());
        unidade.setDescricao(unidadeRequest.getDescricao());

        return mapper.map(unidadeRepository.save(unidade), UnidadeResponseDTO.class);
    }

    public Optional<Boolean> deletarUnidadePorId(Long id){

        Unidade unidade = retornaUnidadeSeExistente(id);

        if(!produtoRepository.findAllByUnidade(unidade).isEmpty()){
            throw new ExcecaoSolicitacaoInvalida("Existe produtos com essa unidade. Operação não pode ser finalizada");
        }

        unidadeRepository.delete(unidade);

        return Optional.of(true);
    }

    private Unidade retornaUnidadeSeExistente(Long id){
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro unidade não encontrada"));
    }
}
