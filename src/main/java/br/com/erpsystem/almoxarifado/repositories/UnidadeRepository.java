package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    Optional<Unidade> findByNome(String nome);

}
