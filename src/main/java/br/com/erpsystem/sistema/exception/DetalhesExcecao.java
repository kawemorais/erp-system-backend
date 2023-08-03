package br.com.erpsystem.sistema.exception;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class DetalhesExcecao {
    private int codigoStatus;

    private String status;

    private String descricao;

    private String timestamp;

    private List<HashMap<String, String>> campos;
}

