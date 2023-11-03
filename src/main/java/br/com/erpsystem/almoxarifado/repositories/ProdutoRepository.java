package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByUnidade(Unidade unidade);

    List<Produto> findAllByCategoriaProduto(CategoriaProduto categoriaProduto);

    Optional<Produto> findByCodigo(String codigo);

    List<Produto> findAllByStatusEquals(int status);

    Optional<Produto> findByIdAndStatusEquals(Long id, int status);
}
