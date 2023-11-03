package br.com.erpsystem.almoxarifado.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "tb_unidades")
@Builder
public class Unidade implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 30)
    @NotBlank(message = "Campo nome não pode estar vazio")
    private String nome;

    @Column(name = "descricao", nullable = false, length = 100)
    @NotBlank(message = "Campo descricao não pode estar vazio")
    @Size(message = "Campo descricao deve conter no minimo dois caracteres", min = 2)
    private String descricao;

}
