package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.util.CriarUnidadeUtil;
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
@DisplayName(value = "Testes para repositorio Unidade")
class UnidadeRepositoryTest {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Test
    @DisplayName("Testa se unidade esta sendo persistida")
    void salvar_devePersistirUnidade_quandoSucesso(){
        Unidade unidadeParaSalvar = CriarUnidadeUtil.criarUnidadeParaSalvar();

        Unidade unidadeSalva = unidadeRepository.save(unidadeParaSalvar);

        Assertions.assertThat(unidadeSalva).isNotNull();
        Assertions.assertThat(unidadeSalva.getId()).isNotNull();
        Assertions.assertThat(unidadeSalva.getId()).isEqualTo(unidadeParaSalvar.getId());
        Assertions.assertThat(unidadeSalva.getNome()).isNotNull();
        Assertions.assertThat(unidadeSalva.getNome()).isEqualTo(unidadeParaSalvar.getNome());
        Assertions.assertThat(unidadeSalva.getDescricao()).isNotNull();
        Assertions.assertThat(unidadeSalva.getDescricao()).isEqualTo(unidadeParaSalvar.getDescricao());
    }

    @Test
    @DisplayName("Testa se unidade esta sendo atualizada")
    void atualizar_deveAtualizarUnidade_quandoSucesso(){
        Unidade unidadeParaSalvar = CriarUnidadeUtil.criarUnidadeParaSalvar();

        Unidade unidadeSalva = unidadeRepository.save(unidadeParaSalvar);

        unidadeSalva.setNome("PÇ");
        unidadeSalva.setDescricao("PEÇA");

        Unidade unidadeAtualizada = unidadeRepository.save(unidadeSalva);

        Assertions.assertThat(unidadeAtualizada).isNotNull();

        Assertions.assertThat(unidadeAtualizada.getNome()).isEqualTo(unidadeSalva.getNome());

        Assertions.assertThat(unidadeAtualizada.getDescricao()).isEqualTo(unidadeSalva.getDescricao());
    }

    @Test
    @DisplayName("Testa se unidade esta sendo deletada")
    void deletar_deveDeletarUnidade_quandoSucesso(){

        Unidade unidadeSalva = unidadeRepository.save(CriarUnidadeUtil.criarUnidadeParaSalvar());

        unidadeRepository.delete(unidadeSalva);

        Optional<Unidade> unidadeOptional = unidadeRepository.findById(unidadeSalva.getId());

        Assertions.assertThat(unidadeOptional).isNotPresent();
    }

    @Test
    @DisplayName("Deve retornar uma unidade quando passado id")
    void busca_deveRetornarUnidade_quandoPassadoIdValido(){

        Unidade unidadeSalva = unidadeRepository.save(CriarUnidadeUtil.criarUnidadeParaSalvar());

        Optional<Unidade> unidadeOptional = unidadeRepository.findById(unidadeSalva.getId());

        Assertions.assertThat(unidadeOptional).isPresent();

        Assertions.assertThat(unidadeOptional.get()).isEqualTo(unidadeSalva);
    }

    @Test
    @DisplayName("Deve retornar lista de unidades")
    void busca_deveRetornarListaUnidades_quandoSucesso(){

        Unidade unidadeSalva = unidadeRepository.save(CriarUnidadeUtil.criarUnidadeParaSalvar());

        List<Unidade> todasUnidades = unidadeRepository.findAll();

        Assertions.assertThat(todasUnidades).isNotEmpty();

        Assertions.assertThat(todasUnidades).hasAtLeastOneElementOfType(Unidade.class);

        Assertions.assertThat(todasUnidades.get(0)).isEqualTo(unidadeSalva);
    }

    @Test
    @DisplayName("Deve retornar nula quando passado id invalido/inexistente")
    void busca_deveRetornarOptionalVazio_quandoPassadoIdInvalidoOuInexistente(){

        Optional<Unidade> unidadeOptional = unidadeRepository.findById(1L);

        Assertions.assertThat(unidadeOptional).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar ConstraintViolationException quando nome e/ou descricao forem nulos")
    void busca_deveRetornarConstraintViolationException_quandoNomeOuDescricaoForemNulos(){

        Unidade unidade = CriarUnidadeUtil.criarUnidadeInvalidaParaSalvar();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> unidadeRepository.save(unidade))
                .withMessageContaining("Campo nome não pode estar vazio")
                .withMessageContaining("Campo descricao não pode estar vazio");


    }

    @Test
    @DisplayName("Deve retornar ConstraintViolationException quando descricao for menor que dois caracteres")
    void busca_deveRetornarConstraintViolationException_quandoDescricaoForMenorDoisCaracteres(){

        Unidade unidade = CriarUnidadeUtil.criarUnidadeDescricaoInvalidaParaSalvar();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> unidadeRepository.save(unidade))
                .withMessageContaining("Campo descricao deve conter no minimo dois caracteres");

    }

}