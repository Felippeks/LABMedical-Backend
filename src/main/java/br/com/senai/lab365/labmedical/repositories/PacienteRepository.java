package br.com.senai.lab365.labmedical.repositories;

import br.com.senai.lab365.labmedical.entities.PacienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Long>, JpaSpecificationExecutor<PacienteEntity> {
    Page<PacienteEntity> findByNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable pageable);
    Page<PacienteEntity> findByCpfContaining(String cpf, Pageable pageable);

    @Query("SELECT p FROM PacienteEntity p WHERE p.telefone = :telefone OR p.email = :email")
    Page<PacienteEntity> findByTelefoneOrEmail(@Param("telefone") String telefone, @Param("email") String email, Pageable pageable);

    Optional<Object> findByCpf(String cpf);
}
