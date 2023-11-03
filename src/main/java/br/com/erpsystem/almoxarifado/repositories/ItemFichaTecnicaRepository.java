package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.ItemFichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFichaTecnicaRepository extends JpaRepository<ItemFichaTecnica, Long> {
}
