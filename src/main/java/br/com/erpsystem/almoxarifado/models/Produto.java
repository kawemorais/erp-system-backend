package br.com.erpsystem.almoxarifado.models;

import br.com.erpsystem.pessoa.models.Fornecedor;
import br.com.erpsystem.sistema.models.enums.StatusSistema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tb_produtos")
@Builder
public class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 10)
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaProduto categoriaProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "peso")
    private Double peso;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fichaTecnica_id")
    private FichaTecnica fichaTecnica;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id")
    private List<EstoqueProduto> estoques;

    @Column(name = "datacriacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;

    @Column(name = "dataalteracao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "isativo", nullable = false)
    private boolean isAtivo;

    @Column(name = "precoultimacompra", precision = 15, scale = 4)
    private BigDecimal precoUltimaCompra;

    @Column(name = "custoproduto", precision = 15, scale = 4)
    private BigDecimal custoProduto;

    @Default
    @Column(name = "status", nullable = false)
    private int status = StatusSistema.NORMAL.getStatusId();

    public Produto() {

    }
}
