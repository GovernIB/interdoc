
-- ============================================================
-- SCRIPT ORACLE DATABASE 18c
-- MIGRACION DE POSTGRESQL A ORACLE
-- USUARIOS, IDIOMAS Y ENTIDADES
-- ============================================================


-- ============================================================
-- 1. CREACION DE SECUENCIAS
-- ============================================================

CREATE SEQUENCE itd_usuari_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1000
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE itd_usuarientitat_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1000
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 2. TABLA ITD_IDIOMA
-- ============================================================

CREATE TABLE itd_idioma (
    idiomaid VARCHAR2(5 CHAR) NOT NULL,
    nom VARCHAR2(50 CHAR) NOT NULL,
    suportat NUMBER(1) DEFAULT 1 NOT NULL,
    ordre NUMBER(10) DEFAULT 0 NOT NULL,

    CONSTRAINT itd_idioma_pk
        PRIMARY KEY (idiomaid),

    CONSTRAINT chk_itd_idioma_suportat
        CHECK (suportat IN (0,1))
);


-- ============================================================
-- 3. TABLA ITD_USUARI
-- ============================================================

CREATE TABLE itd_usuari (
    usuariid NUMBER(19)
        DEFAULT itd_usuari_seq.NEXTVAL NOT NULL,

    username VARCHAR2(255 CHAR) NOT NULL,
    idiomaid VARCHAR2(5 CHAR) NOT NULL,
    darreraentitat NUMBER(19),
    nom VARCHAR2(255 CHAR) NOT NULL,
    llinatge1 VARCHAR2(255 CHAR) NOT NULL,
    llinatge2 VARCHAR2(255 CHAR),
    email VARCHAR2(255 CHAR),
    nif VARCHAR2(255 CHAR),

    CONSTRAINT itd_usuari_pk
        PRIMARY KEY (usuariid),

    CONSTRAINT itd_usuari_nif_uk
        UNIQUE (nif),

    CONSTRAINT itd_usuari_username_uk
        UNIQUE (username),

    CONSTRAINT itd_usuari_entitat_fk
        FOREIGN KEY (darreraentitat)
        REFERENCES itd_entitat(entitatid),

    CONSTRAINT itd_usuari_idioma_idi_fk
        FOREIGN KEY (idiomaid)
        REFERENCES itd_idioma(idiomaid)
);


-- ============================================================
-- 4. TABLA ITD_USUARIENTITAT
-- ============================================================

CREATE TABLE itd_usuarientitat (
    usuarientitatid NUMBER(19)
        DEFAULT itd_usuarientitat_seq.NEXTVAL NOT NULL,

    usuariid NUMBER(19) NOT NULL,
    entitatid NUMBER(19) NOT NULL,
    actiu NUMBER(1) NOT NULL,

    CONSTRAINT itd_usuarientitat_pk
        PRIMARY KEY (usuarientitatid),

    CONSTRAINT itd_usuent_entitat_fk
        FOREIGN KEY (entitatid)
        REFERENCES itd_entitat(entitatid),

    CONSTRAINT itd_usuent_usuari_fk
        FOREIGN KEY (usuariid)
        REFERENCES itd_usuari(usuariid),

    CONSTRAINT itd_usuent_usu_ent_uk
        UNIQUE (usuariid, entitatid),

    CONSTRAINT chk_itd_usuarientitat_actiu
        CHECK (actiu IN (0,1))
);


-- ============================================================
-- 5. INDICES ADICIONALES
-- ============================================================

-- Oracle genera automaticamente los indices correspondientes
-- a las PRIMARY KEY y restricciones UNIQUE.

-- Indice para la FK ITD_USUARI -> ITD_ENTITAT

CREATE INDEX itd_usuari_darreraentitat_fk_i
    ON itd_usuari (darreraentitat);


-- Indice para la FK ITD_USUARI -> ITD_IDIOMA

CREATE INDEX itd_usuari_idiomaid_fk_i
    ON itd_usuari (idiomaid);


-- Indice para la FK ITD_USUARIENTITAT -> ITD_ENTITAT

CREATE INDEX itd_usuent_entitatid_fk_i
    ON itd_usuarientitat (entitatid);

-- No se crea otro indice para usuariid porque
-- la restriccion UNIQUE (usuariid, entitatid)
-- ya genera un indice que comienza por usuariid.


-- ============================================================
-- 6. INSERCION DE IDIOMAS
-- ============================================================

INSERT INTO itd_idioma (
    idiomaid,
    nom,
    suportat,
    ordre
)
VALUES (
    'ca',
    'Català',
    1,
    0
);

INSERT INTO itd_idioma (
    idiomaid,
    nom,
    suportat,
    ordre
)
VALUES (
    'es',
    'Castellano',
    1,
    1
);

COMMIT;


-- ============================================================
-- 7. SECUENCIA ITD_PLUGINENTITAT
-- ============================================================

CREATE SEQUENCE itd_pluginentitat_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1018
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 8. FOREIGN KEY ITD_PLUGIN -> ITD_ENTITAT
-- ============================================================

-- Se asume que ITD_PLUGIN existe del script anterior.
-- El SQL PostgreSQL original no indicaba el nombre
-- de la tabla en el ultimo ADD CONSTRAINT.

ALTER TABLE itd_plugin
    ADD CONSTRAINT fk_itd_plugin_entitat
    FOREIGN KEY (entitatid)
    REFERENCES itd_entitat(entitatid);


-- Indice para la relacion ITD_PLUGIN -> ITD_ENTITAT

CREATE INDEX idx_itd_plugin_entitatid
    ON itd_plugin (entitatid);


-- ============================================================
-- 9. COMPROBACIONES
-- ============================================================

-- Verificar tablas

SELECT table_name
FROM user_tables
WHERE table_name IN (
    'ITD_IDIOMA',
    'ITD_USUARI',
    'ITD_USUARIENTITAT'
)
ORDER BY table_name;


-- Verificar secuencias

SELECT sequence_name
FROM user_sequences
WHERE sequence_name IN (
    'ITD_USUARI_SEQ',
    'ITD_USUARIENTITAT_SEQ',
    'ITD_PLUGINENTITAT_SEQ'
)
ORDER BY sequence_name;


-- Verificar restricciones

SELECT
    table_name,
    constraint_name,
    constraint_type,
    status
FROM user_constraints
WHERE table_name IN (
    'ITD_IDIOMA',
    'ITD_USUARI',
    'ITD_USUARIENTITAT',
    'ITD_PLUGIN'
)
ORDER BY table_name, constraint_type;


-- Verificar datos de idiomas

SELECT
    idiomaid,
    nom,
    suportat,
    ordre
FROM itd_idioma
ORDER BY ordre;


-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
