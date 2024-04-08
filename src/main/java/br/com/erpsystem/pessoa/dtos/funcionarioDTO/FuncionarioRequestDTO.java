package br.com.erpsystem.pessoa.dtos.funcionarioDTO;

import br.com.erpsystem.pessoa.dtos.enderecoDTO.EnderecoRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncionarioRequestDTO {

    @Length(max = 11, message = "Telefone deve conter no maximo 11 caracteres")
    private String telefone;

    @Length(max = 11, message = "Celular deve conter no maximo 11 caracteres")
    private String celular;

    @Email(message = "Deve conter um email válido")
    @NotBlank(message = "Campo email não pode estar vazio")
    private String email;

    @NotNull
    private EnderecoRequestDTO enderecoRequest;

    @NotBlank(message = "Campo nome não pode estar vazio")
    @Length(max = 255, message = "Campo nome não poder conter mais de 255 caracteres")
    private String nome;

    @Length(min = 11, max = 11, message = "Campo cpf deve conter 11 caracteres")
    private String cpf;

    @NotBlank(message = "Campo nome não pode estar vazio")
    private String rg;

    @Length(max = 12, message = "Campo reservista deve conter no maximo 8 caracteres")
    private String reservista;

    @Past(message = "Campo data admissao invalido")
    @NotNull(message = "Campo data admissao nao pode ser nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt_BR")
    private LocalDate dataNascimento;

    @NotBlank
    @Pattern(regexp = "FEMININO|MASCULINO|PREFERE_NAO_DIZER", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Campo sexo com formatacao errada")
    private String sexo;

    @Length(max = 20, message = "Campo matricula deve conter no maximo 20 caracteres")
    private String matricula;

    @PastOrPresent(message = "Campo data admissao invalido")
    @NotNull(message = "Campo data admissao nao pode ser nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt_BR")
    private LocalDate dataAdmissao;

    @NotNull(message = "Campo salario base é obrigatorio")
    private BigDecimal salarioBase;

    @NotNull(message = "Campo setor é obrigatorio")
    private Long fkSetor;

    @NotNull(message = "Campo cargo é obrigatorio")
    private Long fkCargo;

    @NotNull(message = "Campo funcao é obrigatorio")
    private Long fkFuncao;

    private Boolean isAtivo;
}
