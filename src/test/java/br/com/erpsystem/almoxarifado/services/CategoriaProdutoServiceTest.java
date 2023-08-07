package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.repositories.CategoriaProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para servico categoria unidade")
class CategoriaProdutoServiceTest {

    @InjectMocks
    private CategoriaProdutoService categoriaProdutoService;

    @Mock
    private CategoriaProdutoRepository categoriaProdutoRepositoryMock;

    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void configurar(){
        CategoriaProduto categoriaProdutoParaSalvar = CriarCategoriaProdutoUtil.criarCategoriaProdutoParaSalvar();
        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();
        CategoriaProdutoResponseDTO categoriaProdutoResponseDTO = CriarCategoriaProdutoUtil.retornaCategoriaProdutoResponseDTO();

        BDDMockito.when(categoriaProdutoRepositoryMock.findAll())
                .thenReturn(List.of(categoriaProduto));

        BDDMockito.when(categoriaProdutoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(categoriaProduto));

        BDDMockito.when(categoriaProdutoRepositoryMock.save(ArgumentMatchers.isA(CategoriaProduto.class)))
                .thenReturn(categoriaProduto);

        BDDMockito.doNothing().when(categoriaProdutoRepositoryMock).deleteById(ArgumentMatchers.isNotNull());


        BDDMockito.when(mapper.map(ArgumentMatchers.isA(CategoriaProdutoRequestDTO.class), ArgumentMatchers.isNotNull()))
                .thenReturn(categoriaProdutoParaSalvar);

        BDDMockito.when(mapper.map(ArgumentMatchers.isA(CategoriaProduto.class), ArgumentMatchers.isNotNull()))
                .thenReturn(categoriaProdutoResponseDTO);

        BDDMockito.when(produtoRepositoryMock.findAllByCategoriaProduto(ArgumentMatchers.isA(CategoriaProduto.class)))
                .thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve retornar lista de categoria produto quando buscar todas categorias")
    void busca_deveRetornarListaDeCategoriaProdutoResponseDTO_quandoSucesso(){

        Assertions.assertThatCode(() -> categoriaProdutoService.listarTodasCategoriasProduto())
                .doesNotThrowAnyException();

        List<CategoriaProdutoResponseDTO> respostaListaCategoriaProdutoDTO = categoriaProdutoService.listarTodasCategoriasProduto();

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaListaCategoriaProdutoDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaListaCategoriaProdutoDTO.get(0).getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaListaCategoriaProdutoDTO.get(0).getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaListaCategoriaProdutoDTO.get(0).getNome()).isEqualTo(categoriaProduto.getNome());
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto quando buscar categoria por id")
    void busca_deveRetornarCategoriaProdutoRespondeDTO_quandoSucesso(){

        Assertions.assertThatCode(() -> categoriaProdutoService.listarCategoriaProdutoPorId(1L))
                .doesNotThrowAnyException();

        CategoriaProdutoResponseDTO respostaCategoriaProdutoDTO = categoriaProdutoService.listarCategoriaProdutoPorId(1L);

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaCategoriaProdutoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaCategoriaProdutoDTO.getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getNome()).isEqualTo(categoriaProduto.getNome());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar categoria produto")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoCategoriaProdutoNaoEncontrada(){

        BDDMockito.when(categoriaProdutoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> categoriaProdutoService.listarCategoriaProdutoPorId(1L))
                .withMessageContaining("Registro categoria produto não encontrada");
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto quando salvar categoria")
    void salvar_deveRetornarCategoriaProdutoRespondeDTO_quandoSucessoCriar(){

        BDDMockito.when(categoriaProdutoRepositoryMock.findByCodigo(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatCode(() -> categoriaProdutoService
                        .criarCategoriaProduto(CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO()))
                        .doesNotThrowAnyException();

        CategoriaProdutoResponseDTO respostaCategoriaProdutoDTO = categoriaProdutoService
                .criarCategoriaProduto(CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO());

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaCategoriaProdutoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaCategoriaProdutoDTO.getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getNome()).isEqualTo(categoriaProduto.getNome());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando ja existir categoria produto com mesmo codigo")
    void salvar_deveRetornarExcecaoSolicitacaoInvalida_quandoExistirCategoriaProdutoMesmoCodigo(){
        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();
        CategoriaProdutoRequestDTO categoriaProdutoRequestDTO = CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO();

        BDDMockito.when(categoriaProdutoRepositoryMock.findByCodigo(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(categoriaProduto));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> categoriaProdutoService.criarCategoriaProduto(categoriaProdutoRequestDTO))
                .withMessageContaining("Ja existe categoria produto com este codigo");
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto quando alterar categoria")
    void alterar_deveRetornarUnidadeRespondeDTO_quandoSucessoAlterar(){

        Assertions.assertThatCode(
                        () -> categoriaProdutoService.alterarCategoriaProdutoPorId(1L,
                                CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO()))
                .doesNotThrowAnyException();

        CategoriaProdutoResponseDTO respostaCategoriaProdutoDTO = categoriaProdutoService
                .alterarCategoriaProdutoPorId(1L, CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO());

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.retornaCategoriaProdutoSalva();

        Assertions.assertThat(respostaCategoriaProdutoDTO)
                .isNotNull()
                .isExactlyInstanceOf(CategoriaProdutoResponseDTO.class);

        Assertions.assertThat(respostaCategoriaProdutoDTO.getId()).isEqualTo(categoriaProduto.getId());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getCodigo()).isEqualTo(categoriaProduto.getCodigo());
        Assertions.assertThat(respostaCategoriaProdutoDTO.getNome()).isEqualTo(categoriaProduto.getNome());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar categoria produto para alterar")
    void alterar_deveRetornarExcecaoSolicitacaoInvalida_quandoCategoriaProdutoNaoEncontrada(){
        CategoriaProdutoRequestDTO categoriaProdutoRequestDTO = CriarCategoriaProdutoUtil.retornaCategoriaProdutoRequestDTO();

        BDDMockito.when(categoriaProdutoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> categoriaProdutoService.alterarCategoriaProdutoPorId(1L, categoriaProdutoRequestDTO))
                .withMessageContaining("Registro categoria produto não encontrada");
    }

    @Test
    @DisplayName("Nao deve retornar erro quando excluir categoria produto")
    void excluir_naoDeveRetornarErro_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> categoriaProdutoService.deletarCategoriaProdutoPorId(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar categoria produto para excluir")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoCategoriaProdutoNaoEncontrada(){
        BDDMockito.when(categoriaProdutoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> categoriaProdutoService.deletarCategoriaProdutoPorId(1L))
                .withMessageContaining("Registro categoria produto não encontrada");
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando categoria produto estiver sendo usado em pelo menos um produto")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoCategoriaProdutoUsadaEmAlgumProduto(){
        BDDMockito.when(produtoRepositoryMock.findAllByCategoriaProduto(ArgumentMatchers.isNotNull()))
                .thenReturn(List.of(new Produto()));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> categoriaProdutoService.deletarCategoriaProdutoPorId(1L))
                .withMessageContaining("Existem produtos com essa categoria. Operação não pode ser finalizada");
    }

}