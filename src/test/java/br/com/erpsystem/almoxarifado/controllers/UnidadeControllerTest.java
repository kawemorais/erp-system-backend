package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeResponseDTO;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.almoxarifado.services.UnidadeService;
import br.com.erpsystem.util.CriarUnidadeUtil;
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
@DisplayName("Testes para controller unidade")
class UnidadeControllerTest {

    @InjectMocks
    private UnidadeController unidadeController;

    @Mock
    private UnidadeService unidadeServiceMock;

    @BeforeEach
    void configurar(){
        UnidadeResponseDTO unidadeResponseDTO = CriarUnidadeUtil.retornaUnidadeResponseDTO();

        BDDMockito.when(unidadeServiceMock.listarTodasUnidades())
                .thenReturn(List.of(unidadeResponseDTO));


        BDDMockito.when(unidadeServiceMock.listarUnidadePorId(ArgumentMatchers.isNotNull()))
                .thenReturn(unidadeResponseDTO);

        BDDMockito.when(unidadeServiceMock.criarUnidade(ArgumentMatchers.isA(UnidadeRequestDTO.class)))
                .thenReturn(unidadeResponseDTO);

        BDDMockito.when(unidadeServiceMock.alterarUnidadePorId(ArgumentMatchers.isNotNull(),
                        ArgumentMatchers.isA(UnidadeRequestDTO.class)))
                .thenReturn(unidadeResponseDTO);

        BDDMockito.doNothing().when(unidadeServiceMock).deletarUnidadePorId(ArgumentMatchers.isNotNull());

    }

    @Test
    @DisplayName("Deve retornar lista de unidades e codigo http 200 quando buscar todos unidades")
    void busca_deveRetornarListDeUnidadeRespondeDTOeCodigo200_quandoSucesso(){

        Assertions.assertThatCode(() -> unidadeController.listarTodasUnidades())
                .doesNotThrowAnyException();

        ResponseEntity<List<UnidadeResponseDTO>> entidadeResposta = unidadeController.listarTodasUnidades();

        List<UnidadeResponseDTO> respostaListaUnidadeDTO = entidadeResposta.getBody();

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaListaUnidadeDTO).isNotNull();

        Assertions.assertThat(respostaListaUnidadeDTO)
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(UnidadeResponseDTO.class);

        Assertions.assertThat(respostaListaUnidadeDTO.get(0).getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaListaUnidadeDTO.get(0).getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaListaUnidadeDTO.get(0).getDescricao()).isEqualTo(unidade.getDescricao());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar uma unidade e codigo http 200 quando buscar unidade por id")
    void busca_deveRetornarUnidadeRespondeDTOeCodigo200_quandoSucesso(){

        Assertions.assertThatCode(() -> unidadeController.listarUnidadePorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<UnidadeResponseDTO> entidadeResposta = unidadeController.listarUnidadePorId(1L);

        UnidadeResponseDTO respostaUnidadeDTO = entidadeResposta.getBody();

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaUnidadeDTO).isNotNull();

        Assertions.assertThat(respostaUnidadeDTO).isExactlyInstanceOf((UnidadeResponseDTO.class));

        Assertions.assertThat(respostaUnidadeDTO.getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaUnidadeDTO.getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaUnidadeDTO.getDescricao()).isEqualTo(unidade.getDescricao());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Deve retornar uma unidade e codigo http 201 quando salvar unidade")
    void salvar_deveRetornarUnidadeRespondeDTOeCodigo200_quandoSucessoCriar(){

        Assertions.assertThatCode(() -> unidadeController.criarUnidade(CriarUnidadeUtil.retornaUnidadeRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<UnidadeResponseDTO> entidadeResposta = unidadeController
                .criarUnidade(CriarUnidadeUtil.retornaUnidadeRequestDTO());

        UnidadeResponseDTO respostaUnidadeDTO = entidadeResposta.getBody();

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();


        Assertions.assertThat(respostaUnidadeDTO).isNotNull();

        Assertions.assertThat(respostaUnidadeDTO).isExactlyInstanceOf((UnidadeResponseDTO.class));

        Assertions.assertThat(respostaUnidadeDTO.getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaUnidadeDTO.getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaUnidadeDTO.getDescricao()).isEqualTo(unidade.getDescricao());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    @DisplayName("Deve retornar uma unidade e codigo http 201 quando alterar unidade")
    void alterar_deveRetornarUnidadeRespondeDTOeCodigo200_quandoSucessoAlterar(){

        Assertions.assertThatCode(() -> unidadeController
                        .alterarUnidadePorId(1L, CriarUnidadeUtil.retornaUnidadeRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<UnidadeResponseDTO> entidadeResposta = unidadeController
                .alterarUnidadePorId(1L, CriarUnidadeUtil.retornaUnidadeRequestDTO());

        UnidadeResponseDTO respostaUnidadeDTO = entidadeResposta.getBody();

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaUnidadeDTO).isNotNull();

        Assertions.assertThat(respostaUnidadeDTO).isExactlyInstanceOf((UnidadeResponseDTO.class));

        Assertions.assertThat(respostaUnidadeDTO.getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaUnidadeDTO.getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaUnidadeDTO.getDescricao()).isEqualTo(unidade.getDescricao());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Deve retornar void e codigo http 200 quando excluir unidade")
    void excluir_deveRetornarVoidECodigo200_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> unidadeController.deletarUnidadePorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entidadeResposta = unidadeController.deletarUnidadePorId(1L);

        Assertions.assertThat(entidadeResposta).isNotNull();

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}