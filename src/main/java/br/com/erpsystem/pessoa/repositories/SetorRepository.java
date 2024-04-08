package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    Optional<Object> findByNome(String nome);
}
