package br.com.erpsystem.almoxarifado.dtos.unidadeDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UnidadeRequestDTO {

    @NotBlank(message = "Campo nome não pode estar vazio")
    @Size(message = "Campo nome deve conter no minimo dois caracteres", min = 2)
    private String nome;

    @NotBlank(message = "Campo descricao não pode estar vazio")
    @Size(message = "Campo descricao deve conter no minimo dois caracteres", min = 2)
    private String descricao;

}
