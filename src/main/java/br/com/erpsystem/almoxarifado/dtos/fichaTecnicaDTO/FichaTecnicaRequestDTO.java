package br.com.erpsystem.almoxarifado.dtos.fichaTecnicaDTO;

import br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO.ItemFichaTecnicaRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FichaTecnicaRequestDTO {

    @NotBlank(message = "O campo nome ficha tecnica não pode ser vazio")
    private String nome;

    private List<ItemFichaTecnicaRequestDTO> itensFichaTecnica;

    @NotNull(message = "É obrigatorio indicar o id da pessoa criando ficha tecnica")
    //TODO: Buscar o usuário autenticado e setar no momento da criaçao
    private Long fkPessoaCriacao;

    private Long fkProduto;

}