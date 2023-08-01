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
@Table(name = "tb_itens_fichatecnica")
@Builder
public class ItensFichaTecnica implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Produto.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 4)
    private BigDecimal subTotal;

    public ItensFichaTecnica() {

    }
}
