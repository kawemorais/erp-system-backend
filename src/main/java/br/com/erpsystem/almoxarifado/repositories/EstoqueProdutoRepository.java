package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Long> {

    List<EstoqueProduto> findAllByAlmoxarifado(Almoxarifado almoxarifado);

    List<EstoqueProduto> findAllByProduto(Produto produto);

    Optional<EstoqueProduto> findByAlmoxarifadoAndProduto(Almoxarifado almoxarifado, Produto produto);
}
