
-- ============================================================
-- SCRIPT ORACLE DATABASE 18C
-- Esquema: INTERDOC
-- ============================================================


-- ============================================================
-- 1. SECUENCIAS
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

CREATE TABLE itd_idioma
(
    idiomaid VARCHAR2(5 CHAR) NOT NULL,
    nom VARCHAR2(50 CHAR) NOT NULL,
    suportat NUMBER(1) DEFAULT 1 NOT NULL,
    ordre NUMBER(10,0) DEFAULT 0 NOT NULL,

    CONSTRAINT itd_idioma_pk
        PRIMARY KEY (idiomaid),

    CONSTRAINT itd_idioma_suportat_ck
        CHECK (suportat IN (0, 1))
);


-- ============================================================
-- 3. TABLA ITD_USUARI
-- ============================================================

CREATE TABLE itd_usuari
(
    usuariid NUMBER(19,0)
        DEFAULT itd_usuari_seq.NEXTVAL NOT NULL,

    username VARCHAR2(255 CHAR) NOT NULL,

    idiomaid VARCHAR2(5 CHAR) NOT NULL,

    darreraentitat NUMBER(19,0),

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
        REFERENCES itd_entitat (entitatid),

    CONSTRAINT itd_usuari_idioma_idi_fk
        FOREIGN KEY (idiomaid)
        REFERENCES itd_idioma (idiomaid)
);


-- ============================================================
-- 4. INDICES ITD_USUARI
-- ============================================================

CREATE INDEX itd_usuari_darreraentitat_fk_i
    ON itd_usuari (darreraentitat);

CREATE INDEX itd_usuari_idiomaid_fk_i
    ON itd_usuari (idiomaid);


-- ============================================================
-- 5. TABLA ITD_USUARIENTITAT
-- ============================================================

CREATE TABLE itd_usuarientitat
(
    usuarientitatid NUMBER(19,0)
        DEFAULT itd_usuarientitat_seq.NEXTVAL NOT NULL,

    usuariid NUMBER(19,0) NOT NULL,

    entitatid NUMBER(19,0) NOT NULL,

    actiu NUMBER(1) NOT NULL,

    CONSTRAINT itd_usuarientitat_pk
        PRIMARY KEY (usuarientitatid),

    CONSTRAINT itd_usuent_entitat_fk
        FOREIGN KEY (entitatid)
        REFERENCES itd_entitat (entitatid),

    CONSTRAINT itd_usuent_usuari_fk
        FOREIGN KEY (usuariid)
        REFERENCES itd_usuari (usuariid),

    CONSTRAINT itd_usuent_usu_ent_uk
        UNIQUE (usuariid, entitatid),

    CONSTRAINT itd_usuent_actiu_ck
        CHECK (actiu IN (0, 1))
);


-- ============================================================
-- 6. INDICES ITD_USUARIENTITAT
-- ============================================================

CREATE INDEX itd_usuent_entitatid_fk_i
    ON itd_usuarientitat (entitatid);

CREATE INDEX itd_usuent_usuariid_fk_i
    ON itd_usuarientitat (usuariid);


-- ============================================================
-- 7. INSERTS ITD_IDIOMA
-- ============================================================

INSERT INTO itd_idioma
    (idiomaid, nom, suportat, ordre)
VALUES
    ('ca', 'Català', 1, 0);

INSERT INTO itd_idioma
    (idiomaid, nom, suportat, ordre)
VALUES
    ('es', 'Castellano', 1, 1);

COMMIT;


-- ============================================================
-- 8. SECUENCIA ITD_PLUGINENTITAT
-- ============================================================

CREATE SEQUENCE itd_pluginentitat_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1018
    NOCACHE
    NOCYCLE;


-- ============================================================
-- 9. FOREIGN KEY ITD_PLUGINENTITAT
-- ============================================================

-- IMPORTANTE:
-- La sentencia original no indica la tabla sobre la
-- que debe crearse la restriccion.
--
-- Si la tabla es ITD_PLUGINENTITAT, ejecutar:
--
--ALTER TABLE itd_plugin
     --ADD CONSTRAINT fk_itd_plugin_entitat
     --FOREIGN KEY (entitatid)
     --REFERENCES itd_entitat (entitatid);


-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
