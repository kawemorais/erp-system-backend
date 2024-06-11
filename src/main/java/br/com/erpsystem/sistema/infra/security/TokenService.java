package br.com.erpsystem.sistema.infra.security;

import br.com.erpsystem.pessoa.models.UsuarioSistema;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j(topic = "INFRA - AUTH - TokenService")
public class TokenService {

    @Value("${api.security.token-secret}")
    private String secret;

    private final String issuer = "erpsystem-api-security";

    public String gerarToken(UsuarioSistema usuario){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(gerarTempoExpiracaoToken())
                    .sign(algoritmo);

        } catch (JWTCreationException e){
            log.warn(e.getMessage());
            return null;
        }
    }

    public String validaToken(String token){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant gerarTempoExpiracaoToken(){
        int tempoExpiracaoToken = 8;
        return LocalDateTime.now().plusHours(tempoExpiracaoToken).toInstant(ZoneOffset.of("-03:00"));
    }
}
