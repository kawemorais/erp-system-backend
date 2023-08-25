package br.com.erpsystem.almoxarifado.dtos.produtoDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoRequestDTO {

    @NotEmpty(message = "O campo codigo não pode estar vazio")
    private String codigo;

    @NotEmpty(message = "O campo nome não pode estar vazio")
    private String nome;

    @NotNull(message = "O campo unidade não pode estar vazio")
    private Long fkUnidade;

    @NotNull(message = "O campo categoria produto não pode estar vazio")
    private Long fkCategoriaProduto;

    private Long fkFornecedor;

    private String descricao;

    private Double peso;

    private Long fkPessoaCriacao;

    private Boolean isAtivo;
}