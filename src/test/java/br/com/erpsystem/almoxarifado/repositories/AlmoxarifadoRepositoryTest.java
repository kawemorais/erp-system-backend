package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.util.CriarAlmoxarifadoUtil;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest()
@ActiveProfiles("test")
@DisplayName(value = "Testes para repositorio Almoxarifado")
class AlmoxarifadoRepositoryTest {

    @Autowired
    private AlmoxarifadoRepository almoxarifadoRepository;

    @Test
    @DisplayName("Testa se almoxarifado esta sendo persistido")
    void salvar_devePersistirAlmoxarifado_quandoSucesso(){
        Almoxarifado almoxarifadoParaSalvar = CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar();

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(almoxarifadoParaSalvar);

        Assertions.assertThat(almoxarifadoSalvo).isNotNull();
        Assertions.assertThat(almoxarifadoSalvo.getId()).isNotNull();
        Assertions.assertThat(almoxarifadoSalvo.getId()).isEqualTo(almoxarifadoParaSalvar.getId());
        Assertions.assertThat(almoxarifadoSalvo.getCodigo()).isNotNull();
        Assertions.assertThat(almoxarifadoSalvo.getCodigo()).isEqualTo(almoxarifadoParaSalvar.getCodigo());
        Assertions.assertThat(almoxarifadoSalvo.getNome()).isNotNull();
        Assertions.assertThat(almoxarifadoSalvo.getNome()).isEqualTo(almoxarifadoParaSalvar.getNome());
        Assertions.assertThat(almoxarifadoSalvo.getIsAtivo()).isNotNull();
        Assertions.assertThat(almoxarifadoSalvo.getIsAtivo()).isEqualTo(almoxarifadoParaSalvar.getIsAtivo());
    }

    @Test
    @DisplayName("Testa se almoxarifado esta sendo atualizado")
    void atualizar_deveAtualizarAlmoxarifado_quandoSucesso(){
        Almoxarifado almoxarifadoParaSalvar = CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar();

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(almoxarifadoParaSalvar);

        almoxarifadoSalvo.setCodigo("02");
        almoxarifadoSalvo.setNome("ALMOXARIFADO 02");
        almoxarifadoSalvo.setIsAtivo(Boolean.FALSE);

        Almoxarifado almoxarifadoAtualizada = almoxarifadoRepository.save(almoxarifadoSalvo);

        Assertions.assertThat(almoxarifadoAtualizada).isNotNull();

        Assertions.assertThat(almoxarifadoAtualizada.getCodigo()).isEqualTo(almoxarifadoSalvo.getCodigo());
        Assertions.assertThat(almoxarifadoAtualizada.getNome()).isEqualTo(almoxarifadoSalvo.getNome());
        Assertions.assertThat(almoxarifadoAtualizada.getIsAtivo()).isEqualTo(almoxarifadoSalvo.getIsAtivo());
    }

    @Test
    @DisplayName("Testa se almoxarifado esta sendo deletado")
    void deletar_deveDeletarAlmoxarifado_quandoSucesso(){

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar());

        almoxarifadoRepository.delete(almoxarifadoSalvo);

        Optional<Almoxarifado> almoxarifadoOptional = almoxarifadoRepository.findById(almoxarifadoSalvo.getId());

        Assertions.assertThat(almoxarifadoOptional).isNotPresent();
    }

    @Test
    @DisplayName("Deve retornar um almoxarifado quando passado id")
    void busca_deveRetornarAlmoxarifado_quandoPassadoIdValido(){

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar());

        Optional<Almoxarifado> almoxarifadoOptional = almoxarifadoRepository.findById(almoxarifadoSalvo.getId());

        Assertions.assertThat(almoxarifadoOptional).isPresent();

        Assertions.assertThat(almoxarifadoOptional.get()).isEqualTo(almoxarifadoSalvo);
    }

    @Test
    @DisplayName("Deve retornar lista de almoxarifados")
    void busca_deveRetornarListaAlmoxarifado_quandoSucesso(){

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar());

        List<Almoxarifado> todosAlmoxarifados = almoxarifadoRepository.findAll();

        Assertions.assertThat(todosAlmoxarifados).isNotEmpty();

        Assertions.assertThat(todosAlmoxarifados).hasAtLeastOneElementOfType(Almoxarifado.class);

        Assertions.assertThat(todosAlmoxarifados.get(0)).isEqualTo(almoxarifadoSalvo);
    }

    @Test
    @DisplayName("Deve retornar lista de almoxarifados ativos")
    void busca_deveRetornarListaAlmoxarifadoAtivo_quandoStatusAtivo(){

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(CriarAlmoxarifadoUtil.criarAlmoxarifadoParaSalvar());

        List<Almoxarifado> todosAlmoxarifadosAtivos = almoxarifadoRepository.findAllByIsAtivo(Boolean.TRUE);

        Assertions.assertThat(todosAlmoxarifadosAtivos).isNotEmpty();

        Assertions.assertThat(todosAlmoxarifadosAtivos).hasAtLeastOneElementOfType(Almoxarifado.class);

        Assertions.assertThat(todosAlmoxarifadosAtivos.get(0)).isEqualTo(almoxarifadoSalvo);
    }

    @Test
    @DisplayName("Deve retornar lista de almoxarifados inativos")
    void busca_deveRetornarListaAlmoxarifadoInativo_quandoStatusInativo(){

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(CriarAlmoxarifadoUtil.criarAlmoxarifadoInativoParaSalvar());

        List<Almoxarifado> todosAlmoxarifadosAtivos = almoxarifadoRepository.findAllByIsAtivo(Boolean.FALSE);

        Assertions.assertThat(todosAlmoxarifadosAtivos).isNotEmpty();

        Assertions.assertThat(todosAlmoxarifadosAtivos).hasAtLeastOneElementOfType(Almoxarifado.class);

        Assertions.assertThat(todosAlmoxarifadosAtivos.get(0)).isEqualTo(almoxarifadoSalvo);
    }

    @Test
    @DisplayName("Deve retornar nulo quando passado id invalido/inexistente")
    void busca_deveRetornarOptionalVazio_quandoPassadoIdInvalidoOuInexistente(){

        Optional<Almoxarifado> almoxarifadoOptional = almoxarifadoRepository.findById(1L);

        Assertions.assertThat(almoxarifadoOptional).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar ConstraintViolationException quando codigo e/ou nome forem nulos")
    void salvar_deveRetornarConstraintViolationException_quandoCodigoOuNomeForemNulos(){

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.criarAlmoxarifadoInvalidoParaSalvar();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> almoxarifadoRepository.save(almoxarifado))
                .withMessageContaining("Campo codigo não pode estar vazio")
                .withMessageContaining("Campo nome não pode estar vazio");
    }

    @Test
    @DisplayName("Deve retornar ConstraintViolationException quando nome for menor que dois caracteres")
    void salvar_deveRetornarConstraintViolationException_quandoNomeForMenorDoisCaracteres(){

        Almoxarifado almoxarifado = CriarAlmoxarifadoUtil.criarAlmoxarifadoNomeInvalidoParaSalvar();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> almoxarifadoRepository.save(almoxarifado))
                .withMessageContaining("Campo nome deve conter no minimo dois caracteres");
    }
}