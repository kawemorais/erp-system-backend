package br.com.erpsystem.pessoa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_clientes")
@Builder
public class Cliente implements Serializable {

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

    @Column(name = "nomefantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "razaosocial", nullable = false)
    private String razaoSocial;

    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;

    @Column(name = "inscricaoestadual", nullable = false, length = 9)
    private String inscricaoEstadual;

    @Column(name = "inscricaomunicipal", nullable = false, length = 11)
    private String inscricaoMunicipal;

    @Column(name = "isativo", nullable = false)
    protected Boolean isAtivo;

}
