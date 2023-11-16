package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findByNome(String nome);

}
