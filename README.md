# Ticket System Management

## À propos du projet

Ce système de gestion de tickets permet aux employés de signaler et de suivre les problèmes informatiques. Il propose une interface Java Swing pour les utilisateurs et une API REST pour la gestion des tickets.

## Fonctionnalités principales

- Création de tickets avec titre, description, priorité et catégorie
- Suivi des statuts (Nouveau, En cours, Résolu)
- Gestion des rôles utilisateurs (Employés, Support IT)
- Journal d'audit des modifications de statut et commentaires
- Recherche et filtrage des tickets

## Technologies utilisées

- **Backend:** Java 17, Spring Boot <!--, API RESTful avec Swagger/OpenAPI  -->
- **Base de données:** Oracle SQL (21c XE)
- **Interface utilisateur:** Java Swing avec MigLayout
- **Déploiement:** Docker, JAR exécutable

## Structure du projet

```
ticket-management-system/
├── ticketsystem/
│   ├── deployment-files/
│   │   └── docker-compose.yml/
│   │
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── ticketsystemclient/
│   ├── src/
│   ├── pom.xml
│   └── build.gradle
├── database
└── README.md
```

## Prérequis

- Docker et Docker Compose
- JDK 17 ou supérieur (uniquement pour le développement)
- Maven (uniquement pour le développement)

## Installation et démarrage

### Utilisation avec Docker

1. **Cloner le dépôt**

```bash
git clone https://github.com/SalimSaley1/ticket-management-system.git
cd ticketsystem
```

2. **Démarrer l'application avec Docker Compose**

```bash
cd deployment-files
docker-compose up -d
```

Cette commande va:
- Construire l'image du backend Spring Boot
- Démarrer un conteneur Oracle Database
- Exposer l'API REST sur le port 8089

3. **Accéder à l'application**
<!--
- API REST: http://localhost:8080/api
- Documentation Swagger: http://localhost:8080/swagger-ui.html
-->

4. **Lancer le client Swing**

```bash
cd ../../ticketsystemclient
java -jar out/artifacts/ticketsystemclient_jar/ticketsystemclient.jar
```

### Installation manuelle (Optionnel)

1. **Configurer la base de données**

Exécutez le script SQL dans le dossier `database/` sur votre instance Oracle.

2. **Configurer et démarrer le backend**

```bash
cd ticketsystem
# Modifier src/main/resources/application.properties pour la connexion à votre DB
mvn spring-boot:run
```

3. **Construire et exécuter le client Swing**

```bash
cd ticketsystemclient
mvn clean package
java -jar out/artifacts/ticketsystemclient_jar/ticketsystemclient.jar
```

## Configuration Docker

### docker-compose.yml

```yaml
services:
  # Service Oracle Database
  oracle-db:
    image: container-registry.oracle.com/database/express:21.3.0-xe
    container_name: oracle-ticket-db
    ports:
      - "1521:1521"
      - "5500:5500"
    environment:
      - ORACLE_PWD=your_sys_or_system_pwd # system and sys
      - ORACLE_CHARACTERSET=AL32UTF8
    volumes:
      - oracle-data:/opt/oracle/oradata
      # Script d'initialisation pour créer les utilisateurs et schémas
      - ./init-scripts:/opt/oracle/scripts/startup
    healthcheck:
      test: ["CMD", "sqlplus", "-L", "sys/your_sys_or_system_pwd//localhost:1521/xepdb1 as sysdba", "@/opt/oracle/scripts/startup/healthcheck.sql"]
      interval: 30s
      timeout: 10s
      retries: 5
      start_period: 100s
    networks:
      - ticket-network

  # Service Spring Boot Application
  ticket-app:
    build:
      context: ..
      dockerfile: Dockerfile
    container_name: ticket-app
    ports:
      - "8089:8089"
    depends_on:
      - oracle-db
      #condition: service_healthy
    environment:
      # tns format
      - SPRING_DATASOURCE_URL=jdbc:oracle:thin:@
        (DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=oracle-db)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=xepdb1)))
      - SPRING_DATASOURCE_USERNAME=ticket_user
      - SPRING_DATASOURCE_PASSWORD=ticket_password
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - SPRING_DATASOURCE_DRIVER_CLASS_NAME=oracle.jdbc.driver.OracleDriver
      - SPRING_JPA_SHOW_SQL=true
      - JWT_SECRET=salimsaleysalimsaleysalimsaleysalimsaleysalimsaleysalimsaleysalimsaleysalimsaleysalimsaley
      - LOGGING_LEVEL_ROOT=INFO
      - LOGGING_LEVEL_COM_HAHN_TICKETSYSTEM=DEBUG
    networks:
      - ticket-network

volumes:
  oracle-data:
    driver: local

networks:
  ticket-network:
    driver: bridge
```

### Backend Dockerfile

