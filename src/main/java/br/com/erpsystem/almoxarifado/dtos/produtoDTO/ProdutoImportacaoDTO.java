package br.com.erpsystem.almoxarifado.dtos.produtoDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoImportacaoDTO {

    private String codigo;

    private String nome;

    private String nomeUnidade;

    private String nomeCategoriaProduto;

    private String cnpjFornecedor;

    private String descricao;

    private Double peso;
}