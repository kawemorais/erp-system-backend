package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.enderecoDTO.ViaCepEnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "via-cep-consumer", url = "https://viacep.com.br/ws/")
public interface ViaCepService {

    @GetMapping(value = "/{cep}/json")
    ViaCepEnderecoDTO buscarEnderecoPorCep(@PathVariable("cep") String cep);

}
