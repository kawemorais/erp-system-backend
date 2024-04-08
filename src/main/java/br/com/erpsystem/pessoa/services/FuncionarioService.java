package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.funcionarioDTO.FuncionarioRequestDTO;
import br.com.erpsystem.pessoa.dtos.funcionarioDTO.FuncionarioResponseDTO;
import br.com.erpsystem.pessoa.models.*;
import br.com.erpsystem.pessoa.models.enums.TipoSexo;
import br.com.erpsystem.pessoa.repositories.CargoRepository;
import br.com.erpsystem.pessoa.repositories.FuncaoRepository;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.pessoa.repositories.SetorRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;
    private final CargoRepository cargoRepository;
    private final FuncaoRepository funcaoRepository;
    private final EnderecoService enderecoService;

    private final ModelMapper mapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository, CargoRepository cargoRepository, FuncaoRepository funcaoRepository, EnderecoService enderecoService, ModelMapper modelMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
        this.cargoRepository = cargoRepository;
        this.funcaoRepository = funcaoRepository;
        this.enderecoService = enderecoService;
        this.mapper = modelMapper;
    }

    public List<FuncionarioResponseDTO> listarTodosFuncionarios(){
        return funcionarioRepository.findAll()
                .stream()
                .map(funcionario -> mapper.map(funcionario, FuncionarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDTO listarFuncionarioPorId(Long id){
        Funcionario funcionario = retornaFuncionarioSeExistente(id);

        return mapper.map(funcionario, FuncionarioResponseDTO.class);
    }

    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO funcionarioRequest){

        checaExistenciaFuncionarioCamposUnicos(funcionarioRequest.getCpf(), funcionarioRequest.getRg(),
                funcionarioRequest.getReservista(), funcionarioRequest.getEmail(), funcionarioRequest.getMatricula());

        Endereco endereco = enderecoService.gerarEndereco(funcionarioRequest.getEnderecoRequest());

        Funcao funcao = funcaoRepository.findById(funcionarioRequest.getFkFuncao())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Funcao não encontrada"));

        Cargo cargo = cargoRepository.findById(funcionarioRequest.getFkCargo())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Cargo não encontrado"));

        Setor setor = setorRepository.findById(funcionarioRequest.getFkSetor())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Setor não encontrado"));

        Funcionario funcionario = Funcionario.builder()
                .telefone(funcionarioRequest.getTelefone())
                .celular(funcionarioRequest.getCelular())
                .email(funcionarioRequest.getEmail())
                .endereco(endereco)
                .isAtivo(Boolean.TRUE)
                .nome(funcionarioRequest.getNome())
                .cpf(funcionarioRequest.getCpf())
                .rg(funcionarioRequest.getRg())
                .reservista(funcionarioRequest.getReservista())
                .dataNascimento(funcionarioRequest.getDataNascimento())
                .sexo(TipoSexo.valueOf(funcionarioRequest.getSexo().toUpperCase()))
                .matricula(funcionarioRequest.getMatricula())
                .dataAdmissao(funcionarioRequest.getDataAdmissao())
                .salarioBase(funcionarioRequest.getSalarioBase())
                .setor(setor)
                .cargo(cargo)
                .funcao(funcao)
                .build();

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return mapper.map(funcionarioSalvo, FuncionarioResponseDTO.class);
    }

    public FuncionarioResponseDTO alterarFuncionarioPorId(Long id, FuncionarioRequestDTO funcionarioRequest){
        Funcionario funcionario = retornaFuncionarioSeExistente(id);

        if (!funcionario.getCpf().equals(funcionarioRequest.getCpf())) {
            checaExistenciaFuncionarioCpf(funcionarioRequest.getCpf());
        }

        if (!funcionario.getRg().equals(funcionarioRequest.getRg())) {
            checaExistenciaFuncionarioRg(funcionarioRequest.getRg());
        }

        if (!funcionario.getReservista().equals(funcionarioRequest.getReservista())) {
            checaExistenciaFuncionarioReservista(funcionarioRequest.getReservista());
        }

        if (!funcionario.getEmail().equals(funcionarioRequest.getEmail())) {
            checaExistenciaFuncionarioEmail(funcionarioRequest.getEmail());
        }

        if (!funcionario.getMatricula().equals(funcionarioRequest.getMatricula())) {
            checaExistenciaFuncionarioMatricula(funcionarioRequest.getMatricula());
        }

        if (!funcionario.getEndereco().getCep().equals(funcionarioRequest.getEnderecoRequest().getCep())) {
            Endereco novoEndereco = enderecoService.gerarEndereco(funcionarioRequest.getEnderecoRequest());

            funcionario.getEndereco().setCep(novoEndereco.getCep());
            funcionario.getEndereco().setLogradouro(novoEndereco.getLogradouro());
            funcionario.getEndereco().setBairro(novoEndereco.getBairro());
            funcionario.getEndereco().setCidade(novoEndereco.getCidade());
            funcionario.getEndereco().setUf(novoEndereco.getUf());
            funcionario.getEndereco().setNumero(novoEndereco.getNumero());
            funcionario.getEndereco().setComplemento(novoEndereco.getComplemento());
        }

        Funcao funcao = funcaoRepository.findById(funcionarioRequest.getFkFuncao())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Funcao não encontrada"));

        Cargo cargo = cargoRepository.findById(funcionarioRequest.getFkCargo())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Cargo não encontrado"));

        Setor setor = setorRepository.findById(funcionarioRequest.getFkSetor())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Setor não encontrado"));

        funcionario.setTelefone(funcionarioRequest.getTelefone());
        funcionario.setCelular(funcionarioRequest.getCelular());
        funcionario.setEmail(funcionarioRequest.getEmail());
        funcionario.setNome(funcionarioRequest.getNome());
        funcionario.setCpf(funcionarioRequest.getCpf());
        funcionario.setRg(funcionarioRequest.getRg());
        funcionario.setReservista(funcionarioRequest.getReservista());
        funcionario.setDataNascimento(funcionarioRequest.getDataNascimento());
        funcionario.setSexo(TipoSexo.valueOf(funcionarioRequest.getSexo().toUpperCase()));
        funcionario.setMatricula(funcionarioRequest.getMatricula());
        funcionario.setDataAdmissao(funcionarioRequest.getDataAdmissao());
        funcionario.setSalarioBase(funcionarioRequest.getSalarioBase());
        funcionario.setSetor(setor);
        funcionario.setCargo(cargo);
        funcionario.setFuncao(funcao);

        funcionario.getEndereco().setNumero(funcionarioRequest.getEnderecoRequest().getNumero());
        funcionario.getEndereco().setComplemento(funcionarioRequest.getEnderecoRequest().getComplemento());

        Boolean isAtivo = Optional.ofNullable(funcionarioRequest.getIsAtivo()).orElse(true);
        funcionario.setIsAtivo(isAtivo);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return mapper.map(funcionarioSalvo, FuncionarioResponseDTO.class);
    }

    private Funcionario retornaFuncionarioSeExistente(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de funcionário não encontrado"));
    }

    private void checaExistenciaFuncionarioCpf(String cpf){
        if(funcionarioRepository.findByCpf(cpf).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe funcionario cadastrado com este cpf");
        }
    }

    private void checaExistenciaFuncionarioRg(String rg){
        if(funcionarioRepository.findByRg(rg).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe funcionario cadastrado com este rg");
        }
    }

    private void checaExistenciaFuncionarioReservista(String revervista){
        if(funcionarioRepository.findByReservista(revervista).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe funcionario cadastrado com esta reservista");
        }
    }

    private void checaExistenciaFuncionarioEmail(String email){
        if(funcionarioRepository.findByEmail(email).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe funcionario cadastrado com este email");
        }
    }

    private void checaExistenciaFuncionarioMatricula(String matricula){
        if(funcionarioRepository.findByMatricula(matricula).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe funcionario cadastrado com esta matricula");
        }
    }

    private void checaExistenciaFuncionarioCamposUnicos(String cpf, String rg, String reservista, String email,
                                                               String matricula) {
        if(funcionarioRepository.findByCpfAndRgAndReservistaAndEmailAndMatricula(cpf, rg, reservista, email, matricula).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe funcionario cadastrado com estas informações");
        }
    }
}
