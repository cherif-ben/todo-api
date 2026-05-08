package com.todo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gestion de Tâches — To-Do List")
                .description("""
                    API REST complète permettant la gestion d'une liste de tâches.
                    
                    **Fonctionnalités :**
                    - ✅ Créer une tâche (titre, description, statut)
                    - 📋 Lister toutes les tâches avec filtre par statut
                    - ✏️ Modifier une tâche existante
                    - 🗑️ Supprimer une tâche
                    
                    **Statuts disponibles :** `A_FAIRE` | `EN_COURS` | `TERMINE`
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("Développeur — Entretien LBCA")
                    .email("dev@lbca.cf"))
                .license(new License().name("MIT")))
            .servers(List.of(
                new Server().url("http://localhost:8081").description("Serveur local de développement")
            ));
    }
}
