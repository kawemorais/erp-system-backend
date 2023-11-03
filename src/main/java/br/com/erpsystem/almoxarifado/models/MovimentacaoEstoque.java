package br.com.erpsystem.almoxarifado.models;

import br.com.erpsystem.almoxarifado.models.enums.TipoMovimentacaoEstoque;
import br.com.erpsystem.pessoa.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_movimentacao_estoque")
@Builder
public class MovimentacaoEstoque implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "almoxarifado_id", nullable = false)
    private Almoxarifado almoxarifado;

    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @Column(name = "tipomovimentacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoEstoque tipoMovimentacao;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @Column(name = "nomepessoa", nullable = false, length = 100)
    private String nomePessoa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcionario_id")
    @JsonIgnore
    private Funcionario funcionario;

    @Column(name = "datahoramovimentacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraMovimentacao;

}
