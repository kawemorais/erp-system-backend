package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Cargo;
import br.com.erpsystem.pessoa.models.Funcao;
import br.com.erpsystem.pessoa.models.Funcionario;
import br.com.erpsystem.pessoa.models.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findAllByCargo(Cargo cargo);

    List<Funcionario> findAllByFuncao(Funcao funcao);

    List<Setor> findAllBySetor(Setor setor);

    Optional<Funcionario> findByCpfAndRgAndReservistaAndEmailAndMatricula(String cpf, String rg, String reservista, String email, String matricula);

    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByRg(String rg);

    Optional<Funcionario> findByReservista(String reservista);

    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByMatricula(String matricula);
}
