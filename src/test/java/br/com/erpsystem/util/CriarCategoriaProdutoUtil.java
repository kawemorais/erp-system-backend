package br.com.erpsystem.util;

import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.CategoriaProduto;

public class CriarCategoriaProdutoUtil {

    public static CategoriaProduto criarCategoriaProdutoParaSalvar(){
        return CategoriaProduto.builder()
                .codigo("01")
                .nome("MATERIA PRIMA")
                .build();
    }

    public static CategoriaProduto retornaCategoriaProdutoSalva(){
        return CategoriaProduto.builder()
                .id(1L)
                .codigo("01")
                .nome("MATERIA PRIMA")
                .build();
    }

    public static CategoriaProduto criarCategoriaProdutoInvalidaParaSalvar(){
        return CategoriaProduto.builder()
                .codigo("")
                .nome("")
                .build();
    }

    public static CategoriaProduto criarCategoriaProdutoNomeInvalidoParaSalvar(){
        return CategoriaProduto.builder()
                .codigo("01")
                .nome("M")
                .build();
    }

    public static CategoriaProdutoResponseDTO retornaCategoriaProdutoResponseDTO(){
        return CategoriaProdutoResponseDTO.builder()
                .id(retornaCategoriaProdutoSalva().getId())
                .codigo(retornaCategoriaProdutoSalva().getCodigo())
                .nome(retornaCategoriaProdutoSalva().getNome())
                .build();
    }

    public static CategoriaProdutoRequestDTO retornaCategoriaProdutoRequestDTO(){
        return CategoriaProdutoRequestDTO.builder()
                .codigo(criarCategoriaProdutoParaSalvar().getCodigo())
                .nome(criarCategoriaProdutoParaSalvar().getNome())
                .build();
    }
}
