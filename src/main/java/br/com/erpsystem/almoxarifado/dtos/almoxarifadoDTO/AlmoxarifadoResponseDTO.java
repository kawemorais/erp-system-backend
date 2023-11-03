package br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlmoxarifadoResponseDTO {

    private Long id;
    private String codigo;
    private String nome;
    private Boolean isAtivo;

}
