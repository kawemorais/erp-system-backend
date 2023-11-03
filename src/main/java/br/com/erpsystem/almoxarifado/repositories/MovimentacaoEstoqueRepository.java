package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.MovimentacaoEstoque;
import br.com.erpsystem.almoxarifado.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    List<MovimentacaoEstoque> findAllByProduto(Produto produto);

}
