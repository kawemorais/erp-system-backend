package br.com.erpsystem.sistema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcecaoSolicitacaoInvalida extends RuntimeException {
    public ExcecaoSolicitacaoInvalida(String mensagem) {
        super(mensagem);
    }
}
