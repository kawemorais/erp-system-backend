package br.com.erpsystem.pessoa.dtos.funcaoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncaoResponseDTO {

    private Long id;
    private String nome;

}
