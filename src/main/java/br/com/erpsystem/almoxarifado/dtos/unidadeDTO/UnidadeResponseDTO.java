package br.com.erpsystem.almoxarifado.dtos.unidadeDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnidadeResponseDTO {

    private Long id;
    private String nome;
    private String descricao;

}
