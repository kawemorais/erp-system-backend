package br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemFichaTecnicaRequestDTO {

    @NotNull(message = "O campo produto não pode ser nulo")
    private Long fkProduto;

    @NotNull(message = "O campo ficha tecnica não pode ser nulo")
    private Long fkFichaTecnica;

    @Positive(message = "O campo quantidade deve ser um valor maior que zero")
    private Double quantidade;

}
