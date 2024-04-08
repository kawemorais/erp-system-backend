package br.com.erpsystem.pessoa.dtos.enderecoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViaCepEnderecoDTO {

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;
}
