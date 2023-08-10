package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.repositories.AlmoxarifadoRepository;
import br.com.erpsystem.almoxarifado.repositories.EstoqueProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para servico almoxarifado")
class AlmoxarifadoServiceTest {

    @InjectMocks
    private AlmoxarifadoService almoxarifadoService;

    @Mock
    private AlmoxarifadoRepository almoxarifadoRepositoryMock;

    @Mock
    private EstoqueProdutoRepository estoqueProdutoRepositoryMock;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void configurar(){
        Almoxarifado almoxarifadoParaSalvar = CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar();
        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();
        AlmoxarifadoResponseDTO almoxarifadoResponseDTO = CriarAlmoxarifadoUtil.retornaAlmoxarifadoResponseDTO();

        BDDMockito.when(almoxarifadoRepositoryMock.findAll())
                .thenReturn(List.of(almoxarifado));

        BDDMockito.when(almoxarifadoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(almoxarifado));

        BDDMockito.when(almoxarifadoRepositoryMock.findAllByIsAtivo(ArgumentMatchers.isNotNull()))
                .thenReturn(List.of(almoxarifado));

        BDDMockito.when(almoxarifadoRepositoryMock.save(ArgumentMatchers.isA(Almoxarifado.class)))
                .thenReturn(almoxarifado);

        BDDMockito.doNothing().when(almoxarifadoRepositoryMock).deleteById(ArgumentMatchers.isNotNull());


        BDDMockito.when(mapper.map(ArgumentMatchers.isA(AlmoxarifadoRequestDTO.class), ArgumentMatchers.isNotNull()))
                .thenReturn(almoxarifadoParaSalvar);

        BDDMockito.when(mapper.map(ArgumentMatchers.isA(Almoxarifado.class), ArgumentMatchers.isNotNull()))
                .thenReturn(almoxarifadoResponseDTO);

        BDDMockito.when(estoqueProdutoRepositoryMock.findAllByAlmoxarifado(ArgumentMatchers.isA(Almoxarifado.class)))
                .thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve retornar lista de almoxarifado quando buscar todos almoxarifados")
    void busca_deveRetornarListaDeAlmoxarifadoResponseDTO_quandoSucessoBuscarTodos(){

        Assertions.assertThatCode(() -> almoxarifadoService.listarTodosAlmoxarifados())
                .doesNotThrowAnyException();

        List<AlmoxarifadoResponseDTO> respostaListaAlmoxarifadoDTO = almoxarifadoService.listarTodosAlmoxarifados();

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
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado quando buscar almoxarifado por id")
    void busca_deveRetornarAlmoxarifadoRespondeDTO_quandoSucessoBuscarId(){

        Assertions.assertThatCode(() -> almoxarifadoService.listarAlmoxarifadoPorId(1L))
                .doesNotThrowAnyException();

        AlmoxarifadoResponseDTO almoxarifadoResponseDTO = almoxarifadoService.listarAlmoxarifadoPorId(1L);

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(almoxarifadoResponseDTO)
                .isNotNull()
                .isExactlyInstanceOf(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(almoxarifadoResponseDTO.getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(almoxarifadoResponseDTO.getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(almoxarifadoResponseDTO.getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(almoxarifadoResponseDTO.getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());
    }

    @Test
    @DisplayName("Deve retornar lista almoxarifado com mesmo staus quando buscar almoxarifado por status")
    void busca_deveRetornarListaDeAlmoxarifadoResponseDTO_quandoSucessoBuscarStatus(){

        Assertions.assertThatCode(() -> almoxarifadoService.listarTodosAlmoxarifadosPorStatus("true"))
                .doesNotThrowAnyException();

        List<AlmoxarifadoResponseDTO> respostaListaAlmoxarifadoDTO = almoxarifadoService.listarTodosAlmoxarifadosPorStatus("true");

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
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar almoxarifado por id")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoIdAlmoxarifadoNaoEncontrado(){

        BDDMockito.when(almoxarifadoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.listarAlmoxarifadoPorId(1L))
                .withMessageContaining("Registro almoxarifado não encontrado");
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando passado parametro requisicao incorreto")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoParametroIncorreto(){

        BDDMockito.when(almoxarifadoRepositoryMock.findAllByIsAtivo(ArgumentMatchers.any()))
                .thenThrow(new ExcecaoSolicitacaoInvalida("Parametro incorreto"));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.listarTodosAlmoxarifadosPorStatus("diferentedetrueoufalse"))
                .withMessageContaining("Parametro incorreto");
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado quando salvar almoxarifado")
    void salvar_deveRetornarAlmoxarifadoRespondeDTO_quandoSucessoCriar(){

        BDDMockito.when(almoxarifadoRepositoryMock.findByCodigo(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        BDDMockito.when(almoxarifadoRepositoryMock.findByNome(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatCode(() -> almoxarifadoService.criarAlmoxarifado(CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO()))
                .doesNotThrowAnyException();

        AlmoxarifadoResponseDTO respostaAlmoxarifadoResponseDTO = almoxarifadoService
                .criarAlmoxarifado(CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO());

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaAlmoxarifadoResponseDTO)
                .isNotNull()
                .isExactlyInstanceOf(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando ja existir almoxarifado com mesmo codigo")
    void salvar_deveRetornarExcecaoSolicitacaoInvalida_quandoExistirAlmoxarifadoMesmoCodigo(){
        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar();
        AlmoxarifadoRequestDTO almoxarifadoRequestDTO = CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO();

        BDDMockito.when(almoxarifadoRepositoryMock.findByCodigo(ArgumentMatchers.isNotNull()))
                        .thenReturn(Optional.of(almoxarifado));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.criarAlmoxarifado(almoxarifadoRequestDTO))
                .withMessageContaining("Ja existe almoxarifado com este codigo");
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando ja existir almoxarifado com mesmo nome")
    void salvar_deveRetornarExcecaoSolicitacaoInvalida_quandoExistirAlmoxarifadoMesmoNome(){
        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar();
        AlmoxarifadoRequestDTO almoxarifadoRequestDTO = CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO();

        BDDMockito.when(almoxarifadoRepositoryMock.findByNome(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(almoxarifado));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.criarAlmoxarifado(almoxarifadoRequestDTO))
                .withMessageContaining("Ja existe almoxarifado com este nome");
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado quando alterar almoxarifado")
    void alterar_deveRetornarAlmoxarifadoResponseDTO_quandoSucessoAlterar(){

        Assertions.assertThatCode(
                        () -> almoxarifadoService.alterarAlmoxarifadoPorId(1L,
                                CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO()))
                .doesNotThrowAnyException();

        AlmoxarifadoResponseDTO respostaAlmoxarifadoResponseDTO = almoxarifadoService
                .alterarAlmoxarifadoPorId(1L, CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO());

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.retornaAlmoxarifadoSalvo();

        Assertions.assertThat(respostaAlmoxarifadoResponseDTO)
                .isNotNull()
                .isExactlyInstanceOf(AlmoxarifadoResponseDTO.class);

        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getId()).isEqualTo(almoxarifado.getId());
        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getCodigo()).isEqualTo(almoxarifado.getCodigo());
        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getNome()).isEqualTo(almoxarifado.getNome());
        Assertions.assertThat(respostaAlmoxarifadoResponseDTO.getIsAtivo()).isEqualTo(almoxarifado.getIsAtivo());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar almoxarifado para alterar")
    void alterar_deveRetornarExcecaoSolicitacaoInvalida_quandoAlmoxarifadoNaoEncontrado(){
        AlmoxarifadoRequestDTO almoxarifadoRequestDTO = CriarAlmoxarifadoUtil.retornaAlmoxarifadoRequestDTO();

        BDDMockito.when(almoxarifadoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.alterarAlmoxarifadoPorId(1L, almoxarifadoRequestDTO))
                .withMessageContaining("Registro almoxarifado não encontrado");
    }

    @Test
    @DisplayName("Nao deve retornar erro quando excluir categoria produto")
    void excluir_naoDeveRetornarErro_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> almoxarifadoService.deletarAlmoxarifadoPorId(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar almoxarifado para excluir")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoAlmoxarifadoNaoEncontrado(){
        BDDMockito.when(almoxarifadoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.deletarAlmoxarifadoPorId(1L))
                .withMessageContaining("Registro almoxarifado não encontrado");
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando almoxarifado estiver com algum produto deste almoxarifado em estoque")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoAlmoxarifadoUsadoEmAlgumEstoqueProduto(){
        BDDMockito.when(estoqueProdutoRepositoryMock.findAllByAlmoxarifado(ArgumentMatchers.isNotNull()))
                .thenReturn(List.of(new EstoqueProduto()));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> almoxarifadoService.deletarAlmoxarifadoPorId(1L))
                .withMessageContaining("Existem produtos em estoque neste almoxarifado. Operação não pode ser finalizada");
    }

}