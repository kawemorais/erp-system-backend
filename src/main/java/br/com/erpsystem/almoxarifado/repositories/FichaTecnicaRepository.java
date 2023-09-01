package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.FichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {
}
