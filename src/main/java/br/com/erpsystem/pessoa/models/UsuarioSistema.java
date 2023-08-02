package br.com.erpsystem.pessoa.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "tb_usuarios_sistema")
public class UsuarioSistema implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario", nullable = false, length = 50)
    private String usuario;

    @Column(name = "senha", nullable = false, unique = true)
    private String senha;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "isativo")
    private Boolean isAtivo;
}
