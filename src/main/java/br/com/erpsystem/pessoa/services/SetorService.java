package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.setorDTO.SetorRequestDTO;
import br.com.erpsystem.pessoa.dtos.setorDTO.SetorResponseDTO;
import br.com.erpsystem.pessoa.models.Setor;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.pessoa.repositories.SetorRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ModelMapper mapper;

    public SetorService(SetorRepository setorRepository, FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.setorRepository = setorRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public List<SetorResponseDTO> listarTodosSetores(){
        return setorRepository.findAll()
                .stream()
                .map(setor -> mapper.map(setor, SetorResponseDTO.class))
                .collect(Collectors.toList());
    }

    public SetorResponseDTO listarSetorPorId(Long id){
        Setor setor = retornaSetorSeExistente(id);

        return mapper.map(setor, SetorResponseDTO.class);
    }

    public SetorResponseDTO criarSetor(SetorRequestDTO setorRequest){

        checarSeSetorExistentePorNome(setorRequest.getNome());

        Setor setorSalvo = setorRepository.save(mapper.map(setorRequest, Setor.class));

        return mapper.map(setorSalvo, SetorResponseDTO.class);
    }

    public SetorResponseDTO alterarSetorPorId(Long id, SetorRequestDTO setorRequest){

        Setor setor = retornaSetorSeExistente(id);

        if(setor.getNome().equals(setorRequest.getNome())){
            return mapper.map(setor, SetorResponseDTO.class);
        }

        checarSeSetorExistentePorNome(setorRequest.getNome());

        setor.setNome(setorRequest.getNome());

        return mapper.map(setorRepository.save(setor), SetorResponseDTO.class);
    }

    public void deletarSetorPorId(Long id){

        Setor setor = retornaSetorSeExistente(id);

        if(!funcionarioRepository.findAllBySetor(setor).isEmpty()){
            throw new ExcecaoSolicitacaoInvalida("Este setor esta sendo utilizado. Operação não pode ser concluída");
        }

        setorRepository.delete(setor);
    }

    private Setor retornaSetorSeExistente(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de setor não encontrado"));
    }

    private void checarSeSetorExistentePorNome(String nome){
        if(setorRepository.findByNome(nome).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe setor com este nome");
        }
    }
}
