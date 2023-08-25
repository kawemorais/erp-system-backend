package br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstoqueProdutoAddQuantidadeRequestDTO {

    @NotNull(message = "O campo quantidade é obrigatorio")
    @Positive(message = "A quantidade deve ser um numero positivo maior que zero")
    private Double quantidade;

    @NotNull(message = "O campo valor compra é obrigatorio")
    @Positive(message = "A quantidade deve ser um numero positivo maior que zero")
    private BigDecimal valorUnitarioCompra;
}
