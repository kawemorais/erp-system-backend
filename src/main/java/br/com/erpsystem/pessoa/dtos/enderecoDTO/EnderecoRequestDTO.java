package br.com.erpsystem.pessoa.dtos.enderecoDTO;

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
public class EnderecoRequestDTO {

    @NotBlank(message = "O campo cep não pode ser vazio")
    @Length(max = 8, message = "Campo cep invalido! Máximo 8 caracteres.")
    private String cep;

    @NotBlank(message = "O campo cep não pode ser vazio")
    private String numero;

    @Length(max = 200, message = "Máximo 200 caracteres")
    private String complemento;

}
