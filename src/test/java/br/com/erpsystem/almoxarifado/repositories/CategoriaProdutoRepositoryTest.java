package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.util.CriarCategoriaProdutoUtil;
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
@DisplayName(value = "Testes para repositorio CategoriaProduto")
class CategoriaProdutoRepositoryTest {
    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Test
    @DisplayName("Testa se categoria produto esta sendo persistida")
    void salvar_devePersistirCategoriaProduto_quandoSucesso(){
        CategoriaProduto categoriaProdutoParaSalvar = CriarCategoriaProdutoUtil.criarCategoriaProdutoParaSalvar();

        CategoriaProduto categoriaProdutoSalva = categoriaProdutoRepository.save(categoriaProdutoParaSalvar);

        Assertions.assertThat(categoriaProdutoSalva).isNotNull();
        Assertions.assertThat(categoriaProdutoSalva.getId()).isNotNull();
        Assertions.assertThat(categoriaProdutoSalva.getId()).isEqualTo(categoriaProdutoParaSalvar.getId());
        Assertions.assertThat(categoriaProdutoSalva.getCodigo()).isNotNull();
        Assertions.assertThat(categoriaProdutoSalva.getCodigo()).isEqualTo(categoriaProdutoParaSalvar.getCodigo());
        Assertions.assertThat(categoriaProdutoSalva.getNome()).isNotNull();
        Assertions.assertThat(categoriaProdutoSalva.getNome()).isEqualTo(categoriaProdutoParaSalvar.getNome());
    }

    @Test
    @DisplayName("Testa se categoria produto esta sendo atualizada")
    void atualizar_deveAtualizarCategoriaProduto_quandoSucesso(){
        CategoriaProduto categoriaProdutoParaSalvar = CriarCategoriaProdutoUtil.criarCategoriaProdutoParaSalvar();

        CategoriaProduto categoriaProdutoSalva = categoriaProdutoRepository.save(categoriaProdutoParaSalvar);

        categoriaProdutoSalva.setCodigo("02");
        categoriaProdutoSalva.setNome("MATERIA PRIMA");

        CategoriaProduto categoriaProdutoAtualizada = categoriaProdutoRepository.save(categoriaProdutoSalva);

        Assertions.assertThat(categoriaProdutoAtualizada).isNotNull();

        Assertions.assertThat(categoriaProdutoAtualizada.getCodigo()).isEqualTo(categoriaProdutoSalva.getCodigo());

        Assertions.assertThat(categoriaProdutoAtualizada.getNome()).isEqualTo(categoriaProdutoSalva.getNome());
    }

    @Test
    @DisplayName("Testa se categoria produto esta sendo deletada")
    void deletar_deveDeletarUnidade_quandoSucesso(){

        CategoriaProduto categoriaProdutoSalva = categoriaProdutoRepository
                .save(CriarCategoriaProdutoUtil.criarCategoriaProdutoParaSalvar());

        categoriaProdutoRepository.delete(categoriaProdutoSalva);

        Optional<CategoriaProduto> categoriaProdutoOptional = categoriaProdutoRepository.findById(categoriaProdutoSalva.getId());

        Assertions.assertThat(categoriaProdutoOptional).isNotPresent();
    }

    @Test
    @DisplayName("Deve retornar uma categoria produto quando passado id")
    void busca_deveRetornarCategoriaProduto_quandoPassadoIdValido(){

        CategoriaProduto categoriaProdutoSalva = categoriaProdutoRepository
                .save(CriarCategoriaProdutoUtil.criarCategoriaProdutoParaSalvar());

        Optional<CategoriaProduto> categoriaProdutoOptional = categoriaProdutoRepository.findById(categoriaProdutoSalva.getId());

        Assertions.assertThat(categoriaProdutoOptional).isPresent();

        Assertions.assertThat(categoriaProdutoOptional.get()).isEqualTo(categoriaProdutoSalva);
    }

    @Test
    @DisplayName("Deve retornar lista de categoria produto")
    void busca_deveRetornarListaCategoriaProduto_quandoSucesso(){

        CategoriaProduto categoriaProdutoSalva = categoriaProdutoRepository
                .save(CriarCategoriaProdutoUtil.criarCategoriaProdutoParaSalvar());

        List<CategoriaProduto> todasCategoriaProduto = categoriaProdutoRepository.findAll();

        Assertions.assertThat(todasCategoriaProduto)
                .isNotEmpty()
                .hasAtLeastOneElementOfType(CategoriaProduto.class);

        Assertions.assertThat(todasCategoriaProduto.get(0)).isEqualTo(categoriaProdutoSalva);
    }

    @Test
    @DisplayName("Deve retornar nula quando passado id invalido/inexistente")
    void busca_deveRetornarOptionalVazio_quandoPassadoIdInvalidoOuInexistente(){

        Optional<CategoriaProduto> categoriaProdutoOptional = categoriaProdutoRepository.findById(1L);

        Assertions.assertThat(categoriaProdutoOptional).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar ConstraintViolationException quando codigo e/ou nome forem nulos")
    void busca_deveRetornarConstraintViolationException_quandoCodigoOuNomeForemNulos(){

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.criarCategoriaProdutoInvalidaParaSalvar();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> categoriaProdutoRepository.save(categoriaProduto))
                .withMessageContaining("Campo codigo não pode estar vazio")
                .withMessageContaining("Campo nome não pode estar vazio");
    }

    @Test
    @DisplayName("Deve retornar ConstraintViolationException quando nome for menor que dois caracteres")
    void busca_deveRetornarConstraintViolationException_quandoNomeForMenorDoisCaracteres(){

        CategoriaProduto categoriaProduto = CriarCategoriaProdutoUtil.criarCategoriaProdutoNomeInvalidoParaSalvar();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> categoriaProdutoRepository.save(categoriaProduto))
                .withMessageContaining("Campo nome deve conter no minimo dois caracteres");

    }

}