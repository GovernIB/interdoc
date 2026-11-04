
-- ============================================================
-- SCRIPT ORACLE 18c
-- Migracion de PostgreSQL a Oracle
-- ============================================================

-- ============================================================
-- 1. SECUENCIAS
-- ============================================================

CREATE SEQUENCE itd_acces_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_aplicacio_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_entitat_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_fitxer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_infoarxiu_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_infosignatura_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_metadada_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_peticioatercer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_plugin_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_referencia_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_refxml_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_traza_seq START WITH 1 INCREMENT BY 1;


-- ============================================================
-- 2. CREACION DE TABLAS
-- ============================================================

-- TABLA ITD_ACCES

CREATE TABLE itd_acces (
    accesid NUMBER(19) DEFAULT itd_acces_seq.NEXTVAL NOT NULL,
    identificacio VARCHAR2(50 CHAR),
    tipusidentificacio VARCHAR2(50 CHAR),
    ip VARCHAR2(20 CHAR),
    datacreacio TIMESTAMP,
    referenciaid NUMBER(19)
);


-- TABLA ITD_APLICACIO

CREATE TABLE itd_aplicacio (
    aplicacioid NUMBER(19) DEFAULT itd_aplicacio_seq.NEXTVAL NOT NULL,
    usuari VARCHAR2(50 CHAR),
    clau VARCHAR2(100 CHAR),
    nom VARCHAR2(255 CHAR),
    codidir3 VARCHAR2(50 CHAR) NOT NULL,
    datacreacio TIMESTAMP,
    estat NUMBER(10)
);


-- TABLA ITD_ENTITAT

CREATE TABLE itd_entitat (
    entitatid NUMBER(19) DEFAULT itd_entitat_seq.NEXTVAL NOT NULL,
    nom VARCHAR2(255 CHAR) NOT NULL,
    codidir3 VARCHAR2(50 CHAR),
    datacreacio TIMESTAMP,
    actiu NUMBER(10) DEFAULT 0 NOT NULL
);


-- TABLA ITD_FITXER

CREATE TABLE itd_fitxer (
    fitxerid NUMBER(19) DEFAULT itd_fitxer_seq.NEXTVAL NOT NULL,
    nom VARCHAR2(255 CHAR) NOT NULL,
    descripcio VARCHAR2(1000 CHAR),
    mime VARCHAR2(255 CHAR) NOT NULL,
    tamany NUMBER(19) NOT NULL,
    datacreacio TIMESTAMP,
    ruta VARCHAR2(255 CHAR)
);


-- TABLA ITD_INFOARXIU

CREATE TABLE itd_infoarxiu (
    infoarxiuid NUMBER(19) NOT NULL,
    originalfileurl VARCHAR2(255 CHAR),
    csv VARCHAR2(255 CHAR),
    csvgenerationdefinition VARCHAR2(255 CHAR),
    csvvalidationweb VARCHAR2(255 CHAR),
    arxiuexpedientid VARCHAR2(255 CHAR),
    arxiudocumentid VARCHAR2(255 CHAR),
    printableurl VARCHAR2(255 CHAR),
    enifileurl VARCHAR2(255 CHAR),
    validationfileurl VARCHAR2(255 CHAR),
    estatexpedient VARCHAR2(5 CHAR),
    reintents NUMBER(10) DEFAULT 0
);


-- TABLA ITD_INFOSIGNATURA

CREATE TABLE itd_infosignatura (
    infosignaturaid NUMBER(19) NOT NULL,
    signoperation NUMBER(10) NOT NULL,
    signtype VARCHAR2(255 CHAR) NOT NULL,
    signalgorithm VARCHAR2(255 CHAR),
    signmode NUMBER(10),
    signaturestablelocation NUMBER(10),
    timestampincluded NUMBER(1),
    policyincluded NUMBER(1),
    enitipofirma VARCHAR2(255 CHAR),
    eniperfilfirma VARCHAR2(255 CHAR),
    enirolfirma VARCHAR2(255 CHAR),
    enisignername VARCHAR2(255 CHAR),
    enisigneradministrationid VARCHAR2(255 CHAR),
    enisignlevel VARCHAR2(255 CHAR),
    checkadministrationidofsigner NUMBER(1),
    checkdocumentmodifications NUMBER(1),
    checkvalidationsignature NUMBER(1),
    signdate TIMESTAMP,
    "signId" VARCHAR2(255 CHAR),
    filename VARCHAR2(255 CHAR),
    filemime VARCHAR2(255 CHAR),

    CONSTRAINT chk_infosig_timestamp
        CHECK (timestampincluded IN (0,1)),

    CONSTRAINT chk_infosig_policy
        CHECK (policyincluded IN (0,1)),

    CONSTRAINT chk_infosig_admin
        CHECK (checkadministrationidofsigner IN (0,1)),

    CONSTRAINT chk_infosig_doc
        CHECK (checkdocumentmodifications IN (0,1)),

    CONSTRAINT chk_infosig_valid
        CHECK (checkvalidationsignature IN (0,1))
);


