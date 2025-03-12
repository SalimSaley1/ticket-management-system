-- Sera exécuté au démarrage du conteneur Oracle

alter system set LOCAL_LISTENER='(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))' scope=both;
alter system register;


-- Dans Oracle 21c XE, les utilisateurs normaux doivent être créés
-- dans une Pluggable Database (PDB) (ex: XEPDB1); s'obtient avec "lsnrctl status"
ALTER SESSION SET CONTAINER = xepdb1;

-- Création de l'utilisateur pour l'application
CREATE USER ticket_user IDENTIFIED BY ticket_password;

-- Attribution des droits nécessaires
GRANT CONNECT, RESOURCE, DBA TO ticket_user;
GRANT CREATE SESSION, CREATE TABLE, CREATE VIEW, CREATE SEQUENCE TO ticket_user;
GRANT UNLIMITED TABLESPACE TO ticket_user;

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