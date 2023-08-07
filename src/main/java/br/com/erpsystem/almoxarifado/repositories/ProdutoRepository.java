package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByUnidade(Unidade unidade);

    List<Produto> findAllByCategoriaProduto(CategoriaProduto categoriaProduto);
}
