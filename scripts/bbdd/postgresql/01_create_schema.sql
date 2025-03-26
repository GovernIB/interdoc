
CREATE SEQUENCE itd_acces_seq START WITH 1 INCREMENT BY 1; 
CREATE SEQUENCE itd_aplicacio_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_entitat_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_fitxer_seq START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE itd_infoarxiu_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_infosignatura_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_metadada_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_peticioatercer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_plugin_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_referencia_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_refxml_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE itd_traza_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE itd_acces (
    accesid bigint DEFAULT nextval('itd_acces_seq'::regclass) NOT NULL,
    identificacio character varying(50),
    tipusidentificacio character varying(50),
    ip character varying(20),
    datacreacio timestamp without time zone,
    referenciaid bigint
);

CREATE TABLE itd_aplicacio (
    aplicacioid bigint DEFAULT nextval('itd_aplicacio_seq'::regclass) NOT NULL,
    usuari character varying(50),
    clau character varying(100),
    nom character varying(255),
    codidir3 character varying(50) NOT NULL,
    datacreacio timestamp without time zone,
    estat integer
);

CREATE TABLE itd_entitat (
    entitatid bigint DEFAULT nextval('itd_entitat_seq'::regclass) NOT NULL,
    nom character varying(255) NOT NULL,
    codidir3 character varying(50),
    datacreacio timestamp without time zone,
    actiu integer DEFAULT 0 NOT NULL
);

CREATE TABLE itd_fitxer (
    fitxerid bigint DEFAULT nextval('itd_fitxer_seq'::regclass) NOT NULL,
    nom character varying(255) NOT NULL,
    descripcio character varying(1000),
    mime character varying(255) NOT NULL,
    tamany bigint NOT NULL,
    datacreacio timestamp without time zone,
    ruta character varying(255)
);

CREATE TABLE itd_infoarxiu (
    infoarxiuid bigint NOT NULL,
    originalfileurl character varying(255),
    csv character varying(255),
    csvgenerationdefinition character varying(255),
    csvvalidationweb character varying(255),
    arxiuexpedientid character varying(255),
    arxiudocumentid character varying(255),
    printableurl character varying(255),
    enifileurl character varying(255),
    validationfileurl character varying(255),
    estatexpedient character varying(5),
    reintents integer DEFAULT 0
);

CREATE TABLE itd_infosignatura (
    infosignaturaid bigint NOT NULL,
    signoperation integer NOT NULL,
    signtype character varying(255) NOT NULL,
    signalgorithm character varying(255),
    signmode integer,
    signaturestablelocation integer,
    timestampincluded boolean,
    policyincluded boolean,
    enitipofirma character varying(255),
    eniperfilfirma character varying(255),
    enirolfirma character varying(255),
    enisignername character varying(255),
    enisigneradministrationid character varying(255),
    enisignlevel character varying(255),
    checkadministrationidofsigner boolean,
    checkdocumentmodifications boolean,
    checkvalidationsignature boolean,
    signdate timestamp without time zone,
    "signId" character varying(255),
    filename character varying(255),
    filemime character varying(255)
);

CREATE TABLE itd_log (
    logid bigint NOT NULL,
    descripcio text,
    peticio character varying(255),
    excepcio text,
    datacreacio timestamp without time zone
);

CREATE TABLE itd_metadada (
    metadadaid bigint NOT NULL,
    nom character varying(255),
    valor character varying(255),
    datacreacio timestamp without time zone NOT NULL,
    referenciaid bigint NOT NULL
);

CREATE TABLE itd_peticioatercer (
    peticioid bigint NOT NULL,
    csvidentificador character varying(255),
    eniidentificador character varying(255),
    codidir3 character varying(20),
    recuperaciooriginal boolean,
    documenteni character varying(255),
    nif character varying(50),
    tipusidentificacio integer,
    ip character varying(20),
    datacreacio timestamp without time zone,
    estat integer
);

CREATE TABLE itd_plugin (
    pluginid bigint DEFAULT nextval('itd_plugin_seq'::regclass) NOT NULL,
    nom character varying(100),
    propietats text,
    classe character varying(255),
    datacreacio timestamp with time zone,
    actiu integer,
    entitatid bigint NOT NULL,
    tipus bigint
);

