package br.com.erpsystem.almoxarifado.dtos.produtoDTO;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.models.FichaTecnica;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.pessoa.dtos.FornecedorProdutoResponseDTO;
import br.com.erpsystem.pessoa.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResponseDTO {

    private Long id;

    private String codigo;

    private String nome;

    private Unidade unidade;

    private CategoriaProduto categoriaProduto;

    private FornecedorProdutoResponseDTO fornecedor;

    private String descricao;

    private Double peso;

    private FichaTecnica fichaTecnica;

    private List<EstoqueProduto> estoques;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "pt_BR")
    private LocalDateTime dataCriacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "pt_BR")
    private LocalDateTime dataAlteracao;

    private Funcionario funcionario;

    private Boolean isAtivo;

    private BigDecimal precoUltimaCompra;

    private BigDecimal custoProduto;

    private int status;
}
