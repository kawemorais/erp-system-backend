package br.com.erpsystem.sistema.infra.security;

import br.com.erpsystem.pessoa.models.UsuarioSistema;
import br.com.erpsystem.pessoa.repositories.UsuarioSistemaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class DetalheSegurancaUsuario implements UserDetailsService {


    private final UsuarioSistemaRepository usuarioSistemaRepository;

    public DetalheSegurancaUsuario(UsuarioSistemaRepository usuarioSistemaRepository) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuarioLogin) throws RuntimeException {
        UsuarioSistema usuario = this.usuarioSistemaRepository.findByUsuario(usuarioLogin)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getAuthorities()
        );
    }
}
