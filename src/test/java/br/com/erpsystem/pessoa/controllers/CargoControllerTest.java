package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoRequestDTO;
import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoResponseDTO;
import br.com.erpsystem.pessoa.models.Cargo;
import br.com.erpsystem.pessoa.services.CargoService;
import br.com.erpsystem.util.CriarCargoUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para controller cargo")
class CargoControllerTest {

    @InjectMocks
    private CargoController cargoController;

    @Mock
    private CargoService cargoServiceMock;

    @BeforeEach
    void configurar(){
        CargoResponseDTO cargoResponseDTO = CriarCargoUtil.retornaCargoResponseDTO();

        BDDMockito.when(cargoServiceMock.listarTodosCargos())
                .thenReturn(List.of(cargoResponseDTO));


        BDDMockito.when(cargoServiceMock.listarCargoPorId(ArgumentMatchers.isNotNull()))
                .thenReturn(cargoResponseDTO);

        BDDMockito.when(cargoServiceMock.criarCargo(ArgumentMatchers.isA(CargoRequestDTO.class)))
                .thenReturn(cargoResponseDTO);

        BDDMockito.when(cargoServiceMock.alterarCargoPorId(ArgumentMatchers.isNotNull(),
                        ArgumentMatchers.isA(CargoRequestDTO.class)))
                .thenReturn(cargoResponseDTO);

        BDDMockito.doNothing().when(cargoServiceMock).deletarCargoPorId(ArgumentMatchers.isNotNull());

    }

    @Test
    @DisplayName("Deve retornar lista de cargos e codigo http 200 quando buscar todos cargos")
    void busca_deveRetornarListDeCargoRespondeDTOeCodigo200_quandoSucesso(){

        Assertions.assertThatCode(() -> cargoController.listarTodosCargos())
                .doesNotThrowAnyException();

        ResponseEntity<List<CargoResponseDTO>> entidadeResposta = cargoController.listarTodosCargos();

        List<CargoResponseDTO> respostaListaCargoDTO = entidadeResposta.getBody();

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaListaCargoDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(CargoResponseDTO.class);

        Assertions.assertThat(respostaListaCargoDTO.get(0).getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaListaCargoDTO.get(0).getNome()).isEqualTo(cargo.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar um cargo e codigo http 200 quando buscar cargo por id")
    void busca_deveRetornarCargoRespondeDTOeCodigo200_quandoSucesso(){

        Assertions.assertThatCode(() -> cargoController.listarCargoPorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<CargoResponseDTO> entidadeResposta = cargoController.listarCargoPorId(1L);

        CargoResponseDTO respostaCargoDTO = entidadeResposta.getBody();

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaCargoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CargoResponseDTO.class);

        Assertions.assertThat(respostaCargoDTO.getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaCargoDTO.getNome()).isEqualTo(cargo.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Deve retornar um cargo e codigo http 201 quando salvar cargo")
    void salvar_deveRetornarCargoRespondeDTOeCodigo201_quandoSucessoCriar(){

        Assertions.assertThatCode(() -> cargoController.criarCargo(CriarCargoUtil.retornaCargoRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<CargoResponseDTO> entidadeResposta = cargoController
                .criarCargo(CriarCargoUtil.retornaCargoRequestDTO());

        CargoResponseDTO respostaCargoDTO = entidadeResposta.getBody();

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaCargoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CargoResponseDTO.class);

        Assertions.assertThat(respostaCargoDTO.getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaCargoDTO.getNome()).isEqualTo(cargo.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Deve retornar um cargo e codigo http 200 quando alterar cargo")
    void alterar_deveRetornarCargoResponseDTOeCodigo200_quandoSucessoAlterar(){

        Assertions.assertThatCode(() -> cargoController
                        .alterarCargoPorId(1L, CriarCargoUtil.retornaCargoRequestDTO()))
                        .doesNotThrowAnyException();

        ResponseEntity<CargoResponseDTO> entidadeResposta = cargoController
                .alterarCargoPorId(1L, CriarCargoUtil.retornaCargoRequestDTO());

        CargoResponseDTO respostaCargoDTO = entidadeResposta.getBody();

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaCargoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CargoResponseDTO.class);

        Assertions.assertThat(respostaCargoDTO.getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaCargoDTO.getNome()).isEqualTo(cargo.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar void e codigo http 200 quando excluir cargo")
    void excluir_deveRetornarVoidECodigo200_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> cargoController.deletarCargoPorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entidadeResposta = cargoController.deletarCargoPorId(1L);

        Assertions.assertThat(entidadeResposta).isNotNull();

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}