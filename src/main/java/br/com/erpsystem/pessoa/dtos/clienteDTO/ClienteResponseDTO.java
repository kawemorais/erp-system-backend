package br.com.erpsystem.pessoa.dtos.clienteDTO;

import br.com.erpsystem.pessoa.models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseDTO {

    private Long id;

    private String telefone;

    private String celular;

    private String email;

    private Endereco endereco;

    private String nomeFantasia;

    private String razaoSocial;

    private String cnpj;

    private String inscricaoEstadual;

    private String inscricaoMunicipal;

    private Boolean isAtivo;
}
