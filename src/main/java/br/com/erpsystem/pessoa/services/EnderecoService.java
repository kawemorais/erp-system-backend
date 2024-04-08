package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.enderecoDTO.EnderecoRequestDTO;
import br.com.erpsystem.pessoa.dtos.enderecoDTO.ViaCepEnderecoDTO;
import br.com.erpsystem.pessoa.models.Endereco;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final ViaCepService viaCepService;

    public EnderecoService(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    public Endereco gerarEndereco(EnderecoRequestDTO enderecoRequestDTO){

        ViaCepEnderecoDTO enderecoViaCep = viaCepService.buscarEnderecoPorCep(enderecoRequestDTO.getCep());

        return Endereco.builder()
                .cep(enderecoRequestDTO.getCep())
                .logradouro(enderecoViaCep.getLogradouro())
                .bairro(enderecoViaCep.getBairro())
                .cidade(enderecoViaCep.getLocalidade())
                .uf(enderecoViaCep.getUf())
                .numero(enderecoRequestDTO.getNumero())
                .complemento(enderecoRequestDTO.getComplemento())
                .build();
    }
}
