
    create sequence itd_acces_seq start with 1000 increment by  1;
	create sequence itd_aplicacio_seq start with 1000 increment by  1;
	create sequence itd_entitat_seq start with 1000 increment by  1;
	create sequence itd_fitxer_seq start with 1000 increment by  1;
	create sequence itd_infoarxiu_seq start with 1000 increment by  1;
	create sequence itd_infosignatura_seq start with 1000 increment by  1;
	create sequence itd_log_seq start with 1000 increment by  1;
	create sequence itd_metadada_seq start with 1000 increment by  1;
	create sequence itd_peticioatercer_seq start with 1000 increment by  1;
	create sequence itd_plugin_seq start with 1000 increment by  1;
	create sequence itd_referencia_seq start with 1000 increment by  1;
	create sequence itd_refxml_seq start with 1000 increment by  1;
	create sequence itd_traza_seq start with 1000 increment by  1;


	CREATE TABLE itd_acces (
    accesid number(19) NOT NULL,
    identificacio varchar2(50),
    tipusidentificacio varchar2(50),
    ip varchar2(20),
    datacreacio timestamp,
    referenciaid number(19),
	primary key (accesid)
);

CREATE TABLE itd_aplicacio (
    aplicacioid number(19) NOT NULL,
    usuari varchar2(50),
    clau varchar2(100),
    nom varchar2(255),
    codidir3 varchar2(50) NOT NULL,
    datacreacio timestamp,
    estat number(10),
	primary key (aplicacioid)
);

CREATE TABLE itd_entitat (
    entitatid number(19) NOT NULL,
    nom varchar2(255) NOT NULL,
    codidir3 varchar2(50),
    datacreacio timestamp,
    actiu number(10) NOT NULL,
	primary key (entitatid)
);

CREATE TABLE itd_fitxer (
    fitxerid number(19) NOT NULL,
    nom varchar2(255) NOT NULL,
    descripcio varchar2(1000),
    mime varchar2(255) NOT NULL,
    tamany number(19) NOT NULL,
    datacreacio timestamp DEFAULT SYSTIMESTAMP,
    ruta varchar2(255),
	primary key (fitxerid)
);

CREATE TABLE itd_infoarxiu (
    infoarxiuid number(19) NOT NULL,
    originalfileurl varchar2(255),
    csv varchar2(255),
    csvgenerationdefinition varchar2(255),
    csvvalidationweb varchar2(255),
    arxiuexpedientid varchar2(255),
    arxiudocumentid varchar2(255),
    printableurl varchar2(255),
    enifileurl varchar2(255),
    validationfileurl varchar2(255),
    estatexpedient varchar2(5),
	primary key (infoarxiuid)
);

CREATE TABLE itd_infosignatura (
    infosignaturaid number(19) NOT NULL,
    signoperation number(10) NOT NULL,
    signtype varchar2(255) NOT NULL,
    signalgorithm varchar2(255),
    signmode number(10),
    signaturestablelocation number(10),
    timestampincluded char(1),
    policyincluded char(1),
    enitipofirma varchar2(255),
    eniperfilfirma varchar2(255),
    enirolfirma varchar2(255),
    enisignername varchar2(255),
    enisigneradministrationid varchar2(255),
    enisignlevel varchar2(255),
    checkadministrationidofsigner char(1),
    checkdocumentmodifications char(1),
    checkvalidationsignature char(1),
    signdate timestamp,
    "signId" varchar2(255),
    filename varchar2(255),
    filemime varchar2(255),
	primary key (infosignaturaid)
);

CREATE TABLE itd_log (
    logid number(19) NOT NULL,
    descripcio clob,
    peticio varchar2(255),
    excepcio clob,
    datacreacio timestamp,
	primary key (logid)
);

CREATE TABLE itd_metadada (
    metadadaid number(19) NOT NULL,
    nom varchar2(255),
    valor varchar2(255),
    datacreacio timestamp NOT NULL,
    referenciaid number(19) NOT NULL,
	primary key (metadadaid)
);

CREATE TABLE itd_peticioatercer (
    peticioid number(19) NOT NULL,
    csvidentificador varchar2(255),
    eniidentificador varchar2(255),
    codidir3 varchar2(20),
    recuperaciooriginal char(1),
    documenteni varchar2(255),
    nif varchar2(50),
    tipusidentificacio number(10),
    ip varchar2(20),
    datacreacio timestamp,
    estat number(10),
	primary key (peticioid)
);

