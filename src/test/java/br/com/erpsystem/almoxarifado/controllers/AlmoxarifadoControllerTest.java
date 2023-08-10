package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.services.AlmoxarifadoService;
import br.com.erpsystem.util.CriarAlmoxarifadoUtil;
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
@DisplayName("Testes para controller almoxarifado")
class AlmoxarifadoControllerTest {

    @InjectMocks
    private AlmoxarifadoController almoxarifadoController;
    
    @Mock
    private AlmoxarifadoService almoxarifadoServiceMock;

    @BeforeEach
    void configurar(){
        AlmoxarifadoResponseDTO almoxarifadoResponseDTO = CriarAlmoxarifadoUtil.retornaAlmoxarifadoResponseDTO();

        BDDMockito.when(almoxarifadoServiceMock.listarTodosAlmoxarifados())
                .thenReturn(List.of(almoxarifadoResponseDTO));

        BDDMockito.when(almoxarifadoServiceMock.listarTodosAlmoxarifadosPorStatus(ArgumentMatchers.isNotNull()))
                .thenReturn(List.of(almoxarifadoResponseDTO));

        BDDMockito.when(almoxarifadoServiceMock.listarAlmoxarifadoPorId(ArgumentMatchers.isNotNull()))
                .thenReturn(almoxarifadoResponseDTO);

        BDDMockito.when(almoxarifadoServiceMock.criarAlmoxarifado(ArgumentMatchers.isA(AlmoxarifadoRequestDTO.class)))
                .thenReturn(almoxarifadoResponseDTO);

        BDDMockito.when(almoxarifadoServiceMock.alterarAlmoxarifadoPorId(ArgumentMatchers.isNotNull(),
                        ArgumentMatchers.isA(AlmoxarifadoRequestDTO.class)))
                .thenReturn(almoxarifadoResponseDTO);

        BDDMockito.doNothing().when(almoxarifadoServiceMock).deletarAlmoxarifadoPorId(ArgumentMatchers.isNotNull());
    }
    
    @Test
    @DisplayName("Deve retornar lista de almoxarifado e codigo http 200 quando buscar todos almoxarifados")
    void busca_deveRetornarListDeAlmoxarifadoRespondeDTOeCodigo200_quandoSucessoBuscarTodos(){

        Assertions.assertThatCode(() -> almoxarifadoController.listarTodosAlmoxarifados())
                .doesNotThrowAnyException();

        ResponseEntity<List<AlmoxarifadoResponseDTO>> entidadeResposta = almoxarifadoController.listarTodosAlmoxarifados();

        List<AlmoxarifadoResponseDTO> respostaListaAlmoxarifadoDTO = entidadeResposta.getBody();

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaListaAlmoxarifadoDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar lista de almoxarifado e codigo http 200 quando buscar todos almoxarifados com mesmo status")
    void busca_deveRetornarListDeAlmoxarifadoRespondeDTOeCodigo200_quandoSucessoBuscarStatus(){

        Assertions.assertThatCode(() -> almoxarifadoController.listarTodosAlmoxarifadosPorStatus("true"))
                .doesNotThrowAnyException();

        ResponseEntity<List<AlmoxarifadoResponseDTO>> entidadeResposta = almoxarifadoController.listarTodosAlmoxarifadosPorStatus("true");

        List<AlmoxarifadoResponseDTO> respostaListaAlmoxarifadoDTO = entidadeResposta.getBody();

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaListaAlmoxarifadoDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaListaAlmoxarifadoDTO.get(0).getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado e codigo http 200 quando buscar almoxarifado por id")
    void busca_deveRetornarAlmoxarifadoResponseDTOeCodigo200_quandoSucessoBuscarId(){

        Assertions.assertThatCode(() -> almoxarifadoController.listarAlmoxarifadoPorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<AlmoxarifadoResponseDTO> entidadeResposta = almoxarifadoController.listarAlmoxarifadoPorId(1L);

        AlmoxarifadoResponseDTO respostaAlmoxarifadoDTO = entidadeResposta.getBody();

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaAlmoxarifadoDTO)
                .isNotNull()
                .isExactlyInstanceOf(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaAlmoxarifadoDTO.getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado e codigo http 201 quando salvar almoxarifado")
    void salvar_deveRetornarAlmoxarifadoRespondeDTOeCodigo201_quandoSucessoCriar(){

        Assertions.assertThatCode(() -> almoxarifadoController.criarAlmoxarifado(CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<AlmoxarifadoResponseDTO> entidadeResposta = almoxarifadoController
                .criarAlmoxarifado(CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO());

        AlmoxarifadoResponseDTO respostaAlmoxarifadoDTO = entidadeResposta.getBody();

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaAlmoxarifadoDTO)
                .isNotNull()
                .isExactlyInstanceOf(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaAlmoxarifadoDTO.getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getNome()).isEqualTo(almoxarifado.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado e codigo http 200 quando alterar almoxarifado")
    void alterar_deveRetornarAlmoxarifadoResponseDTOeCodigo200_quandoSucessoAlterar(){

        Assertions.assertThatCode(() -> almoxarifadoController
                        .alterarAlmoxarifadoPorId(1L, CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<AlmoxarifadoResponseDTO> entidadeResposta = almoxarifadoController
                .alterarAlmoxarifadoPorId(1L, CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO());

        AlmoxarifadoResponseDTO respostaAlmoxarifadoDTO = entidadeResposta.getBody();

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaAlmoxarifadoDTO)
                .isNotNull()
                .isExactlyInstanceOf(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaAlmoxarifadoDTO.getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaAlmoxarifadoDTO.getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar void e codigo http 200 quando excluir almoxarifado")
    void excluir_deveRetornarVoidECodigo200_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> almoxarifadoController.deletarAlmoxarifadoPorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entidadeResposta = almoxarifadoController.deletarAlmoxarifadoPorId(1L);

        Assertions.assertThat(entidadeResposta).isNotNull();

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    
}