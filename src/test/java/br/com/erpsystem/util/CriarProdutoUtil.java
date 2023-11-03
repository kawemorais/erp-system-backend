package br.com.erpsystem.util;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.pessoa.models.Fornecedor;
import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.sistema.models.enums.StatusSistema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CriarProdutoUtil {
    public static Produto retornaProdutoSalvo(){
        return Produto.builder()
                .id(1L)
                .codigo("")
                .nome("")
                .unidade(new Unidade())
                .categoriaProduto(new CategoriaProduto())
                .fornecedor(new Fornecedor())
                .descricao("")
                .peso(0.0)
                .fichaTecnica(null)
                .estoques(null)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .pessoaCriacao(new Funcionario())
                .isAtivo(true)
                .precoUltimaCompra(BigDecimal.ZERO)
                .custoProduto(BigDecimal.ZERO)
                .status(StatusSistema.NORMAL.getStatusId())
                .build();
    }
}