-- TABLA ITD_LOG

CREATE TABLE itd_log (
    logid NUMBER(19) NOT NULL,
    descripcio CLOB,
    peticio VARCHAR2(255 CHAR),
    excepcio CLOB,
    datacreacio TIMESTAMP
);


-- TABLA ITD_METADADA

CREATE TABLE itd_metadada (
    metadadaid NUMBER(19) NOT NULL,
    nom VARCHAR2(255 CHAR),
    valor VARCHAR2(255 CHAR),
    datacreacio TIMESTAMP NOT NULL,
    referenciaid NUMBER(19) NOT NULL
);


-- TABLA ITD_PETICIOATERCER

CREATE TABLE itd_peticioatercer (
    peticioid NUMBER(19) NOT NULL,
    csvidentificador VARCHAR2(255 CHAR),
    eniidentificador VARCHAR2(255 CHAR),
    codidir3 VARCHAR2(20 CHAR),
    recuperaciooriginal NUMBER(1),
    documenteni VARCHAR2(255 CHAR),
    nif VARCHAR2(50 CHAR),
    tipusidentificacio NUMBER(10),
    ip VARCHAR2(20 CHAR),
    datacreacio TIMESTAMP,
    estat NUMBER(10),

    CONSTRAINT chk_peticio_recup
        CHECK (recuperaciooriginal IN (0,1))
);


-- TABLA ITD_PLUGIN

CREATE TABLE itd_plugin (
    pluginid NUMBER(19) DEFAULT itd_plugin_seq.NEXTVAL NOT NULL,
    nom VARCHAR2(100 CHAR),
    propietats CLOB,
    classe VARCHAR2(255 CHAR),
    datacreacio TIMESTAMP WITH TIME ZONE,
    actiu NUMBER(10),
    entitatid NUMBER(19) NOT NULL,
    tipus NUMBER(19)
);


-- TABLA ITD_REFERENCIA

CREATE TABLE itd_referencia (
    referenciaid NUMBER(19) NOT NULL,
    csvidentificador VARCHAR2(255 CHAR),
    uuid VARCHAR2(255 CHAR),
    direccio VARCHAR2(255 CHAR),
    hash VARCHAR2(255 CHAR),
    emisor VARCHAR2(50 CHAR) NOT NULL,
    receptor VARCHAR2(50 CHAR) NOT NULL,
    urlvisible VARCHAR2(255 CHAR),
    datacreacio TIMESTAMP,
    referencia VARCHAR2(255 CHAR),
    entitatid NUMBER(19),
    infosignaturaid NUMBER(19),
    infoarxiuid NUMBER(19),
    formatfirma VARCHAR2(5 CHAR),
    fitxerid NUMBER(19),
    numeroregistre VARCHAR2(255 CHAR)
);


-- TABLA ITD_REFERENCIAXML

CREATE TABLE itd_referenciaxml (
    referenciaxmlid NUMBER(19) NOT NULL,
    resultat CLOB,
    datacreacio TIMESTAMP,
    referenciaid NUMBER(19)
);


-- TABLA ITD_TRAZA

CREATE TABLE itd_traza (
    trazaid NUMBER(19) NOT NULL,
    nom VARCHAR2(255 CHAR),
    valor VARCHAR2(255 CHAR),
    datacreacio TIMESTAMP,
    referenciaid NUMBER(19)
);


-- ============================================================
-- 3. PRIMARY KEYS
-- ============================================================

ALTER TABLE itd_peticioatercer
    ADD CONSTRAINT int_peticioatercer_pk
    PRIMARY KEY (peticioid);

ALTER TABLE itd_referencia
    ADD CONSTRAINT int_referencia_pk
    PRIMARY KEY (referenciaid);

ALTER TABLE itd_referenciaxml
    ADD CONSTRAINT int_referenciaxml_pk
    PRIMARY KEY (referenciaxmlid);

ALTER TABLE itd_traza
    ADD CONSTRAINT int_traza_pk
    PRIMARY KEY (trazaid);

ALTER TABLE itd_acces
    ADD CONSTRAINT itd_acces_pk
    PRIMARY KEY (accesid);

