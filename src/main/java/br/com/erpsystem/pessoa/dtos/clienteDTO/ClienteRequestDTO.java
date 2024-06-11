package br.com.erpsystem.pessoa.dtos.clienteDTO;

import br.com.erpsystem.pessoa.dtos.enderecoDTO.EnderecoRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteRequestDTO {

    @Length(max = 10, message = "Telefone deve conter no maximo 10 caracteres")
    private String telefone;

    @Length(max = 11, message = "Telefone deve conter no maximo 11 caracteres")
    private String celular;

    @Email(message = "Deve conter um email válido")
    @NotBlank(message = "Campo email não pode estar vazio")
    private String email;

    @NotNull
    private EnderecoRequestDTO enderecoRequest;

    @NotBlank(message = "Campo nome fantasia precisa ser preenchido")
    private String nomeFantasia;

    @NotBlank(message = "Campo razão social precisa ser preenchido")
    private String razaoSocial;

    @NotBlank(message = "Campo cnpj precisa ser preenchido")
    @CNPJ(message = "Cnpj inválido")
    private String cnpj;

    @NotBlank(message = "Campo inscricão estadual precisa ser preenchido")
    @Length(min = 9, max = 9, message = "Campo inscricão estadual deve conter no maximo 9 caracteres")
    private String inscricaoEstadual;

    @NotBlank(message = "Campo inscricão municipal precisa ser preenchido")
    @Length(min = 11, max = 11, message = "Campo inscricão estadual deve conter no maximo 14 caracteres")
    private String inscricaoMunicipal;

    private Boolean isAtivo;
}
