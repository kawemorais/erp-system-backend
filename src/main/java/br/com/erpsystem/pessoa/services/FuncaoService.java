package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.funcaoDTO.FuncaoRequestDTO;
import br.com.erpsystem.pessoa.dtos.funcaoDTO.FuncaoResponseDTO;
import br.com.erpsystem.pessoa.models.Funcao;
import br.com.erpsystem.pessoa.repositories.FuncaoRepository;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncaoService {

    private final FuncaoRepository funcaoRepository;
    private final FuncionarioRepository funcionarioRepository;

    private final ModelMapper mapper;

    public FuncaoService(FuncaoRepository funcaoRepository, FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.funcaoRepository = funcaoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public List<FuncaoResponseDTO> listarTodasFuncoes(){
        return funcaoRepository.findAll()
                .stream()
                .map(funcao -> mapper.map(funcao, FuncaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public FuncaoResponseDTO listarFuncaoPorId(Long id){
        Funcao funcao = retornaFuncaoSeExistente(id);

        return mapper.map(funcao, FuncaoResponseDTO.class);
    }

    public FuncaoResponseDTO criarFuncao(FuncaoRequestDTO funcaoRequest){

        checarSeFuncaoExistentePorNome(funcaoRequest.getNome());

        Funcao funcaoSalva = funcaoRepository.save(mapper.map(funcaoRequest, Funcao.class));

        return mapper.map(funcaoSalva, FuncaoResponseDTO.class);
    }

    public FuncaoResponseDTO alterarFuncaoPorId(Long id, FuncaoRequestDTO funcaoRequest){

        Funcao funcao = retornaFuncaoSeExistente(id);

        if(funcao.getNome().equals(funcaoRequest.getNome())){
            return mapper.map(funcao, FuncaoResponseDTO.class);
        }

        checarSeFuncaoExistentePorNome(funcaoRequest.getNome());

        funcao.setNome(funcaoRequest.getNome());

        return mapper.map(funcaoRepository.save(funcao), FuncaoResponseDTO.class);

    }

    public void deletarFuncaoPorId(Long id){

        Funcao funcao = retornaFuncaoSeExistente(id);

        if(!funcionarioRepository.findAllByFuncao(funcao).isEmpty()){
            throw new ExcecaoSolicitacaoInvalida("Esta funcao esta sendo utilizado. Operação não pode ser concluída");
        }

        funcaoRepository.delete(funcao);
    }

    private Funcao retornaFuncaoSeExistente(Long id) {
        return funcaoRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de função não encontrado"));
    }

    private void checarSeFuncaoExistentePorNome(String nome){
        if(funcaoRepository.findByNome(nome).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe função com este nome");
        }
    }
}
