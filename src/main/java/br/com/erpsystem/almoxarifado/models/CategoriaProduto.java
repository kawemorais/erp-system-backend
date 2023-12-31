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
@Table(name = "tb_categorias_produto")
@Builder
public class CategoriaProduto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 10)
    @NotBlank(message = "Campo codigo não pode estar vazio")
    private String codigo;

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank(message = "Campo nome não pode estar vazio")
    @Size(message = "Campo nome deve conter no minimo dois caracteres", min = 2)
    private String nome;

}
