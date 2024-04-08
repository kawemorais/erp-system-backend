package br.com.erpsystem.pessoa.dtos.setorDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetorResponseDTO {

    private Long id;
    private String nome;

}
