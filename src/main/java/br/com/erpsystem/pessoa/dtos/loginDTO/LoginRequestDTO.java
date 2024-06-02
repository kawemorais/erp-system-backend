package br.com.erpsystem.pessoa.dtos.loginDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "Campo usuario não pode estar vazio")
    private String usuario;

    @NotBlank(message = "Campo senha não pode estar vazio")
    private String senha;
}
