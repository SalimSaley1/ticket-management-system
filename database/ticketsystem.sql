--------------------------------------------------------
--  Fichier cr�� - mercredi-f�vrier-26-2025   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Type MUSEE
--------------------------------------------------------

  CREATE OR REPLACE TYPE "ENSATE"."MUSEE" as object(
Nom varchar2(20),
jourFermeture varchar2(15));

create type menu as object(
Nom varchar(20),
Prix number(2));

----------   1)  type restaurant 
create type TList_menu as VARRAY(3) of object ;
create or replace type Tadr as object (rue varchar2(30), ville varchar2(30), code_postal number);
create or replace type restaurant as object (nom varchar2(20), adresse Tadr,menus TList_menu);

----------   2)  type ville
create type Nrestaurant as table of restaurant ;
create type Nmusee as table of musee;
create table ville (nom varchar2(30), restaurants Nrestaurant, musees Nmusee) nested table restaurants, musees store as  tab_restaurants, tab_musees;

----------   3)  Table global

create table Villes of ville;

/
--------------------------------------------------------
--  DDL for Type T_ADRESSAGE
--------------------------------------------------------

  CREATE OR REPLACE TYPE "ENSATE"."T_ADRESSAGE" as Object(ip varchar2(15), dns varchar2(100));

/
--------------------------------------------------------
--  DDL for Type T_LOCALISATION
--------------------------------------------------------

  CREATE OR REPLACE TYPE "ENSATE"."T_LOCALISATION" as object (campus varchar2(50), batiment varchar2(20), salle number(3));

