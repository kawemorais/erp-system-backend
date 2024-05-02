package br.com.erpsystem.pessoa.dtos.usuarioSistemaDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioSistemaRequestDTO {

    @NotBlank(message = "Campo usuario não pode estar vazio")
    @Length(min = 3, max = 50, message = "Campo usuario deve ter entre 3 e 50 caracteres")
    private String usuario;

    @NotBlank(message = "Campo senha não pode estar vazio")
    @Length(min = 4, max = 16, message = "Campo senha deve ter entre 4 e 16 caracteres")
    private String senha;

    @NotBlank(message = "Campo funcionário não pode estar vazio")
    private Long fkFuncionario;

    private Boolean isAtivo;

}
