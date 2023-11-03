package br.com.erpsystem.almoxarifado.repositories;

import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlmoxarifadoRepository extends JpaRepository<Almoxarifado, Long> {

    Optional<Almoxarifado> findByCodigo(String codigo);

    Optional<Almoxarifado> findByNome(String nome);

    List<Almoxarifado> findAllByIsAtivo(Boolean isAtivo);

}
