package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    Optional<CategoriaProduto> findByCodigo(String codigo);

}