ALTER TABLE itd_aplicacio
    ADD CONSTRAINT itd_aplicacio_pk
    PRIMARY KEY (aplicacioid);

ALTER TABLE itd_entitat
    ADD CONSTRAINT itd_entitat_pkey
    PRIMARY KEY (entitatid);

ALTER TABLE itd_fitxer
    ADD CONSTRAINT itd_fitxer_pkey
    PRIMARY KEY (fitxerid);

ALTER TABLE itd_infoarxiu
    ADD CONSTRAINT itd_infoarxiu_pkey
    PRIMARY KEY (infoarxiuid);

ALTER TABLE itd_infosignatura
    ADD CONSTRAINT itd_infosignatura_pkey
    PRIMARY KEY (infosignaturaid);

ALTER TABLE itd_log
    ADD CONSTRAINT itd_log_pkey
    PRIMARY KEY (logid);

ALTER TABLE itd_metadada
    ADD CONSTRAINT itd_metadada_pk
    PRIMARY KEY (metadadaid);

ALTER TABLE itd_plugin
    ADD CONSTRAINT itd_plugin_pkey
    PRIMARY KEY (pluginid);


-- ============================================================
-- 4. FOREIGN KEYS
-- ============================================================

-- ITD_ACCES -> ITD_REFERENCIA

ALTER TABLE itd_acces
    ADD CONSTRAINT int_acces_referencia_fk
    FOREIGN KEY (referenciaid)
    REFERENCES itd_referencia(referenciaid);


-- ITD_REFERENCIAXML -> ITD_REFERENCIA

ALTER TABLE itd_referenciaxml
    ADD CONSTRAINT int_refxml_referencia_fk
    FOREIGN KEY (referenciaid)
    REFERENCES itd_referencia(referenciaid);


-- ITD_TRAZA -> ITD_REFERENCIA

ALTER TABLE itd_traza
    ADD CONSTRAINT int_traza_referencia_fk
    FOREIGN KEY (referenciaid)
    REFERENCES itd_referencia(referenciaid);


-- ITD_METADADA -> ITD_REFERENCIA

ALTER TABLE itd_metadada
    ADD CONSTRAINT itd_metadada_referencia_fk
    FOREIGN KEY (referenciaid)
    REFERENCES itd_referencia(referenciaid);


-- ============================================================
-- 5. INDICES ADICIONALES
-- ============================================================

-- Los indices de PRIMARY KEY son creados automaticamente
-- por Oracle. Solo creamos los indices adicionales utiles
-- para las relaciones y consultas.

CREATE INDEX int_refxml_referenciaid_fk_i
    ON itd_referenciaxml (referenciaid);

CREATE INDEX idx_acces_referenciaid
    ON itd_acces (referenciaid);

CREATE INDEX idx_traza_referenciaid
    ON itd_traza (referenciaid);

CREATE INDEX idx_metadada_referenciaid
    ON itd_metadada (referenciaid);


-- ============================================================
-- 6. COMPROBACIONES
-- ============================================================

-- Comprobar tablas creadas

SELECT table_name
FROM user_tables
WHERE table_name LIKE 'ITD_%'
ORDER BY table_name;


-- Comprobar secuencias

SELECT sequence_name
FROM user_sequences
WHERE sequence_name LIKE 'ITD_%'
ORDER BY sequence_name;


-- Comprobar primary keys y foreign keys

SELECT
    table_name,
    constraint_name,
    constraint_type,
    status
FROM user_constraints
WHERE table_name LIKE 'ITD_%'
  AND constraint_type IN ('P', 'R')
ORDER BY table_name, constraint_type;




ALTER TABLE itd_infoarxiu
MODIFY infoarxiuid DEFAULT itd_infoarxiu_seq.NEXTVAL;

ALTER TABLE itd_infosignatura
MODIFY infosignaturaid DEFAULT itd_infosignatura_seq.NEXTVAL;

ALTER TABLE itd_metadada
MODIFY metadadaid DEFAULT itd_metadada_seq.NEXTVAL;

ALTER TABLE itd_peticioatercer
MODIFY peticioid DEFAULT itd_peticioatercer_seq.NEXTVAL;

ALTER TABLE itd_referencia
MODIFY referenciaid DEFAULT itd_referencia_seq.NEXTVAL;

ALTER TABLE itd_referenciaxml
MODIFY referenciaxmlid DEFAULT itd_refxml_seq.NEXTVAL;

ALTER TABLE itd_traza
MODIFY trazaid DEFAULT itd_traza_seq.NEXTVAL;


-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
