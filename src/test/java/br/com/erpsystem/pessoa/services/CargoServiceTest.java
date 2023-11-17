package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoRequestDTO;
import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoResponseDTO;
import br.com.erpsystem.pessoa.models.Cargo;
import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.pessoa.repositories.CargoRepository;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para servico cargo")
class CargoServiceTest {
    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepositoryMock;

    @Mock
    private FuncionarioRepository funcionarioRepositoryMock;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void configurar(){
        Cargo cargoParaSalvar = CriarCargoUtil.criarCargoParaSalvar();
        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();
        CargoResponseDTO cargoResponseDTO = CriarCargoUtil.retornaCargoResponseDTO();

        BDDMockito.when(cargoRepositoryMock.findAll())
                .thenReturn(List.of(cargo));

        BDDMockito.when(cargoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(cargo));

        BDDMockito.when(cargoRepositoryMock.save(ArgumentMatchers.isA(Cargo.class)))
                .thenReturn(cargo);

        BDDMockito.doNothing().when(cargoRepositoryMock).deleteById(ArgumentMatchers.isNotNull());


        BDDMockito.when(mapper.map(ArgumentMatchers.isA(CargoRequestDTO.class), ArgumentMatchers.isNotNull()))
                .thenReturn(cargoParaSalvar);

        BDDMockito.when(mapper.map(ArgumentMatchers.isA(Cargo.class), ArgumentMatchers.isNotNull()))
                .thenReturn(cargoResponseDTO);

        BDDMockito.when(funcionarioRepositoryMock.findAllByCargo(ArgumentMatchers.isA(Cargo.class)))
                .thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve retornar lista de cargos quando buscar todos cargos")
    void busca_deveRetornarListaDeCargoResponseDTO_quandoSucesso(){

        Assertions.assertThatCode(() -> cargoService.listarTodosCargos())
                .doesNotThrowAnyException();

        List<CargoResponseDTO> respostaListaCargoDTO = cargoService.listarTodosCargos();

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaListaCargoDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(CargoResponseDTO.class);

        Assertions.assertThat(respostaListaCargoDTO.get(0).getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaListaCargoDTO.get(0).getNome()).isEqualTo(cargo.getNome());
    }

    @Test
    @DisplayName("Deve retornar um cargo quando buscar cargo por id")
    void busca_deveRetornarCargoRespondeDTO_quandoSucesso(){

        Assertions.assertThatCode(() -> cargoService.listarCargoPorId(1L))
                .doesNotThrowAnyException();

        CargoResponseDTO respostaCargoDTO = cargoService.listarCargoPorId(1L);

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaCargoDTO)
                .isExactlyInstanceOf(CargoResponseDTO.class)
                .isNotNull();

        Assertions.assertThat(respostaCargoDTO.getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaCargoDTO.getNome()).isEqualTo(cargo.getNome());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar cargo")
    void busca_deveRetornarExcecaoSolicitacaoInvalida_quandoCargoNaoEncontrado(){

        BDDMockito.when(cargoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> cargoService.listarCargoPorId(1L))
                .withMessageContaining("Registro de cargo não encontrado");
    }

    @Test
    @DisplayName("Deve retornar um cargo quando salvar cargo")
    void salvar_deveRetornarCargoRespondeDTO_quandoSucessoCriar(){

        BDDMockito.when(cargoRepositoryMock.findByNome(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatCode(() -> cargoService.criarCargo(CriarCargoUtil.retornaCargoRequestDTO()))
                .doesNotThrowAnyException();

        CargoResponseDTO respostaCargoDTO = cargoService.criarCargo(CriarCargoUtil.retornaCargoRequestDTO());

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaCargoDTO)
                .isExactlyInstanceOf(CargoResponseDTO.class)
                .isNotNull();

        Assertions.assertThat(respostaCargoDTO.getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaCargoDTO.getNome()).isEqualTo(cargo.getNome());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando ja existir cargo com mesmo nome")
    void salvar_deveRetornarExcecaoSolicitacaoInvalida_quandoExistirCargoMesmoNome(){
        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();
        CargoRequestDTO cargoRequestDTO = CriarCargoUtil.retornaCargoRequestDTO();

        BDDMockito.when(cargoRepositoryMock.findByNome(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.of(cargo));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> cargoService.criarCargo(cargoRequestDTO))
                .withMessageContaining("Já existe cargo com este nome");
    }

    @Test
    @DisplayName("Deve retornar um cargo quando alterar cargo")
    void alterar_deveRetornarCargoRespondeDTO_quandoSucessoAlterar(){

        Assertions.assertThatCode(
                () -> cargoService.alterarCargoPorId(1L, CriarCargoUtil.retornaCargoRequestDTO()))
                .doesNotThrowAnyException();

        CargoResponseDTO respostaCargoDTO = cargoService
                .alterarCargoPorId(1L, CriarCargoUtil.retornaCargoRequestDTO());

        Cargo cargo = CriarCargoUtil.retornaCargoSalvo();

        Assertions.assertThat(respostaCargoDTO)
                .isExactlyInstanceOf(CargoResponseDTO.class)
                .isNotNull();

        Assertions.assertThat(respostaCargoDTO.getId()).isEqualTo(cargo.getId());
        Assertions.assertThat(respostaCargoDTO.getNome()).isEqualTo(cargo.getNome());
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar cargo para alterar")
    void alterar_deveRetornarExcecaoSolicitacaoInvalida_quandoCargoNaoEncontrada(){
        CargoRequestDTO cargoRequestDTO = CriarCargoUtil.retornaCargoRequestDTO();

        BDDMockito.when(cargoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> cargoService.alterarCargoPorId(1L, cargoRequestDTO))
                .withMessageContaining("Registro de cargo não encontrado");
    }

    @Test
    @DisplayName("Nao deve retornar erro quando excluir cargo")
    void excluir_naoDeveRetornarErro_quandoSucessoExcluir(){
        Assertions.assertThatCode(() -> cargoService.deletarCargoPorId(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando nao encontrar cargo para excluir")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoCargoNaoEncontrada(){
        BDDMockito.when(cargoRepositoryMock.findById(ArgumentMatchers.isNotNull()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> cargoService.deletarCargoPorId(1L))
                .withMessageContaining("Registro de cargo não encontrado");
    }

    @Test
    @DisplayName("Deve retornar ExcecaoSolicitacaoInvalida quando cargo estiver sendo usado em pelo menos um funcionario")
    void excluir_deveRetornarExcecaoSolicitacaoInvalida_quandoCargoUsadaEmAlgumFuncionario(){
        BDDMockito.when(funcionarioRepositoryMock.findAllByCargo(ArgumentMatchers.isNotNull()))
                .thenReturn(List.of(new Funcionario()));

        Assertions.assertThatExceptionOfType(ExcecaoSolicitacaoInvalida.class)
                .isThrownBy(() -> cargoService.deletarCargoPorId(1L))
                .withMessageContaining("Este cargo esta sendo utilizado. Operação não pode ser concluída");
    }
}