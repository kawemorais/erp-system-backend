package br.com.erpsystem.almoxarifado.dtos.movimentacaoEstoqueDTO;

import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoEstoqueResponseDTO {

    private Long id;

    @JsonIncludeProperties({"codigo", "nome"})
    private Produto produto;

    @JsonIncludeProperties({"nome"})
    private Almoxarifado almoxarifado;

    private Double quantidade;

    private String tipoMovimentacao;

    private String observacao;

    private String nomePessoa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", locale = "pt_BR")
    private LocalDateTime dataHoraMovimentacao;
}
