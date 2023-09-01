package br.com.erpsystem.almoxarifado.dtos.fichaTecnicaDTO;

import br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO.ItemFichaTecnicaResponseDTO;
import br.com.erpsystem.pessoa.models.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
public class FichaTecnicaResponseDTO {

    private Long id;

    private String nome;

    private List<ItemFichaTecnicaResponseDTO> itensFichaTecnica;

    private BigDecimal custoTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "pt_BR")
    private LocalDateTime dataCriacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "pt_BR")
    private LocalDateTime dataUltimaAlteracao;

    @JsonIncludeProperties({"id", "nome", "matricula"})
    private Funcionario pessoaCriacao;

}
