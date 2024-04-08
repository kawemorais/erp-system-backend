package br.com.erpsystem.pessoa.dtos.funcionarioDTO;

import br.com.erpsystem.pessoa.models.Cargo;
import br.com.erpsystem.pessoa.models.Endereco;
import br.com.erpsystem.pessoa.models.Funcao;
import br.com.erpsystem.pessoa.models.Setor;
import br.com.erpsystem.pessoa.models.enums.TipoSexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncionarioResponseDTO {

    private Long id;

    private String telefone;

    private String celular;

    private String email;

    private Endereco endereco;

    private Boolean isAtivo;

    private String nome;

    private String cpf;

    private String rg;

    private String reservista;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt_BR")
    private LocalDate dataNascimento;

    private TipoSexo sexo;

    private String matricula;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt_BR")
    private LocalDate dataAdmissao;

    private BigDecimal salarioBase;

    private Setor setor;

    private Cargo cargo;

    private Funcao funcao;
}
