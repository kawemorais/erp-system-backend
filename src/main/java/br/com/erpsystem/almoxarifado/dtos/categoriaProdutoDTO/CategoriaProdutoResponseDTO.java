package br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaProdutoResponseDTO {

    private Long id;
    private String codigo;
    private String nome;


}
