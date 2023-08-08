package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.models.EstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Long> {

    List<EstoqueProduto> findAllByAlmoxarifado(Almoxarifado almoxarifado);

}
