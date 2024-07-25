package br.com.senai.lab365.labmedical.repositories;

import br.com.senai.lab365.labmedical.entities.ConsultaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {

    @PersistenceContext
    EntityManager entityManager = null;

    default List<ConsultaEntity> findByMotivoConsulta(String motivoConsulta) {
        String jpql = "SELECT c FROM ConsultaEntity c WHERE c.motivoConsulta = :motivoConsulta";
        TypedQuery<ConsultaEntity> query = entityManager.createQuery(jpql, ConsultaEntity.class);
        query.setParameter("motivoConsulta", motivoConsulta);
        return query.getResultList();
    }
}