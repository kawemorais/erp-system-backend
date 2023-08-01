package br.com.erpsystem.almoxarifado.models;

import br.com.erpsystem.pessoa.models.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tb_fichas_tecnicas")
@Builder
public class FichaTecnica implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fichatecnica_id", nullable = false)
    private List<ItensFichaTecnica> itensFichaTecnica;

    @Column(name = "custototal", precision = 15, scale = 4)
    private BigDecimal custoTotal;

    @Column(name = "datacriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;

    @Column(name = "dataultimaalteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataUltimaAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario pessoaCriacao;

    public FichaTecnica() {

    }
}
