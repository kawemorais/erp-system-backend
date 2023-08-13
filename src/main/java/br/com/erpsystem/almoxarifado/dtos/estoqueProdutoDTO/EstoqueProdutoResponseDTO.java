package br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO;

import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstoqueProdutoResponseDTO {

    private Long id;

    private Almoxarifado almoxarifado;

    private Produto produto;

    private Double quantidade;

    private BigDecimal valorUnitarioProdutoEstoque;

    private BigDecimal valorTotalProdutoEstoque;

    private Double estoqueMinimo;

    private Double estoqueMaximo;

    private Double estoquePontoPedido;

    private String locCorredor;

    private String locPrateleira;

    private String locBox;

}
