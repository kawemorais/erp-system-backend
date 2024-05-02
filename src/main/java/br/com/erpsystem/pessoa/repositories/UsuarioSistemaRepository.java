package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.pessoa.models.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
    Optional<UsuarioSistema> findByUsuario(String usuario);

    Optional<UsuarioSistema> findByFuncionario(Funcionario funcionario);
}
