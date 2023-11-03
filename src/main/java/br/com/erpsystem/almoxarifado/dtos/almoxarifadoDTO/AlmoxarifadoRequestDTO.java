package br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlmoxarifadoRequestDTO {

    @NotBlank(message = "Campo codigo não pode estar vazio")
    private String codigo;

    @NotBlank(message = "Campo nome não pode estar vazio")
    @Size(message = "Campo nome deve conter no minimo dois caracteres", min = 2)
    private String nome;

    private Boolean isAtivo;

}
