package br.com.erpsystem.pessoa.dtos.funcaoDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncaoRequestDTO {

    @NotBlank(message = "Campo nome não pode estar vazio")
    private String nome;

}
