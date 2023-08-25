package br.com.erpsystem.pessoa.models;

import br.com.erpsystem.pessoa.models.enums.TipoSexo;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_funcionarios")
@Builder
public class Funcionario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "telefone", length = 10)
    protected String telefone;

    @Column(name = "celular", length = 11)
    protected String celular;

    @Column(name = "email", nullable = false)
    protected String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    protected Endereco endereco;

    @Column(name = "isativo", nullable = false)
    protected Boolean isAtivo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "rg", nullable = false, length = 8)
    private String rg;

    @Column(name = "reservista", length = 12)
    private String reservista;

    @Column(name = "datanascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dataNascimento;

    @Column(name = "sexo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSexo sexo;

    @Column(name = "matricula", length = 20)
    private String matricula;

    @Column(name = "dataadmissao", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dataAdmissao;

    @Column(name = "salariobase", precision = 15, scale = 4)
    private BigDecimal salarioBase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcao_id", nullable = false)
    private Funcao funcao;

}

