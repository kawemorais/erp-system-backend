package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.repositories.AlmoxarifadoRepository;
import br.com.erpsystem.almoxarifado.repositories.EstoqueProdutoRepository;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import br.com.erpsystem.util.CriarAlmoxarifadoUtil;
import br.com.erpsystem.util.CriarEstoqueProdutoUtil;
import br.com.erpsystem.util.CriarProdutoUtil;
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

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para servico estoque produto")
class EstoqueProdutoServiceTest {

    @InjectMocks
    private EstoqueProdutoService estoqueProdutoService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Mock
    private EstoqueProdutoRepository estoqueProdutoRepositoryMock;

    @Mock
    private AlmoxarifadoRepository almoxarifadoRepositoryMock;

    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void configurar() {

        EstoqueProduto estoqueProdutoSalvo = CriarEstoqueProdutoUtil.retornaEstoqueProdutoSalvo();

        BDDMockito.when(estoqueProdutoRepositoryMock.findAllByAlmoxarifado(ArgumentMatchers.isA(Almoxarifado.class)))
                .thenReturn(List.of(estoqueProdutoSalvo));

        BDDMockito.when(estoqueProdutoRepositoryMock.findAllByProduto(ArgumentMatchers.isA(Produto.class)))
                .thenReturn(List.of(estoqueProdutoSalvo));

        BDDMockito.when(estoqueProdutoRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(estoqueProdutoSalvo));

        BDDMockito.when(estoqueProdutoRepositoryMock.findByAlmoxarifadoAndProduto(ArgumentMatchers.isNotNull(), ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        BDDMockito.when(estoqueProdutoRepositoryMock.save(ArgumentMatchers.isA(EstoqueProduto.class)))
                .thenReturn(CriarEstoqueProdutoUtil.retornaEstoqueProdutoSalvo());

        BDDMockito.when(almoxarifadoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.ofNullable(CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo()));

        BDDMockito.when(produtoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.ofNullable(CriarProdutoUtil.retornaProdutoSalvo()));

        BDDMockito.when(produtoRepositoryMock.findByIdAndStatusEquals(ArgumentMatchers.isNotNull(), ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(CriarProdutoUtil.retornaProdutoSalvo()));

        BDDMockito.when(mapper.map(ArgumentMatchers.isA(EstoqueProduto.class), ArgumentMatchers.isNotNull()))
                .thenReturn(CriarEstoqueProdutoUtil.retornaEstoqueProdutoResponseDTO());
    }

    @Test
    @DisplayName("Deve retornar lista de estoque produto quando buscar todos almoxarifados usando parametros")
    void busca_deveRetornarListaDeEstoqueProdutoDTO_quandoSucessoBuscarTodosPorParametro(){

        Assertions.assertThatCode(() -> estoqueProdutoService.listarTodosEstoquesPorParametro("almoxarifado", 1L))
                .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> estoqueProdutoService.listarTodosEstoquesPorParametro("produto", 1L))
                .doesNotThrowAnyException();

        List<EstoqueProdutoResponseDTO> respostaListaEstoqueProdutoA = estoqueProdutoService.listarTodosEstoquesPorParametro("almoxarifado", 1L);
        List<EstoqueProdutoResponseDTO> respostaListaEstoqueProdutoP = estoqueProdutoService.listarTodosEstoquesPorParametro("produto", 1L);

        EstoqueProduto estoqueProdutoSalvo = CriarEstoqueProdutoUtil.retornaEstoqueProdutoSalvo();

        Assertions.assertThat(respostaListaEstoqueProdutoA)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(EstoqueProdutoResponseDTO.class);

        Assertions.assertThat(respostaListaEstoqueProdutoP)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(EstoqueProdutoResponseDTO.class);

        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getId()).isEqualTo(estoqueProdutoSalvo.getId());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getAlmoxarifado()).isEqualTo(estoqueProdutoSalvo.getAlmoxarifado());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getProduto()).isEqualTo(estoqueProdutoSalvo.getProduto());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getQuantidade()).isEqualTo(estoqueProdutoSalvo.getQuantidade());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getValorUnitarioProdutoEstoque()).isEqualTo(estoqueProdutoSalvo.getValorUnitarioProdutoEstoque());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getValorTotalProdutoEstoque()).isEqualTo(estoqueProdutoSalvo.getValorTotalProdutoEstoque());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getEstoqueMinimo()).isEqualTo(estoqueProdutoSalvo.getEstoqueMinimo());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getEstoqueMaximo()).isEqualTo(estoqueProdutoSalvo.getEstoqueMaximo());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getEstoquePontoPedido()).isEqualTo(estoqueProdutoSalvo.getEstoquePontoPedido());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getEstoquePontoPedido()).isEqualTo(estoqueProdutoSalvo.getEstoquePontoPedido());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getLocCorredor()).isEqualTo(estoqueProdutoSalvo.getLocCorredor());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getLocPrateleira()).isEqualTo(estoqueProdutoSalvo.getLocPrateleira());
        Assertions.assertThat(respostaListaEstoqueProdutoA.get(0).getLocBox()).isEqualTo(estoqueProdutoSalvo.getLocBox());

        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getId()).isEqualTo(estoqueProdutoSalvo.getId());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getAlmoxarifado()).isEqualTo(estoqueProdutoSalvo.getAlmoxarifado());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getProduto()).isEqualTo(estoqueProdutoSalvo.getProduto());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getQuantidade()).isEqualTo(estoqueProdutoSalvo.getQuantidade());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getValorUnitarioProdutoEstoque()).isEqualTo(estoqueProdutoSalvo.getValorUnitarioProdutoEstoque());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getValorTotalProdutoEstoque()).isEqualTo(estoqueProdutoSalvo.getValorTotalProdutoEstoque());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getEstoqueMinimo()).isEqualTo(estoqueProdutoSalvo.getEstoqueMinimo());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getEstoqueMaximo()).isEqualTo(estoqueProdutoSalvo.getEstoqueMaximo());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getEstoquePontoPedido()).isEqualTo(estoqueProdutoSalvo.getEstoquePontoPedido());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getEstoquePontoPedido()).isEqualTo(estoqueProdutoSalvo.getEstoquePontoPedido());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getLocCorredor()).isEqualTo(estoqueProdutoSalvo.getLocCorredor());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getLocPrateleira()).isEqualTo(estoqueProdutoSalvo.getLocPrateleira());
        Assertions.assertThat(respostaListaEstoqueProdutoP.get(0).getLocBox()).isEqualTo(estoqueProdutoSalvo.getLocBox());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando parametro incorreto")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoParametroIncorreto(){

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> estoqueProdutoService.listarTodosEstoquesPorParametro("qualquer", 1L))
                .withMessageContaining("Parametro inválido");
    }

    @Test
    @DisplayName("Deve retornar um estoque produto quando buscado por id")
    void busca_deveRetornarEstoqueProdutoDTO_quandoSucessoBuscarId(){

        Assertions.assertThatCode(() -> estoqueProdutoService.buscarEstoqueProdutoPorId(1L))
                .doesNotThrowAnyException();

        EstoqueProdutoResponseDTO estoqueProdutoResponseDTO = estoqueProdutoService.buscarEstoqueProdutoPorId(1L);

        EstoqueProduto estoqueProduto = CriarEstoqueProdutoUtil.retornaEstoqueProdutoSalvo();

        Assertions.assertThat(estoqueProdutoResponseDTO)
                .isNotNull()
                .isExactlyInstanceOf(EstoqueProdutoResponseDTO.class);

        Assertions.assertThat(estoqueProdutoResponseDTO.getAlmoxarifado()).isEqualTo(estoqueProduto.getAlmoxarifado());
        Assertions.assertThat(estoqueProdutoResponseDTO.getId()).isEqualTo(estoqueProduto.getId());
        Assertions.assertThat(estoqueProdutoResponseDTO.getProduto()).isEqualTo(estoqueProduto.getProduto());
        Assertions.assertThat(estoqueProdutoResponseDTO.getQuantidade()).isEqualTo(estoqueProduto.getQuantidade());
        Assertions.assertThat(estoqueProdutoResponseDTO.getValorUnitarioProdutoEstoque()).isEqualTo(estoqueProduto.getValorUnitarioProdutoEstoque());
        Assertions.assertThat(estoqueProdutoResponseDTO.getValorTotalProdutoEstoque()).isEqualTo(estoqueProduto.getValorTotalProdutoEstoque());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoqueMinimo()).isEqualTo(estoqueProduto.getEstoqueMinimo());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoqueMaximo()).isEqualTo(estoqueProduto.getEstoqueMaximo());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoquePontoPedido()).isEqualTo(estoqueProduto.getEstoquePontoPedido());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoquePontoPedido()).isEqualTo(estoqueProduto.getEstoquePontoPedido());
        Assertions.assertThat(estoqueProdutoResponseDTO.getLocCorredor()).isEqualTo(estoqueProduto.getLocCorredor());
        Assertions.assertThat(estoqueProdutoResponseDTO.getLocPrateleira()).isEqualTo(estoqueProduto.getLocPrateleira());
        Assertions.assertThat(estoqueProdutoResponseDTO.getLocBox()).isEqualTo(estoqueProduto.getLocBox());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando id não existir")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoIdNaoEncontrado(){

        BDDMockito.when(estoqueProdutoRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> estoqueProdutoService.buscarEstoqueProdutoPorId(1L))
                .withMessageContaining("Estoque produto não encontrado");
    }

    @Test
    @DisplayName("Deve retornar um estoque produto quando salvar")
    void salvar_deveRetornarEstoqueProdutoDTO_quandoSucessoCriar(){

        Assertions.assertThatCode(() -> estoqueProdutoService.criarNovoEstoqueProduto(CriarEstoqueProdutoUtil.retornaEstoqueProdutoRequestDTO()))
                .doesNotThrowAnyException();

        EstoqueProdutoResponseDTO estoqueProdutoResponseDTO = estoqueProdutoService
                .criarNovoEstoqueProduto(CriarEstoqueProdutoUtil.retornaEstoqueProdutoRequestDTO());

        EstoqueProduto estoqueProduto = CriarEstoqueProdutoUtil.retornaEstoqueProdutoSalvo();

        Assertions.assertThat(estoqueProdutoResponseDTO)
                .isNotNull()
                .isExactlyInstanceOf(EstoqueProdutoResponseDTO.class);

        Assertions.assertThat(estoqueProdutoResponseDTO.getId()).isEqualTo(estoqueProduto.getId());
        Assertions.assertThat(estoqueProdutoResponseDTO.getAlmoxarifado()).isEqualTo(estoqueProduto.getAlmoxarifado());
        Assertions.assertThat(estoqueProdutoResponseDTO.getProduto()).isEqualTo(estoqueProduto.getProduto());
        Assertions.assertThat(estoqueProdutoResponseDTO.getQuantidade()).isEqualTo(estoqueProduto.getQuantidade());
        Assertions.assertThat(estoqueProdutoResponseDTO.getValorUnitarioProdutoEstoque()).isEqualTo(estoqueProduto.getValorUnitarioProdutoEstoque());
        Assertions.assertThat(estoqueProdutoResponseDTO.getValorTotalProdutoEstoque()).isEqualTo(estoqueProduto.getValorTotalProdutoEstoque());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoqueMinimo()).isEqualTo(estoqueProduto.getEstoqueMinimo());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoqueMaximo()).isEqualTo(estoqueProduto.getEstoqueMaximo());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoquePontoPedido()).isEqualTo(estoqueProduto.getEstoquePontoPedido());
        Assertions.assertThat(estoqueProdutoResponseDTO.getEstoquePontoPedido()).isEqualTo(estoqueProduto.getEstoquePontoPedido());
        Assertions.assertThat(estoqueProdutoResponseDTO.getLocCorredor()).isEqualTo(estoqueProduto.getLocCorredor());
        Assertions.assertThat(estoqueProdutoResponseDTO.getLocPrateleira()).isEqualTo(estoqueProduto.getLocPrateleira());
        Assertions.assertThat(estoqueProdutoResponseDTO.getLocBox()).isEqualTo(estoqueProduto.getLocBox());

    }

}