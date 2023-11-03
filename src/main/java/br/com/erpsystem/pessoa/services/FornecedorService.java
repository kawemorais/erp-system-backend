package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.models.Fornecedor;
import br.com.erpsystem.pessoa.repositories.FornecedorRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public Fornecedor retornaFornecedorSeExistentePorCnpj(String cnpj){
        return fornecedorRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Fornecedor não encontrado"));
    }
}
