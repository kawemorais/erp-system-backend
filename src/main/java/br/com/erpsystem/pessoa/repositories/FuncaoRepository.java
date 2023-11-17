package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

    Optional<Object> findByNome(String nome);

}
