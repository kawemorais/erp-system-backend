package br.com.erpsystem.sistema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExcecaoErroAutenticacao extends RuntimeException {
    public ExcecaoErroAutenticacao(String mensagem) {
        super(mensagem);
    }
}
