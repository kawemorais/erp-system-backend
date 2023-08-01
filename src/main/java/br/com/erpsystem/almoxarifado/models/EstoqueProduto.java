package br.com.erpsystem.almoxarifado.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tb_estoques_produto")
@Builder
public class EstoqueProduto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "almoxarifado_id", nullable = false)
    private Almoxarifado almoxarifado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @Column(name = "valorunitarioprodutoestoque", nullable = false, precision = 15, scale = 4)
    private BigDecimal valorUnitarioProdutoEstoque;

    @Column(name = "valortotalprodutoestoque", nullable = false, precision = 15, scale = 4)
    private BigDecimal valorTotalProdutoEstoque;

    @Column(name = "estoqueminimo")
    private Double estoqueMinimo;

    @Column(name = "estoquemaximo")
    private Double estoqueMaximo;

    @Column(name = "estoquepontopedido")
    private Double estoquePontoPedido;

    @Column(name = "loccorredor", length = 100)
    private String locCorredor;

    @Column(name = "locprateleira", length = 100)
    private String locPrateleira;

    @Column(name = "locbox", length = 100)
    private String locBox;

    public EstoqueProduto() {

    }
}
