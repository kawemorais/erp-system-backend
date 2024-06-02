package br.com.erpsystem.sistema.infra.security;

import br.com.erpsystem.pessoa.models.UsuarioSistema;
import br.com.erpsystem.pessoa.repositories.UsuarioSistemaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class FiltroSegurancaConfig extends OncePerRequestFilter {

    public final TokenService tokenService;

    public final UsuarioSistemaRepository usuarioSistemaRepository;

    public FiltroSegurancaConfig(TokenService tokenService, UsuarioSistemaRepository usuarioSistemaRepository) {
        this.tokenService = tokenService;
        this.usuarioSistemaRepository = usuarioSistemaRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        String usuarioDoToken = tokenService.validaToken(token);

        if(usuarioDoToken != null){

            UsuarioSistema usuarioSistema = usuarioSistemaRepository.findByUsuario(usuarioDoToken).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

            Collection<? extends GrantedAuthority> listaPermissao = usuarioSistema.getAuthorities();

            UsernamePasswordAuthenticationToken usuarioAutenticado = new UsernamePasswordAuthenticationToken(usuarioSistema, null, listaPermissao);

            SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        String headerRequisicao = request.getHeader("Authorization");

        if(headerRequisicao == null) return null;

        return headerRequisicao.replace("Bearer ", "");
    }

}
