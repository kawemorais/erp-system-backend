package br.com.erpsystem.sistema.exception;

import br.com.erpsystem.sistema.utils.FormatoDataUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExcecaoHandler {

    private final FormatoDataUtil dataUtil;

    public ApiExcecaoHandler(FormatoDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @ExceptionHandler(ExcecaoSolicitacaoInvalida.class)
    public ResponseEntity<DetalhesExcecao> handlerExcecaoSolicitacaoInvalida(ExcecaoSolicitacaoInvalida excecao){
        return new ResponseEntity<>(
                DetalhesExcecao.builder()
                        .codigoStatus(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase())
                        .descricao(excecao.getMessage())
                        .timestamp(dataUtil.formatarData(LocalDateTime.now()))
                        .build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DetalhesExcecao> handlerArgumentosNaoValidos(MethodArgumentNotValidException excecao){

        List<FieldError> fieldErrors = excecao.getBindingResult().getFieldErrors();

        DetalhesExcecao detalhesExcecao = DetalhesExcecao.builder()
                .codigoStatus(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.toString())
                .descricao("Campos invalidos")
                .timestamp(dataUtil.formatarData(LocalDateTime.now()))
                .campos(
                        fieldErrors.stream()
                                .map(fieldError -> {
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                                    return map;
                                })
                                .collect(Collectors.toList())
                )
                .build();

        return new ResponseEntity<>(detalhesExcecao, HttpStatus.BAD_REQUEST);
    }
}
