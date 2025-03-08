-- healthcheck.sql
-- Script à placer à la racine du projet

WHENEVER SQLERROR EXIT SQL.SQLCODE;
BEGIN
  DBMS_OUTPUT.PUT_LINE('Database is running');
END;
/
EXIT;