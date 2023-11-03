package br.com.erpsystem.util;

import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.models.Produto;

import java.math.BigDecimal;

public class CriarEstoqueProdutoUtil {

    public static EstoqueProduto criarEstoqueProdutoParaSalvar(){
        return EstoqueProduto.builder()
                .almoxarifado(new Almoxarifado())
                .produto(new Produto())
                .quantidade(1.0)
                .valorUnitarioProdutoEstoque(BigDecimal.ZERO)
                .valorTotalProdutoEstoque(BigDecimal.ZERO)
                .estoqueMinimo(0.0)
                .estoqueMaximo(0.0)
                .estoquePontoPedido(0.0)
                .locCorredor("")
                .locPrateleira("")
                .locBox("")
                .build();
    }

    public static EstoqueProduto retornaEstoqueProdutoSalvo(){
        return EstoqueProduto.builder()
                .id(1L)
                .almoxarifado(new Almoxarifado())
                .produto(new Produto())
                .quantidade(1.0)
                .valorUnitarioProdutoEstoque(BigDecimal.ZERO)
                .valorTotalProdutoEstoque(BigDecimal.ZERO)
                .estoqueMinimo(0.0)
                .estoqueMaximo(0.0)
                .estoquePontoPedido(0.0)
                .locCorredor("")
                .locPrateleira("")
                .locBox("")
                .build();
    }

    public static EstoqueProdutoResponseDTO retornaEstoqueProdutoResponseDTO(){
        return EstoqueProdutoResponseDTO.builder()
                .id(retornaEstoqueProdutoSalvo().getId())
                .produto(retornaEstoqueProdutoSalvo().getProduto())
                .almoxarifado(retornaEstoqueProdutoSalvo().getAlmoxarifado())
                .quantidade(retornaEstoqueProdutoSalvo().getQuantidade())
                .valorUnitarioProdutoEstoque(retornaEstoqueProdutoSalvo().getValorUnitarioProdutoEstoque())
                .valorTotalProdutoEstoque(retornaEstoqueProdutoSalvo().getValorTotalProdutoEstoque())
                .estoqueMinimo(retornaEstoqueProdutoSalvo().getEstoqueMinimo())
                .estoqueMaximo(retornaEstoqueProdutoSalvo().getEstoqueMaximo())
                .estoquePontoPedido(retornaEstoqueProdutoSalvo().getEstoquePontoPedido())
                .locCorredor(retornaEstoqueProdutoSalvo().getLocCorredor())
                .locPrateleira(retornaEstoqueProdutoSalvo().getLocPrateleira())
                .locBox(retornaEstoqueProdutoSalvo().getLocBox())
                .build();
    }

    public static EstoqueProdutoRequestDTO retornaEstoqueProdutoRequestDTO() {
        return EstoqueProdutoRequestDTO.builder()
                .fkAlmoxarifado(1L)
                .fkProduto(1L)
                .quantidade(1.0)
                .valorUnitarioCompra(BigDecimal.ZERO)
                .estoqueMinimo(0.0)
                .estoqueMaximo(0.0)
                .estoquePontoPedido(0.0)
                .locCorredor("")
                .locPrateleira("")
                .locBox("")
                .build();
    }
}
