package br.com.erpsystem.util;

import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;

public class CriarAlmoxarifadoUtil {

    public static Almoxarifado criarAlmoxarifadoParaSalvar(){
        return Almoxarifado.builder()
                .codigo("01")
                .nome("ALMOXARIFADO 01")
                .isAtivo(true)
                .build();
    }

    public static Almoxarifado criarAlmoxarifadoInativoParaSalvar(){
        return Almoxarifado.builder()
                .codigo("01")
                .nome("ALMOXARIFADO 01")
                .isAtivo(false)
                .build();
    }

    public static Almoxarifado retornaAlmoxarifadoSalvo(){
        return Almoxarifado.builder()
                .id(1L)
                .codigo("01")
                .nome("ALMOXARIFADO 01")
                .isAtivo(true)
                .build();
    }

    public static Almoxarifado criarAlmoxarifadoInvalidoParaSalvar(){
        return Almoxarifado.builder()
                .codigo("")
                .nome("")
                .build();
    }

    public static Almoxarifado criarAlmoxarifadoNomeInvalidoParaSalvar(){
        return Almoxarifado.builder()
                .codigo("01")
                .nome("A")
                .build();
    }

    public static AlmoxarifadoResponseDTO retornaAlmoxarifadoResponseDTO(){
        return AlmoxarifadoResponseDTO.builder()
                .id(retornaAlmoxarifadoSalvo().getId())
                .codigo(retornaAlmoxarifadoSalvo().getCodigo())
                .nome(retornaAlmoxarifadoSalvo().getNome())
                .isAtivo(retornaAlmoxarifadoSalvo().getIsAtivo())
                .build();
    }

    public static AlmoxarifadoRequestDTO retornaAlmoxarifadoRequestDTO(){
        return AlmoxarifadoRequestDTO.builder()
                .codigo(criarAlmoxarifadoParaSalvar().getCodigo())
                .nome(criarAlmoxarifadoParaSalvar().getNome())
                .isAtivo(criarAlmoxarifadoParaSalvar().getIsAtivo())
                .build();
    }
}
