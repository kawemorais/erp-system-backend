package br.com.erpsystem.pessoa.repositories;

import br.com.erpsystem.pessoa.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRazaoSocialAndCnpjAndInscricaoEstadualAndInscricaoMunicipal(String razaoSocial, String cnpj, String inscricaoEstadual, String inscricaoMunicipal);

    Optional<Cliente> findByCnpj(String cnpj);

    Optional<Cliente> findByRazaoSocial(String razaoSocial);

    Optional<Cliente> findByInscricaoEstadual(String inscricaoEstadual);

    Optional<Cliente> findByInscricaoMunicipal(String inscricaoMunicipal);
}

