package br.com.senai.lab365.labmedical.repositories;

import br.com.senai.lab365.labmedical.entities.ExameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends JpaRepository<ExameEntity, Long> {
}