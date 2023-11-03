package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.services.CategoriaProdutoService;
import br.com.erpsystem.util.CriarCategoriaProdutoUtil;
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
@DisplayName("Testes para controller categoria produto")
class CategoriaProdutoControllerTest {

    @InjectMocks
    private CategoriaProdutoController categoriaProdutoController;

    @Mock
    private CategoriaProdutoService categoriaProdutoServiceMock;

    @BeforeEach
    void configurar(){
        CategoriaProdutoResponseDTO categoriaProdutoResponseDTO = CriarCategoriaProdutoUtil.retornaCategoriaProdutoResponseDTO();

        BDDMockito.when(categoriaProdutoServiceMock.listarTodasCategoriasProduto())
                .thenReturn(List.of(categoriaProdutoResponseDTO));


        BDDMockito.when(categoriaProdutoServiceMock.listarCategoriaProdutoPorId(ArgumentMatchers.isNotNull()))
                .thenReturn(categoriaProdutoResponseDTO);

        BDDMockito.when(categoriaProdutoServiceMock.criarCategoriaProduto(ArgumentMatchers.isA(CategoriaProdutoRequestDTO.class)))
                .thenReturn(categoriaProdutoResponseDTO);

        BDDMockito.when(categoriaProdutoServiceMock.alterarCategoriaProdutoPorId(ArgumentMatchers.isNotNull(),
                        ArgumentMatchers.isA(CategoriaProdutoRequestDTO.class)))
                .thenReturn(categoriaProdutoResponseDTO);

        BDDMockito.doNothing().when(categoriaProdutoServiceMock).deletarCategoriaProdutoPorId(ArgumentMatchers.isNotNull());
    }

    @Test
    @DisplayName("Deve retornar lista de categoria produto e codigo http 200 quando buscar todas categorias")
    void busca_deveRetornarListDeCategoriaProdutoRespondeDTOeCodigo200_quandoSucesso(){

        Assertions.assertThatCode(() -> categoriaProdutoController.listarTodasCategoriasProduto())
                .doesNotThrowAnyException();

        ResponseEntity<List<CategoriaProdutoResponseDTO>> entidadeResposta = categoriaProdutoController.listarTodasCategoriasProduto();

        List<CategoriaProdutoResponseDTO> respostaListaCategoriaProdutoDTO = entidadeResposta.getBody();

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaListaCategoriaProdutoDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaListaCategoriaProdutoDTO.get(0).getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaListaCategoriaProdutoDTO.get(0).getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaListaCategoriaProdutoDTO.get(0).getNome()).isEqualTo(categoriaProduto.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto e codigo http 200 quando buscar categoria por id")
    void busca_deveRetornarCategoriaProdutoRespondeDTOeCodigo200_quandoSucesso(){

        Assertions.assertThatCode(() -> categoriaProdutoController.listarCategoriaProdutoPorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<CategoriaProdutoResponseDTO> entidadeResposta = categoriaProdutoController.listarCategoriaProdutoPorId(1L);

        CategoriaProdutoResponseDTO respostaCategoriaProdutoDTO = entidadeResposta.getBody();

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaCategoriaProdutoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaCategoriaProdutoDTO.getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getNome()).isEqualTo(categoriaProduto.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto e codigo http 201 quando salvar unidade")
    void salvar_deveRetornarCategoriaProdutoRespondeDTOeCodigo201_quandoSucessoCriar(){

        Assertions.assertThatCode(() -> categoriaProdutoController.criarCategoriaProduto(CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<CategoriaProdutoResponseDTO> entidadeResposta = categoriaProdutoController
                .criarCategoriaProduto(CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO());

        CategoriaProdutoResponseDTO respostaCategoriaProdutoDTO = entidadeResposta.getBody();

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaCategoriaProdutoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaCategoriaProdutoDTO.getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getNome()).isEqualTo(categoriaProduto.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto e codigo http 200 quando alterar categoria")
    void alterar_deveRetornarCategoriaProdutoResponseDTOeCodigo200_quandoSucessoAlterar(){

        Assertions.assertThatCode(() -> categoriaProdutoController
                        .alterarCategoriaProdutoPorId(1L, CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<CategoriaProdutoResponseDTO> entidadeResposta = categoriaProdutoController
                .alterarCategoriaProdutoPorId(1L, CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO());

        CategoriaProdutoResponseDTO categoriaProdutoResponseDTO = entidadeResposta.getBody();

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(categoriaProdutoResponseDTO)
                .isNotNull()
                .isExactlyInstanceOf(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(categoriaProdutoResponseDTO.getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(categoriaProdutoResponseDTO.getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(categoriaProdutoResponseDTO.getNome()).isEqualTo(categoriaProduto.getNome());

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Deve retornar void e codigo http 200 quando excluir categoria")
    void excluir_deveRetornarVoidECodigo200_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> categoriaProdutoController.deletarCategoriaProdutoPorId(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entidadeResposta = categoriaProdutoController.deletarCategoriaProdutoPorId(1L);

        Assertions.assertThat(entidadeResposta).isNotNull();

        Assertions.assertThat(entidadeResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}