package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    Optional<CategoriaProduto> findByCodigo(String codigo);

}
