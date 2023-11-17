package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Cargo;
import br.com.erpsystem.pessoa.models.Funcao;
import br.com.erpsystem.pessoa.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findAllByCargo(Cargo cargo);

    List<Funcionario> findAllByFuncao(Funcao funcao);
}
