-- Script à placer dans le dossier ./init-scripts
-- Sera exécuté au démarrage du conteneur Oracle

-- Connexion en tant qu'utilisateur SYS (administrateur)
-- CONNECT SYS/password123@//localhost:1521/XE AS SYSDBA;

alter system set LOCAL_LISTENER='(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))' scope=both;
alter system register;


-- Dans Oracle 21c XE, les utilisateurs normaux doivent être créés
-- dans une Pluggable Database (PDB) (ex: XEPDB1); s'obtient avec "lsnrctl status"
ALTER SESSION SET CONTAINER = xepdb1;

-- Création de l'utilisateur pour l'application
CREATE USER '&DB_USERNAME' IDENTIFIED BY '&DB_PASSWORD';

-- Attribution des droits nécessaires
GRANT CONNECT, RESOURCE, DBA TO ticket_user;
GRANT CREATE SESSION, CREATE TABLE, CREATE VIEW, CREATE SEQUENCE TO ticket_user;
GRANT UNLIMITED TABLESPACE TO ticket_user;

-- Création du schéma (optionnel, peut être fait par JPA/Hibernate)
-- On peut ajouter ici des instructions SQL pour créer des tables, des séquences, etc.
-- ou laisser Hibernate s'en charger avec spring.jpa.hibernate.ddl-auto=update

-- Script de vérification de santé pour le healthcheck
--CREATE OR REPLACE PROCEDURE check_db_health AS
--BEGIN
--NULL;
--END;
--/

-- Création du script de healthcheck
--WHENEVER SQLERROR EXIT SQL.SQLCODE;
--BEGIN
  --check_db_health();
  --DBMS_OUTPUT.PUT_LINE('Database is healthy');
--END;
--/
EXIT;