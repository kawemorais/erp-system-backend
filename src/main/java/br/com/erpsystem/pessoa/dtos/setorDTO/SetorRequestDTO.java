package br.com.erpsystem.pessoa.dtos.setorDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetorRequestDTO {

    @NotBlank(message = "Campo nome não pode estar vazio")
    private String nome;

}