CREATE TABLE itd_referencia (
    referenciaid bigint NOT NULL,
    csvidentificador character varying(255),
    uuid character varying(255),
    direccio character varying(255),
    hash character varying(255),
    emisor character varying(50) NOT NULL,
    receptor character varying(50) NOT NULL,
    urlvisible character varying(255),
    datacreacio timestamp without time zone,
    referencia character varying(255),
    entitatid bigint,
    infosignaturaid bigint,
    infoarxiuid bigint,
    formatfirma character varying(5),
    fitxerid bigint,
    numeroregistre character varying(255)
);

CREATE TABLE itd_referenciaxml (
    referenciaxmlid bigint NOT NULL,
    resultat text,
    datacreacio timestamp without time zone,
    referenciaid bigint
);

CREATE TABLE itd_traza (
    trazaid bigint NOT NULL,
    nom character varying(255),
    valor character varying(255),
    datacreacio timestamp without time zone,
    referenciaid bigint
);

CREATE INDEX int_acces_pk_i ON itd_acces USING btree (accesid);
CREATE INDEX int_aplicacio_pk_i ON itd_aplicacio USING btree (aplicacioid);
CREATE INDEX int_metadada_metadadaid_fk_i ON itd_metadada USING btree (metadadaid);
CREATE INDEX int_metadada_pk_i ON itd_metadada USING btree (metadadaid);
CREATE INDEX int_peticioatercer_pk_i ON itd_peticioatercer USING btree (peticioid);
CREATE INDEX int_referencia_pk_i ON itd_referencia USING btree (referenciaid);
CREATE INDEX int_referenciaxml_pk_i ON itd_referenciaxml USING btree (referenciaxmlid);
CREATE INDEX int_refxml_referenciaid_fk_i ON itd_referenciaxml USING btree (referenciaid);
CREATE INDEX int_refxml_referenciaxmlid_fk_i ON itd_referenciaxml USING btree (referenciaxmlid);
CREATE INDEX int_traza_pk_i ON itd_traza USING btree (trazaid);
CREATE INDEX int_traza_trazaid_fk_i ON itd_traza USING btree (trazaid);

ALTER TABLE itd_peticioatercer
    ADD CONSTRAINT int_peticioatercer_pk PRIMARY KEY (peticioid);

ALTER TABLE itd_referencia
    ADD CONSTRAINT int_referencia_pk PRIMARY KEY (referenciaid);

ALTER TABLE itd_referenciaxml
    ADD CONSTRAINT int_referenciaxml_pk PRIMARY KEY (referenciaxmlid);

ALTER TABLE itd_traza
    ADD CONSTRAINT int_traza_pk PRIMARY KEY (trazaid);

ALTER TABLE itd_acces
    ADD CONSTRAINT itd_acces_pk PRIMARY KEY (accesid);

ALTER TABLE itd_aplicacio
    ADD CONSTRAINT itd_aplicacio_pk PRIMARY KEY (aplicacioid);

ALTER TABLE itd_entitat
    ADD CONSTRAINT itd_entitat_pkey PRIMARY KEY (entitatid);

ALTER TABLE itd_fitxer
    ADD CONSTRAINT itd_fitxer_pkey PRIMARY KEY (fitxerid);

ALTER TABLE itd_infoarxiu
    ADD CONSTRAINT itd_infoarxiu_pkey PRIMARY KEY (infoarxiuid);

ALTER TABLE itd_infosignatura
    ADD CONSTRAINT itd_infosignatura_pkey PRIMARY KEY (infosignaturaid);

ALTER TABLE itd_log
    ADD CONSTRAINT itd_log_pkey PRIMARY KEY (logid);

ALTER TABLE itd_metadada
    ADD CONSTRAINT itd_metadada_pk PRIMARY KEY (metadadaid);

ALTER TABLE itd_plugin
    ADD CONSTRAINT itd_plugin_pkey PRIMARY KEY (pluginid);

ALTER TABLE itd_acces
    ADD CONSTRAINT int_acces_referencia_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia(referenciaid);

ALTER TABLE ONLY itd_referenciaxml
    ADD CONSTRAINT int_refxml_referencia_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia(referenciaid);

ALTER TABLE ONLY itd_traza
    ADD CONSTRAINT int_traza_referencia_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia(referenciaid);

ALTER TABLE ONLY itd_metadada
    ADD CONSTRAINT itd_metadada_referencia_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia(referenciaid);
