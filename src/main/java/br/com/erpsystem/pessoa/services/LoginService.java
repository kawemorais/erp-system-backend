package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.loginDTO.LoginRequestDTO;
import br.com.erpsystem.pessoa.dtos.loginDTO.LoginResponseDTO;
import br.com.erpsystem.pessoa.models.UsuarioSistema;
import br.com.erpsystem.pessoa.repositories.UsuarioSistemaRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import br.com.erpsystem.sistema.infra.security.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginService(UsuarioSistemaRepository usuarioSistemaRepository, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest){
        UsuarioSistema usuarioSistema = usuarioSistemaRepository.findByUsuario(loginRequest.getUsuario())
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de usuario não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), usuarioSistema.getSenha())){
            throw new ExcecaoSolicitacaoInvalida("Senha invalida");
        }

        String token = this.tokenService.gerarToken(usuarioSistema);

        return new LoginResponseDTO(token);
    }


}
