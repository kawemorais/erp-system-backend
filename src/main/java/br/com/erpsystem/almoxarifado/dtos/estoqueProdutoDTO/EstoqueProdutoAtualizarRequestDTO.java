package br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstoqueProdutoAtualizarRequestDTO {

    @NotEmpty(message = "O campo quantidade é obrigatorio")
    @Positive(message = "A quantidade deve ser um numero positivo maior que zero")
    private Double quantidade;

    @NotNull(message = "O campo valor compra é obrigatorio")
    @Positive(message = "A quantidade deve ser um numero positivo maior que zero")
    private BigDecimal valorUnitarioCompra;

    @Positive(message = "Estoque minimo deve ser um numero positivo maior que zero")
    private Double estoqueMinimo;

    @Positive(message = "Estoque maximo deve ser um numero positivo maior que zero")
    private Double estoqueMaximo;

    @Positive(message = "Estoque ponto pedido deve ser um numero positivo maior que zero")
    private Double estoquePontoPedido;

    @Builder.Default()
    private String locCorredor = "";

    @Builder.Default()
    private String locPrateleira = "";

    @Builder.Default
    private String locBox = "";

}
