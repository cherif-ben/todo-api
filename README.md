# 📋 Todo API — Spring Boot

API REST de gestion de tâches développée avec Spring Boot, Spring Data JPA, Lombok et Swagger.

---

## 🛠️ Stack Technologique

| Technologie | Version | Rôle |
|---|---|---|
| Java | 21 | Langage |
| Spring Boot | 3.2.0 | Framework principal |
| Spring Data JPA | 3.2.0 | Accès base de données |
| H2 Database | - | BDD en mémoire (dev) |
| PostgreSQL | - | BDD (production) |
| Lombok | - | Réduction du code |
| Springdoc OpenAPI | 2.3.0 | Documentation Swagger |

---

## 🚀 Lancer le projet

### Prérequis
- Java 21+
- Maven 3.8+

### 1. Cloner le projet
```bash
git clone https://github.com/votre-username/todo-api.git
cd todo-api
```

### 2. Lancer en développement (H2 en mémoire)
```bash
./mvnw spring-boot:run
```

### 3. Lancer les tests
```bash
./mvnw test
```

### 4. Construire le JAR
```bash
./mvnw clean package
java -jar target/todo-api-1.0.0.jar
```

---

## 🌐 URLs importantes

| URL | Description |
|---|---|
| `http://localhost:8081/swagger-ui.html` | **Documentation Swagger (UI)** |
| `http://localhost:8081/api-docs` | Documentation OpenAPI JSON |
| `http://localhost:8081/h2-console` | Console BDD H2 (dev) |

---

## 📡 Endpoints API

### Base URL : `http://localhost:8081/api/taches`

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/api/taches` | Créer une nouvelle tâche |
| `GET` | `/api/taches` | Lister toutes les tâches |
| `GET` | `/api/taches?statut=A_FAIRE` | Filtrer par statut |
| `GET` | `/api/taches/{id}` | Récupérer une tâche par ID |
| `PUT` | `/api/taches/{id}` | Modifier une tâche |
| `DELETE` | `/api/taches/{id}` | Supprimer une tâche |

---

## 📊 Modèle de données

### Statuts disponibles
| Valeur | Signification |
|---|---|
| `A_FAIRE` | Tâche non commencée |
| `EN_COURS` | Tâche en cours |
| `TERMINE` | Tâche terminée |

### Corps d'une requête POST/PUT
```json
{
  "titre": "Mon titre de tâche",
  "description": "Description détaillée de la tâche",
  "statut": "A_FAIRE"
}
```

### Réponse type
```json
{
  "success": true,
  "message": "Tâche créée avec succès",
  "data": {
    "id": 1,
    "titre": "Mon titre de tâche",
    "description": "Description détaillée de la tâche",
    "statut": "A_FAIRE",
    "createdAt": "2025-07-15T10:30:00",
    "updatedAt": "2025-07-15T10:30:00"
  },
  "timestamp": "2025-07-15T10:30:00"
}
```

---

## 🧪 Exemples avec curl

### Créer une tâche
```bash
curl -X POST http://localhost:8081/api/taches \
  -H "Content-Type: application/json" \
  -d '{"titre":"Préparer l'\''entretien","description":"Réviser Spring Boot","statut":"EN_COURS"}'
```

### Lister toutes les tâches
```bash
curl http://localhost:8081/api/taches
```

### Filtrer par statut
```bash
curl "http://localhost:8081/api/taches?statut=A_FAIRE"
```

### Modifier une tâche
```bash
curl -X PUT http://localhost:8081/api/taches/1 \
  -H "Content-Type: application/json" \
  -d '{"statut":"TERMINE"}'
```

### Supprimer une tâche
```bash
curl -X DELETE http://localhost:8081/api/taches/1
```

---

## 🗄️ Configuration PostgreSQL (production)

Modifier `application.yml` :
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/todo_db
    username: votre_user
    password: votre_password
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

---

## 📁 Structure du projet

```
todo-api/
├── src/
│   ├── main/
│   │   ├── java/com/todo/
│   │   │   ├── TodoApiApplication.java       # Point d'entrée
│   │   │   ├── config/
│   │   │   │   ├── SwaggerConfig.java        # Config OpenAPI/Swagger
│   │   │   │   └── DataInitializer.java      # Données de démo
│   │   │   ├── controller/
│   │   │   │   └── TacheController.java      # Endpoints REST
│   │   │   ├── dto/
│   │   │   │   ├── TacheRequestDTO.java      # Corps requête création
│   │   │   │   ├── TacheUpdateDTO.java       # Corps requête modification
│   │   │   │   ├── TacheResponseDTO.java     # Corps réponse
│   │   │   │   └── ApiResponseDTO.java       # Réponse enveloppe générique
│   │   │   ├── entity/
│   │   │   │   ├── Tache.java                # Entité JPA
│   │   │   │   └── StatutTache.java          # Enum des statuts
│   │   │   ├── exception/
│   │   │   │   ├── TacheNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/
│   │   │   │   └── TacheRepository.java      # Interface JPA
│   │   │   └── service/
│   │   │       ├── TacheService.java         # Interface
│   │   │       └── TacheServiceImpl.java     # Implémentation
│   │   └── resources/
│   │       └── application.yml              # Configuration
│   └── test/
│       └── java/com/todo/
│           └── TacheServiceTest.java        # Tests unitaires
├── pom.xml
└── README.md
```

---

## ✅ Fonctionnalités implémentées

- [x] Créer une tâche (POST)
- [x] Lister toutes les tâches (GET)
- [x] Filtrer par statut (GET ?statut=)
- [x] Récupérer une tâche par ID (GET /{id})
- [x] Modifier une tâche (PUT /{id})
- [x] Supprimer une tâche (DELETE /{id})
- [x] Validation des données (@Valid, @NotBlank)
- [x] Gestion des erreurs (404, 400, 500)
- [x] Documentation Swagger automatique
- [x] Tests unitaires (JUnit 5 + Mockito)
- [x] Données de démo au démarrage
