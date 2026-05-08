package com.todo.repository;

import com.todo.entity.StatutTache;
import com.todo.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {

    // Filtrer les tâches par statut
    List<Tache> findByStatutOrderByCreatedAtDesc(StatutTache statut);

    // Toutes les tâches triées par date de création
    List<Tache> findAllByOrderByCreatedAtDesc();

    // Rechercher par titre (insensible à la casse)
    List<Tache> findByTitreContainingIgnoreCaseOrderByCreatedAtDesc(String titre);
}
