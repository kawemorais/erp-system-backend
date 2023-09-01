package br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO;

import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.FichaTecnica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemFichaTecnicaResponseDTO {

    private Long id;

    private ProdutoResponseDTO produto;

    @JsonIgnore
    private FichaTecnica fichaTecnica;

    private Double quantidade;

    private BigDecimal subTotal;

}
