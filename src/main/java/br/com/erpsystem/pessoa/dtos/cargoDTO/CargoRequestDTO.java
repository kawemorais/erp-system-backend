package br.com.erpsystem.pessoa.dtos.cargoDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CargoRequestDTO {

    @NotBlank(message = "Campo nome não pode estar vazio")
    private String nome;

}