/
--------------------------------------------------------
--  DDL for Sequence APP_USER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."APP_USER_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence APP_USER_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."APP_USER_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence AUDIT_LOG_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."AUDIT_LOG_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence AUDIT_LOG_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."AUDIT_LOG_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence COMMENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."COMMENT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TICKET_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."TICKET_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 1001 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TICKET_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."TICKET_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_COMMENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."USER_COMMENT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_COMMENT_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."USER_COMMENT_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ENSATE"."USER_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table APP_USER
--------------------------------------------------------

  CREATE TABLE "ENSATE"."APP_USER" 
   (	"ID" NUMBER(19,0), 
	"EMAIL" VARCHAR2(255 CHAR), 
	"FULL_NAME" VARCHAR2(255 CHAR), 
	"PASSWORD" VARCHAR2(255 CHAR), 
	"ROLE" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table AUDIT_LOG
--------------------------------------------------------

  CREATE TABLE "ENSATE"."AUDIT_LOG" 
   (	"ID" NUMBER(19,0), 
	"AUDIT_TYPE" VARCHAR2(255 CHAR), 
	"NEW_STATUS" NUMBER(3,0), 
	"PREVIOUS_STATUS" NUMBER(3,0), 
	"CHANGE_DATE" TIMESTAMP (6), 
	"COMMENT_ID" NUMBER(19,0), 
	"PERFORMED_BY_ID" NUMBER(19,0), 
	"TICKET_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table DIVISIONS
--------------------------------------------------------

  CREATE TABLE "ENSATE"."DIVISIONS" 
   (	"CODEDIV" CHAR(1 BYTE), 
	"NOMDIV" VARCHAR2(40 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table EQUIPES
--------------------------------------------------------

  CREATE TABLE "ENSATE"."EQUIPES" 
   (	"CODEEQUIPE" CHAR(3 BYTE), 
	"NOMEQUIPE" VARCHAR2(50 BYTE), 
	"CODEDIV" CHAR(1 BYTE), 
	"VILLE" VARCHAR2(40 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table HTE_APP_USER
--------------------------------------------------------

  CREATE GLOBAL TEMPORARY TABLE "ENSATE"."HTE_APP_USER" 
   (	"RN_" NUMBER(10,0), 
	"ID" NUMBER(19,0), 
	"EMAIL" VARCHAR2(255 CHAR), 
	"FULL_NAME" VARCHAR2(255 CHAR), 
	"PASSWORD" VARCHAR2(255 CHAR)
   ) ON COMMIT DELETE ROWS ;
--------------------------------------------------------
--  DDL for Table HTE_AUDIT_LOG
--------------------------------------------------------

  CREATE GLOBAL TEMPORARY TABLE "ENSATE"."HTE_AUDIT_LOG" 
   (	"NEW_STATUS" NUMBER(3,0), 
	"PREVIOUS_STATUS" NUMBER(3,0), 
	"RN_" NUMBER(10,0), 
	"ID" NUMBER(19,0), 
	"AUDIT_TYPE" VARCHAR2(255 CHAR)
   ) ON COMMIT DELETE ROWS ;
--------------------------------------------------------
--  DDL for Table HTE_COMMENT
--------------------------------------------------------

  CREATE GLOBAL TEMPORARY TABLE "ENSATE"."HTE_COMMENT" 
   (	"RN_" NUMBER(10,0), 
	"CREATION_DATE" TIMESTAMP (6), 
	"ID" NUMBER(19,0), 
	"CONTENT" VARCHAR2(1000 CHAR)
   ) ON COMMIT DELETE ROWS ;
--------------------------------------------------------
--  DDL for Table HTE_TICKET
--------------------------------------------------------

  CREATE GLOBAL TEMPORARY TABLE "ENSATE"."HTE_TICKET" 
   (	"RN_" NUMBER(10,0), 
	"CREATION_DATE" TIMESTAMP (6), 
	"ID" NUMBER(19,0), 
	"DESCRIPTION" VARCHAR2(1000 CHAR), 
	"CATEGORY" VARCHAR2(255 CHAR), 
	"PRIORITY" VARCHAR2(255 CHAR), 
	"STATUS" VARCHAR2(255 CHAR), 
	"TITLE" VARCHAR2(255 CHAR)
   ) ON COMMIT DELETE ROWS ;
--------------------------------------------------------
--  DDL for Table HTE_USER
--------------------------------------------------------

  CREATE GLOBAL TEMPORARY TABLE "ENSATE"."HTE_USER" 
   (	"RN_" NUMBER(10,0), 
	"ID" NUMBER(19,0), 
	"EMAIL" VARCHAR2(255 CHAR), 
	"FULL_NAME" VARCHAR2(255 CHAR), 
	"PASSWORD" VARCHAR2(255 CHAR)
   ) ON COMMIT DELETE ROWS ;
--------------------------------------------------------
--  DDL for Table HTE_USER_COMMENT
--------------------------------------------------------

  CREATE GLOBAL TEMPORARY TABLE "ENSATE"."HTE_USER_COMMENT" 
   (	"RN_" NUMBER(10,0), 
	"CREATION_DATE" TIMESTAMP (6), 
	"ID" NUMBER(19,0), 
	"CONTENT" VARCHAR2(1000 CHAR)
   ) ON COMMIT DELETE ROWS ;
--------------------------------------------------------
--  DDL for Table JOUEURS
--------------------------------------------------------

  CREATE TABLE "ENSATE"."JOUEURS" 
   (	"NUMJOUEUR" NUMBER(3,0), 
	"NOM" VARCHAR2(30 BYTE), 
	"PRENOM" VARCHAR2(30 BYTE), 
	"SALAIRE" NUMBER(8,0), 
	"CODEEQUIPE" CHAR(3 BYTE), 
	"POSITION_JOUEUR" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table MATCHS
--------------------------------------------------------

  CREATE TABLE "ENSATE"."MATCHS" 
   (	"NUMMATCH" NUMBER(4,0), 
	"DATEMATCH" DATE, 
	"CODEEQUIPEV" CHAR(3 BYTE), 
	"CODEEQUIPER" CHAR(3 BYTE), 
	"SCOREV" NUMBER(2,0), 
	"SCORER" NUMBER(2,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table SERVEUR
--------------------------------------------------------

  CREATE TABLE "ENSATE"."SERVEUR" 
   (	"NUMSERV" NUMBER(3,0), 
	"ADRESSAGE" "ENSATE"."T_ADRESSAGE" , 
	"LOCALISATION" "ENSATE"."T_LOCALISATION" 
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table STATISTIQUES
--------------------------------------------------------

  CREATE TABLE "ENSATE"."STATISTIQUES" 
   (	"NUMMATCH" NUMBER(4,0), 
	"NUMJOUEUR" NUMBER(3,0), 
	"NBBUTS" NUMBER(3,0), 
	"NBPASSES" NUMBER(3,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table TICKET
--------------------------------------------------------

  CREATE TABLE "ENSATE"."TICKET" 
   (	"ID" NUMBER(19,0), 
	"CATEGORY" VARCHAR2(255 CHAR), 
	"CREATION_DATE" TIMESTAMP (6), 
	"DESCRIPTION" VARCHAR2(1000 CHAR), 
	"PRIORITY" VARCHAR2(255 CHAR), 
	"STATUS" VARCHAR2(255 CHAR), 
	"TITLE" VARCHAR2(255 CHAR), 
	"CREATED_BY_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table USER_COMMENT
--------------------------------------------------------

  CREATE TABLE "ENSATE"."USER_COMMENT" 
   (	"ID" NUMBER(19,0), 
	"CONTENT" VARCHAR2(1000 CHAR), 
	"CREATION_DATE" TIMESTAMP (6), 
	"TICKET_ID" NUMBER(19,0), 
	"USER_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index UK_M40KHMLVDNIWGOHTQ40N8F8K1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ENSATE"."UK_M40KHMLVDNIWGOHTQ40N8F8K1" ON "ENSATE"."AUDIT_LOG" ("COMMENT_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index UK_1J9D9A06I600GD43UU3KM82JW
--------------------------------------------------------

  CREATE UNIQUE INDEX "ENSATE"."UK_1J9D9A06I600GD43UU3KM82JW" ON "ENSATE"."APP_USER" ("EMAIL") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  Constraints for Table EQUIPES
--------------------------------------------------------

  ALTER TABLE "ENSATE"."EQUIPES" ADD PRIMARY KEY ("CODEEQUIPE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."EQUIPES" MODIFY ("NOMEQUIPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HTE_APP_USER
--------------------------------------------------------

  ALTER TABLE "ENSATE"."HTE_APP_USER" ADD PRIMARY KEY ("RN_") ENABLE;
  ALTER TABLE "ENSATE"."HTE_APP_USER" MODIFY ("RN_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HTE_TICKET
--------------------------------------------------------

  ALTER TABLE "ENSATE"."HTE_TICKET" ADD PRIMARY KEY ("RN_") ENABLE;
  ALTER TABLE "ENSATE"."HTE_TICKET" MODIFY ("RN_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TICKET
--------------------------------------------------------

  ALTER TABLE "ENSATE"."TICKET" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."TICKET" ADD CHECK (status in ('NEW','IN_PROGRESS','RESOLVED')) ENABLE;
  ALTER TABLE "ENSATE"."TICKET" ADD CHECK (priority in ('LOW','MEDIUM','HIGH')) ENABLE;
  ALTER TABLE "ENSATE"."TICKET" ADD CHECK (category in ('NETWORK','HARDWARE','SOFTWARE','OTHER')) ENABLE;
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("STATUS" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("PRIORITY" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("CREATION_DATE" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."TICKET" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STATISTIQUES
--------------------------------------------------------

  ALTER TABLE "ENSATE"."STATISTIQUES" ADD PRIMARY KEY ("NUMMATCH", "NUMJOUEUR")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table HTE_USER
--------------------------------------------------------

  ALTER TABLE "ENSATE"."HTE_USER" ADD PRIMARY KEY ("RN_") ENABLE;
  ALTER TABLE "ENSATE"."HTE_USER" MODIFY ("RN_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DIVISIONS
--------------------------------------------------------

  ALTER TABLE "ENSATE"."DIVISIONS" MODIFY ("NOMDIV" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."DIVISIONS" ADD PRIMARY KEY ("CODEDIV")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table HTE_USER_COMMENT
--------------------------------------------------------

  ALTER TABLE "ENSATE"."HTE_USER_COMMENT" ADD PRIMARY KEY ("RN_") ENABLE;
  ALTER TABLE "ENSATE"."HTE_USER_COMMENT" MODIFY ("RN_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MATCHS
--------------------------------------------------------

  ALTER TABLE "ENSATE"."MATCHS" ADD PRIMARY KEY ("NUMMATCH")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table HTE_COMMENT
--------------------------------------------------------

  ALTER TABLE "ENSATE"."HTE_COMMENT" ADD PRIMARY KEY ("RN_") ENABLE;
  ALTER TABLE "ENSATE"."HTE_COMMENT" MODIFY ("RN_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table JOUEURS
--------------------------------------------------------

  ALTER TABLE "ENSATE"."JOUEURS" MODIFY ("NOM" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."JOUEURS" ADD PRIMARY KEY ("NUMJOUEUR")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table HTE_AUDIT_LOG
--------------------------------------------------------

  ALTER TABLE "ENSATE"."HTE_AUDIT_LOG" ADD PRIMARY KEY ("RN_") ENABLE;
  ALTER TABLE "ENSATE"."HTE_AUDIT_LOG" MODIFY ("RN_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table AUDIT_LOG
--------------------------------------------------------

  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CONSTRAINT "UK_M40KHMLVDNIWGOHTQ40N8F8K1" UNIQUE ("COMMENT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" MODIFY ("CHANGE_DATE" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CHECK (previous_status between 0 and 2) ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CHECK (new_status between 0 and 2) ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CHECK (audit_type in ('TICKET_STATUS_CHANGE','COMMENT_ADDED')) ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" MODIFY ("AUDIT_TYPE" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."AUDIT_LOG" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table APP_USER
--------------------------------------------------------

  ALTER TABLE "ENSATE"."APP_USER" ADD CHECK (role in ('EMPLOYEE','IT_SUPPORT')) ENABLE;
  ALTER TABLE "ENSATE"."APP_USER" MODIFY ("ROLE" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."APP_USER" ADD CONSTRAINT "UK_1J9D9A06I600GD43UU3KM82JW" UNIQUE ("EMAIL")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."APP_USER" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."APP_USER" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."APP_USER" MODIFY ("FULL_NAME" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."APP_USER" MODIFY ("EMAIL" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."APP_USER" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USER_COMMENT
--------------------------------------------------------

  ALTER TABLE "ENSATE"."USER_COMMENT" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "ENSATE"."USER_COMMENT" MODIFY ("CREATION_DATE" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."USER_COMMENT" MODIFY ("CONTENT" NOT NULL ENABLE);
  ALTER TABLE "ENSATE"."USER_COMMENT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table AUDIT_LOG
--------------------------------------------------------

  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CONSTRAINT "FK456B3RAVWO7LOOIFQKDQPRFDH" FOREIGN KEY ("PERFORMED_BY_ID")
	  REFERENCES "ENSATE"."APP_USER" ("ID") ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CONSTRAINT "FKFJVGWQQHUXAPFP8OE0ISIQOPV" FOREIGN KEY ("COMMENT_ID")
	  REFERENCES "ENSATE"."USER_COMMENT" ("ID") ENABLE;
  ALTER TABLE "ENSATE"."AUDIT_LOG" ADD CONSTRAINT "FKQ29CSL9WG9Y418QJ28VMDDNUO" FOREIGN KEY ("TICKET_ID")
	  REFERENCES "ENSATE"."TICKET" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EQUIPES
--------------------------------------------------------

  ALTER TABLE "ENSATE"."EQUIPES" ADD FOREIGN KEY ("CODEDIV")
	  REFERENCES "ENSATE"."DIVISIONS" ("CODEDIV") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table JOUEURS
--------------------------------------------------------

  ALTER TABLE "ENSATE"."JOUEURS" ADD FOREIGN KEY ("CODEEQUIPE")
	  REFERENCES "ENSATE"."EQUIPES" ("CODEEQUIPE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MATCHS
--------------------------------------------------------

  ALTER TABLE "ENSATE"."MATCHS" ADD FOREIGN KEY ("CODEEQUIPEV")
	  REFERENCES "ENSATE"."EQUIPES" ("CODEEQUIPE") ENABLE;
  ALTER TABLE "ENSATE"."MATCHS" ADD FOREIGN KEY ("CODEEQUIPER")
	  REFERENCES "ENSATE"."EQUIPES" ("CODEEQUIPE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STATISTIQUES
--------------------------------------------------------

  ALTER TABLE "ENSATE"."STATISTIQUES" ADD FOREIGN KEY ("NUMMATCH")
	  REFERENCES "ENSATE"."MATCHS" ("NUMMATCH") ENABLE;
  ALTER TABLE "ENSATE"."STATISTIQUES" ADD FOREIGN KEY ("NUMJOUEUR")
	  REFERENCES "ENSATE"."JOUEURS" ("NUMJOUEUR") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TICKET
--------------------------------------------------------

  ALTER TABLE "ENSATE"."TICKET" ADD CONSTRAINT "FKRAM1TD620EV9WVPBQ4JTKVL6D" FOREIGN KEY ("CREATED_BY_ID")
	  REFERENCES "ENSATE"."APP_USER" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_COMMENT
--------------------------------------------------------

  ALTER TABLE "ENSATE"."USER_COMMENT" ADD CONSTRAINT "FKE62XHRA7XELP3396GEX40F2VP" FOREIGN KEY ("USER_ID")
	  REFERENCES "ENSATE"."APP_USER" ("ID") ENABLE;
  ALTER TABLE "ENSATE"."USER_COMMENT" ADD CONSTRAINT "FKSWOI7MI7SYQE39KFJMIK84ATK" FOREIGN KEY ("TICKET_ID")
	  REFERENCES "ENSATE"."TICKET" ("ID") ENABLE;
