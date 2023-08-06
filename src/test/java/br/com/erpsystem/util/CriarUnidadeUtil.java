package br.com.erpsystem.util;

import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeResponseDTO;
import br.com.erpsystem.almoxarifado.models.Unidade;

public class CriarUnidadeUtil {

    public static Unidade criarUnidadeParaSalvar(){
        return Unidade.builder()
                .nome("UNID")
                .descricao("UNIDADE")
                .build();
    }

    public static Unidade retornaUnidadeSalva(){
        return Unidade.builder()
                .id(1L)
                .nome("UNID")
                .descricao("UNIDADE")
                .build();
    }

    public static Unidade criarUnidadeInvalidaParaSalvar(){
        return Unidade.builder()
                .nome("")
                .descricao("")
                .build();
    }

    public static Unidade criarUnidadeDescricaoInvalidaParaSalvar(){
        return Unidade.builder()
                .nome("UNID")
                .descricao("U")
                .build();
    }

    public static UnidadeResponseDTO retornaUnidadeResponseDTO(){
        return UnidadeResponseDTO.builder()
                .id(retornaUnidadeSalva().getId())
                .nome(retornaUnidadeSalva().getNome())
                .descricao(retornaUnidadeSalva().getDescricao())
                .build();
    }

    public static UnidadeRequestDTO retornaUnidadeRequestDTO(){
        return UnidadeRequestDTO.builder()
                .nome(criarUnidadeParaSalvar().getNome())
                .descricao(criarUnidadeParaSalvar().getDescricao())
                .build();
    }
}
