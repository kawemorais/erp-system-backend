package br.com.erpsystem.almoxarifado.dtos.unidadeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnidadeResponseDTO {

    private Long id;
    private String nome;
    private String descricao;

}