```dockerfile
FROM openjdk:17-jdk-alpine

# add the jar file into the docker image
ADD target/ticketsystem-0.0.1-SNAPSHOT.jar app.jar

# run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Schéma de la base de données

Le schéma de la base de données est disponible dans le fichier `database/init.sql`. Voici les tables principales:

### USER

| Colonne       | Type         | Description                           |
|---------------|--------------|---------------------------------------|
| USER_ID       | NUMBER       | Identifiant unique (clé primaire)     |
| USERNAME      | VARCHAR2(50) | Nom d'utilisateur unique              |
| PASSWORD      | VARCHAR2(60) | Mot de passe crypté                   |
| FULL_NAME     | VARCHAR2(100)| Nom complet de l'utilisateur          |
| EMAIL         | VARCHAR2(100)| Adresse email                         |
| ROLE          | VARCHAR2(20) | Rôle: EMPLOYEE ou IT_SUPPORT          |
| CREATED_AT    | TIMESTAMP    | Date de création du compte            |

### TICKET

| Colonne       | Type          | Description                           |
|---------------|---------------|---------------------------------------|
| TICKET_ID     | NUMBER        | Identifiant unique (clé primaire)     |
| TITLE         | VARCHAR2(100) | Titre du ticket                       |
| DESCRIPTION   | VARCHAR2(4000)| Description détaillée                 |
| PRIORITY      | VARCHAR2(10)  | LOW, MEDIUM, HIGH                     |
| CATEGORY      | VARCHAR2(20)  | NETWORK, HARDWARE, SOFTWARE, OTHER    |
| STATUS        | VARCHAR2(20)  | NEW, IN_PROGRESS, RESOLVED            |
| CREATED_BY    | NUMBER        | Référence à USER_ID (clé étrangère)   |
| ASSIGNED_TO   | NUMBER        | Référence à USER_ID (clé étrangère)   |
| CREATED_AT    | TIMESTAMP     | Date de création du ticket            |
| UPDATED_AT    | TIMESTAMP     | Date de dernière modification         |

### TICKET_COMMENTS

| Colonne       | Type          | Description                           |
|---------------|---------------|---------------------------------------|
| COMMENT_ID    | NUMBER        | Identifiant unique (clé primaire)     |
| TICKET_ID     | NUMBER        | Référence à TICKET_ID (clé étrangère) |
| USER_ID       | NUMBER        | Référence à USER_ID (clé étrangère)   |
| CONTENT       | VARCHAR2(4000)| Contenu du commentaire                |
| CREATED_AT    | TIMESTAMP     | Date de création du commentaire       |

### AUDIT_LOG

| Colonne       | Type          | Description                           |
|---------------|---------------|---------------------------------------|
| LOG_ID        | NUMBER        | Identifiant unique (clé primaire)     |
| TICKET_ID     | NUMBER        | Référence à TICKET_ID (clé étrangère) |
| USER_ID       | NUMBER        | Référence à USER_ID (clé étrangère)   |
| ACTION        | VARCHAR2(50)  | Action effectuée                      |
| OLD_VALUE     | VARCHAR2(100) | Ancienne valeur (pour les changements)|
| NEW_VALUE     | VARCHAR2(100) | Nouvelle valeur                       |
| TIMESTAMP     | TIMESTAMP     | Date et heure de l'action             |

## Documentation de l'API
<!--
L'API REST est documentée avec Swagger/OpenAPI et est accessible à l'adresse `http://localhost:8080/swagger-ui.html` lorsque l'application est en cours d'exécution.
-->
### Principaux points de terminaison

#### Authentification

| Méthode | Point de terminaison      | Description                | Corps de la requête          |
|---------|---------------------------|----------------------------|------------------------------|
| POST    | `/api/auth/login`         | Connexion utilisateur      | `{username, password}`       |
| POST    | `/api/auth/register`      | Inscription utilisateur    | `{username, password, ...}`  |

#### Tickets

| Méthode | Point de terminaison         | Description                           | Corps/Paramètres                     |
|---------|------------------------------|---------------------------------------|-------------------------------------|
| GET     | `/api/tickets`               | Liste des tickets (filtrable)         | Query: `status, priority, category` |
| GET     | `/api/tickets/{id}`          | Détails d'un ticket                   | Path: `id`                          |
| POST    | `/api/tickets`               | Création d'un ticket                  | Ticket object                       |
| PUT     | `/api/tickets/{id}`          | Mise à jour d'un ticket               | Path: `id`, Ticket object           |
| PUT     | `/api/tickets/{id}/status`   | Modification du statut                | Path: `id`, `{status}`              |
| GET     | `/api/tickets/search`        | Recherche de tickets                  | Query: `query`                      |

#### Commentaires

| Méthode | Point de terminaison                 | Description                     | Corps/Paramètres                     |
|---------|-------------------------------------|----------------------------------|-------------------------------------|
| GET     | `/api/tickets/{id}/comments`        | Liste des commentaires          | Path: `id`                          |
| POST    | `/api/tickets/{id}/comments`        | Ajout d'un commentaire          | Path: `id`, Comment object           |

#### Journal d'audit

| Méthode | Point de terminaison         | Description                    | Corps/Paramètres                     |
|---------|------------------------------|--------------------------------|-------------------------------------|
| GET     | `/api/tickets/{id}/audit`    | Journal d'audit d'un ticket    | Path: `id`                          |

### Exemples de requêtes

#### Connexion

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john.doe", "password": "password123"}'
```

#### Création d'un ticket

```bash
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "title": "Problème de connexion réseau",
    "description": "Je ne peux pas accéder au serveur interne depuis ce matin",
    "priority": "HIGH",
    "category": "NETWORK"
  }'
```

#### Changement de statut d'un ticket

```bash
curl -X PUT http://localhost:8080/api/tickets/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{"status": "IN_PROGRESS"}'
```

## Client Java Swing

Le client Java Swing est distribué sous forme de JAR exécutable. Il offre une interface utilisateur pour:

- Se connecter au système
- Créer et consulter des tickets
- Ajouter des commentaires
- Changer le statut des tickets (pour les utilisateurs IT Support)
- Rechercher et filtrer les tickets


## Contributeurs

- Salim Saley Midou

