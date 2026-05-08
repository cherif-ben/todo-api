package com.todo.config;

import com.todo.entity.StatutTache;
import com.todo.entity.Tache;
import com.todo.repository.TacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final TacheRepository tacheRepository;

    @Override
    public void run(String... args) {
        if (tacheRepository.count() == 0) {
            log.info("Insertion des données de démonstration...");

            tacheRepository.save(Tache.builder()
                .titre("Configurer la base de données PostgreSQL")
                .description("Installer PostgreSQL, créer la base lbca_db et configurer les accès.")
                .statut(StatutTache.TERMINE).build());

            tacheRepository.save(Tache.builder()
                .titre("Implémenter l'API REST des tâches")
                .description("Créer les endpoints CRUD avec Spring Boot et Spring Data JPA.")
                .statut(StatutTache.TERMINE).build());

            tacheRepository.save(Tache.builder()
                .titre("Ajouter la documentation Swagger")
                .description("Intégrer springdoc-openapi pour documenter automatiquement l'API.")
                .statut(StatutTache.EN_COURS).build());

            tacheRepository.save(Tache.builder()
                .titre("Rédiger le fichier README")
                .description("Expliquer comment installer et lancer le projet, les endpoints disponibles.")
                .statut(StatutTache.EN_COURS).build());

            tacheRepository.save(Tache.builder()
                .titre("Déployer sur un serveur de production")
                .description("Conteneuriser avec Docker et déployer sur un VPS ou service cloud.")
                .statut(StatutTache.A_FAIRE).build());

            tacheRepository.save(Tache.builder()
                .titre("Écrire les tests unitaires")
                .description("Couvrir le TacheService avec JUnit 5 et Mockito.")
                .statut(StatutTache.A_FAIRE).build());

            log.info("6 tâches de démonstration insérées.");
        }
    }
}
