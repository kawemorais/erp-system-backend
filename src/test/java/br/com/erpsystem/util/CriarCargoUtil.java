package br.com.erpsystem.util;

import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoRequestDTO;
import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoResponseDTO;
import br.com.erpsystem.pessoa.models.Cargo;

public class CriarCargoUtil {

    public static Cargo criarCargoParaSalvar(){
        return Cargo.builder()
                .nome("CARGO")
                .build();
    }

    public static Cargo retornaCargoSalvo(){
        return Cargo.builder()
                .id(1L)
                .nome("CARGO")
                .build();
    }

    public static CargoResponseDTO retornaCargoResponseDTO(){
        return CargoResponseDTO.builder()
                .id(retornaCargoSalvo().getId())
                .nome(retornaCargoSalvo().getNome())
                .build();
    }

    public static CargoRequestDTO retornaCargoRequestDTO(){
        return CargoRequestDTO.builder()
                .nome(criarCargoParaSalvar().getNome())
                .build();
    }
}
