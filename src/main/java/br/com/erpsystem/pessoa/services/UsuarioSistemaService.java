package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.usuarioSistemaDTO.UsuarioSistemaRequestDTO;
import br.com.erpsystem.pessoa.dtos.usuarioSistemaDTO.UsuarioSistemaResponseDTO;
import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.pessoa.models.UsuarioSistema;
import br.com.erpsystem.pessoa.repositories.UsuarioSistemaRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;
    private final FuncionarioService funcionarioService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioSistemaRepository, FuncionarioService funcionarioService, ModelMapper mapper, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
        this.funcionarioService = funcionarioService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioSistemaResponseDTO> listarTodosUsuarios(){
        return usuarioSistemaRepository.findAll()
                .stream()
                .map(usuario -> mapper.map(usuario, UsuarioSistemaResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioSistemaResponseDTO listarUsuarioPorId(Long id){
        UsuarioSistema usuario = retornaUsuarioSistemaSeExistente(id);

        return mapper.map(usuario, UsuarioSistemaResponseDTO.class);
    }

    public UsuarioSistemaResponseDTO criarUsuario(UsuarioSistemaRequestDTO usuarioRequest){
        checarSeUsuarioSistemaExistentePorUsuario(usuarioRequest.getUsuario());
        Funcionario funcionario = checarSeUsuarioSistemaExistentePorFuncionario(usuarioRequest.getFkFuncionario());

        UsuarioSistema usuarioSistema = UsuarioSistema.builder()
                .usuario(usuarioRequest.getUsuario())
                .senha(passwordEncoder.encode(usuarioRequest.getSenha()))
                .funcionario(funcionario)
                .isAtivo(true)
                .build();

        return mapper.map(usuarioSistemaRepository.save(usuarioSistema), UsuarioSistemaResponseDTO.class);
    }

    public UsuarioSistemaResponseDTO alterarUsuarioPorId(Long id, UsuarioSistemaRequestDTO usuarioRequest){
        UsuarioSistema usuarioSistema = retornaUsuarioSistemaSeExistente(id);

        String nomeUsuario = usuarioRequest.getUsuario();

        if (!usuarioSistema.getUsuario().equals(nomeUsuario)){
            checarSeUsuarioSistemaExistentePorUsuario(nomeUsuario);
            usuarioSistema.setUsuario(nomeUsuario);
        }

        if (!usuarioSistema.getFuncionario().getId().equals(usuarioRequest.getFkFuncionario())){
            Funcionario funcionario = checarSeUsuarioSistemaExistentePorFuncionario(usuarioRequest.getFkFuncionario());
            usuarioSistema.setFuncionario(funcionario);
        }

        usuarioSistema.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        usuarioSistema.setIsAtivo(usuarioRequest.getIsAtivo());

        return mapper.map(usuarioSistemaRepository.save(usuarioSistema), UsuarioSistemaResponseDTO.class);
    }

    public UsuarioSistemaResponseDTO ativarOuInativarUsuario(Long id){
        UsuarioSistema usuarioSistema = retornaUsuarioSistemaSeExistente(id);

        usuarioSistema.setIsAtivo(!usuarioSistema.getIsAtivo());

        return mapper.map(usuarioSistemaRepository.save(usuarioSistema), UsuarioSistemaResponseDTO.class);
    }

    private UsuarioSistema retornaUsuarioSistemaSeExistente(Long id){
        return usuarioSistemaRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de usuário não encontrado"));
    }

    private void checarSeUsuarioSistemaExistentePorUsuario(String usuario) {
        if(usuarioSistemaRepository.findByUsuario(usuario).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe usuário do sistema com este nome de usuário");
        }
    }

    private Funcionario checarSeUsuarioSistemaExistentePorFuncionario(Long id){
        Funcionario funcionario = funcionarioService.retornaFuncionarioSeExistente(id);

        if (usuarioSistemaRepository.findByFuncionario(funcionario).isPresent()) {
            throw new ExcecaoSolicitacaoInvalida("Já existe usuário do sistema vinculado a este funcionário");
        }

        return funcionario;
    }
}
