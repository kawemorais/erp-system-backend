package br.com.erpsystem.pessoa.dtos.usuarioSistemaDTO;

import br.com.erpsystem.pessoa.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioSistemaResponseDTO {

    private String usuario;

    @JsonIncludeProperties({"id", "nome", "matricula"})
    private Funcionario funcionario;

    private Boolean isAtivo;

}
