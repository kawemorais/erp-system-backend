package br.com.erpsystem.almoxarifado.models;

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
@Table(name = "tb_almoxarifados")
@Builder
public class Almoxarifado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo", nullable = false, length = 10)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "isativo", nullable = false)
    private boolean isAtivo;

}
