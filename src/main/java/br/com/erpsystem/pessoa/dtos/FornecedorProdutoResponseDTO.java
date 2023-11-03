package br.com.erpsystem.pessoa.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FornecedorProdutoResponseDTO {
    private Long id;

    private String nomeFantasia;

    private String cnpj;
}