CREATE TABLE itd_plugin (
    pluginid number(19) NOT NULL,
    nom varchar2(100),
    propietats clob,
    classe varchar2(255),
    datacreacio timestamp with time zone,
    actiu number(10),
    entitatid number(19) NOT NULL,
    tipus number(19),
	primary key (pluginid)
);

CREATE TABLE itd_referencia (
    referenciaid number(19) NOT NULL,
    csvidentificador varchar2(255),
    uuid varchar2(255),
    direccio varchar2(255),
    hash varchar2(255),
    emisor varchar2(50) NOT NULL,
    receptor varchar2(50) NOT NULL,
    urlvisible varchar2(255),
    datacreacio timestamp,
    referencia varchar2(255),
    entitatid number(19),
    infosignaturaid number(19),
    infoarxiuid number(19),
    formatfirma varchar2(5),
    fitxerid number(19),
	primary key(referenciaid)
);

CREATE TABLE itd_referenciaxml (
    referenciaxmlid number(19) NOT NULL,
    resultat clob,
    datacreacio timestamp,
    referenciaid number(19),
	primary key (referenciaxmlid)
);

CREATE TABLE itd_traza (
    trazaid number(19) NOT NULL,
    nom varchar2(255),
    valor varchar2(255),
    datacreacio timestamp,
    referenciaid number(19),
	primary key(trazaid)
);

CREATE INDEX itd_acces_referenciaid_fk_i on itd_acces (referenciaid);
CREATE INDEX itd_metadada_referenciaid_fk_i ON itd_metadada (referenciaid);
CREATE INDEX itd_plugin_entitatid_fk_i ON itd_plugin (entitatid);
CREATE INDEX itd_referencia_entitatid_fk_i ON itd_referencia (entitatid);
CREATE INDEX itd_referencia_infoarxiuid_fk_i ON itd_referencia (infoarxiuid);
CREATE INDEX itd_referencia_infosignaturaid_fk_i ON itd_referencia (infosignaturaid);
CREATE INDEX itd_referencia_fitxerid_fk_i ON itd_referencia (fitxerid);
CREATE INDEX itd_refxml_referenciaid_fk_i ON itd_referenciaxml (referenciaid);
CREATE INDEX itd_traza_referenciaid_fk_i ON itd_traza (referenciaid);

   ALTER TABLE itd_acces 
ADD CONSTRAINT itd_accref_refid_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia;

ALTER TABLE itd_plugin
ADD CONSTRAINT itd_pluent_entid_fk FOREIGN KEY (entitatid) REFERENCES itd_entitat;

ALTER TABLE itd_referencia
ADD CONSTRAINT itd_refent_entid_fk FOREIGN KEY (entitatid) REFERENCES itd_entitat;

ALTER TABLE itd_referencia 
ADD CONSTRAINT itd_refinf_infosignatu_fk FOREIGN KEY (infosignaturaid) REFERENCES itd_infosignatura;

ALTER TABLE itd_referencia
ADD CONSTRAINT itd_refinf_infoarxiu_fk FOREIGN KEY (infoarxiuid) REFERENCES itd_infoarxiu;

ALTER TABLE itd_referencia 
ADD CONSTRAINT itd_reffit_fitxerid_fk FOREIGN KEY (fitxerid) REFERENCES itd_fitxer;

ALTER TABLE itd_referenciaxml
ADD CONSTRAINT itd_rxmref_refid_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia;

ALTER TABLE itd_traza
ADD CONSTRAINT itd_traref_refid_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia;

ALTER TABLE itd_metadada
ADD CONSTRAINT itd_metref_refid_fk FOREIGN KEY (referenciaid) REFERENCES itd_referencia;

    -- Grants per l'usuari www_interdoc
    -- seqüències
    GRANT SELECT, ALTER ON itd_acces_seq TO WWW_INTERDOC;
    GRANT SELECT, ALTER ON itd_aplicacio_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_entitat_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_fitxer_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_infoarxiu_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_infosignatura_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_log_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_metadada_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_peticioatercer_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_plugin_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_referencia_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_refxml_seq TO WWW_INTERDOC;
	GRANT SELECT, ALTER ON itd_traza_seq TO WWW_INTERDOC;
	
	
    -- taules
    GRANT SELECT, INSERT, UPDATE, DELETE ON itd_acces TO WWW_INTERDOC;
    GRANT SELECT, INSERT, UPDATE, DELETE ON itd_aplicacio TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_entitat TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_fitxer TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_infoarxiu TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_infosignatura TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_log TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_metadada TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_peticioatercer TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_plugin TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_referencia TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_referenciaxml TO WWW_INTERDOC;
	GRANT SELECT, INSERT, UPDATE, DELETE ON itd_traza TO WWW_INTERDOC;


