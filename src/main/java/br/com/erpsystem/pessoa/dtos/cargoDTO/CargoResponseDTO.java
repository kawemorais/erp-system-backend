package br.com.erpsystem.pessoa.dtos.cargoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CargoResponseDTO {

    private Long id;
    private String nome;

}
