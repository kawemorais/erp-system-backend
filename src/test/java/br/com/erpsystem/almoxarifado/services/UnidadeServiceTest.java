package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeResponseDTO;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.UnidadeRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para servico unidade")
class UnidadeServiceTest {
    @InjectMocks
    private UnidadeService unidadeService;

    @Mock
    private UnidadeRepository unidadeRepositoryMock;

    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void configurar(){
        Unidade unidadeParaSalvar = CriarUnidadeUtil.criarUnidadeParaSalvar();
        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();
        UnidadeResponseDTO unidadeResponseDTO = CriarUnidadeUtil.retornaUnidadeResponseDTO();

        BDDMockito.when(unidadeRepositoryMock.findAll())
                .thenReturn(List.of(unidade));

        BDDMockito.when(unidadeRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(unidade));

        BDDMockito.when(unidadeRepositoryMock.save(ArgumentMatchers.isA(Unidade.class)))
                .thenReturn(unidade);

        BDDMockito.doNothing().when(unidadeRepositoryMock).deleteById(ArgumentMatchers.isNotNull());


        BDDMockito.when(mapper.map(ArgumentMatchers.isA(UnidadeRequestDTO.class), ArgumentMatchers.isNotNull()))
                .thenReturn(unidadeParaSalvar);

        BDDMockito.when(mapper.map(ArgumentMatchers.isA(Unidade.class), ArgumentMatchers.isNotNull()))
                .thenReturn(unidadeResponseDTO);

        BDDMockito.when(produtoRepositoryMock.findAllByUnidade(ArgumentMatchers.isA(Unidade.class)))
                .thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve retornar lista de unidades quando buscar todos unidades")
    void busca_deveRetornarListaDeUnidadeResponseDTO_quandoSucesso(){

        Assertions.assertThatCode(() -> unidadeService.listarTodasUnidades())
                .doesNotThrowAnyException();

        List<UnidadeResponseDTO> respostaListaUnidadeDTO = unidadeService.listarTodasUnidades();

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaListaUnidadeDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(UnidadeResponseDTO.class);

        Assertions.assertThat(respostaListaUnidadeDTO.get(0).getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaListaUnidadeDTO.get(0).getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaListaUnidadeDTO.get(0).getDescricao()).isEqualTo(unidade.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar uma unidade quando buscar unidade por id")
    void busca_deveRetornarUnidadeRespondeDTO_quandoSucesso(){

        Assertions.assertThatCode(() -> unidadeService.listarUnidadePorId(1L))
                .doesNotThrowAnyException();

        UnidadeResponseDTO respostaUnidadeDTO = unidadeService.listarUnidadePorId(1L);

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaUnidadeDTO)
                .isExactlyInstanceOf(UnidadeResponseDTO.class)
                .isNotNull();

        Assertions.assertThat(respostaUnidadeDTO.getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaUnidadeDTO.getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaUnidadeDTO.getDescricao()).isEqualTo(unidade.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar unidade")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoUnidadeNaoEncontrada(){

        BDDMockito.when(unidadeRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> unidadeService.listarUnidadePorId(1L))
                .withMessageContaining("Registro unidade não encontrada");
    }

    @Test
    @DisplayName("Deve retornar uma unidade quando salvar unidade")
    void salvar_deveRetornarUnidadeRespondeDTO_quandoSucessoCriar(){

        BDDMockito.when(unidadeRepositoryMock.findByNome(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatCode(() -> unidadeService.criarUnidade(CriarUnidadeUtil.retornaUnidadeRequestDTO()))
                .doesNotThrowAnyException();

        UnidadeResponseDTO respostaUnidadeDTO = unidadeService.criarUnidade(CriarUnidadeUtil.retornaUnidadeRequestDTO());

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaUnidadeDTO)
                .isExactlyInstanceOf(UnidadeResponseDTO.class)
                .isNotNull();

        Assertions.assertThat(respostaUnidadeDTO.getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaUnidadeDTO.getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaUnidadeDTO.getDescricao()).isEqualTo(unidade.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando ja existir unidade com mesmo nome")
    void salvar_deveRetornarExcecaoSolicitacaoInvalida_quandoExistirUnidadeMesmoNome(){
        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();
        UnidadeRequestDTO unidadeRequestDTO = CriarUnidadeUtil.retornaUnidadeRequestDTO();

        BDDMockito.when(unidadeRepositoryMock.findByNome(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(unidade));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> unidadeService.criarUnidade(unidadeRequestDTO))
                .withMessageContaining("Ja existe unidade com este nome");
    }

    @Test
    @DisplayName("Deve retornar uma unidade quando alterar unidade")
    void alterar_deveRetornarUnidadeRespondeDTO_quandoSucessoAlterar(){

        Assertions.assertThatCode(
                () -> unidadeService.alterarUnidadePorId(1L, CriarUnidadeUtil.retornaUnidadeRequestDTO()))
                .doesNotThrowAnyException();

        UnidadeResponseDTO respostaUnidadeDTO = unidadeService
                .alterarUnidadePorId(1L, CriarUnidadeUtil.retornaUnidadeRequestDTO());

        Unidade unidade = CriarUnidadeUtil.retornaUnidadeSalva();

        Assertions.assertThat(respostaUnidadeDTO)
                .isExactlyInstanceOf(UnidadeResponseDTO.class)
                .isNotNull();

        Assertions.assertThat(respostaUnidadeDTO.getId()).isEqualTo(unidade.getId());
        Assertions.assertThat(respostaUnidadeDTO.getNome()).isEqualTo(unidade.getNome());
        Assertions.assertThat(respostaUnidadeDTO.getDescricao()).isEqualTo(unidade.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar unidade para alterar")
    void alterar_deveRetornarExcecaoSolicitacaoInvalida_quandoUnidadeNaoEncontrada(){
        UnidadeRequestDTO unidadeRequestDTO = CriarUnidadeUtil.retornaUnidadeRequestDTO();

        BDDMockito.when(unidadeRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> unidadeService.alterarUnidadePorId(1L, unidadeRequestDTO))
                .withMessageContaining("Registro unidade não encontrada");
    }

    @Test
    @DisplayName("Nao deve retornar erro quando excluir unidade")
    void excluir_naoDeveRetornarErro_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> unidadeService.deletarUnidadePorId(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar unidade para excluir")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoUnidadeNaoEncontrada(){
        BDDMockito.when(unidadeRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> unidadeService.deletarUnidadePorId(1L))
                .withMessageContaining("Registro unidade não encontrada");
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando unidade estiver sendo usado em pelo menos um produto")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoUnidadeUsadaEmAlgumProduto(){
        BDDMockito.when(produtoRepositoryMock.findAllByUnidade(ArgumentMatchers.isNotNull()))
                .thenReturn(List.of(new Produto()));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> unidadeService.deletarUnidadePorId(1L))
                .withMessageContaining("Existem produtos com essa unidade. Operação não pode ser finalizada");
    }
}