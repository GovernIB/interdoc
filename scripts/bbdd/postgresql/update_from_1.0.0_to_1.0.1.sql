CREATE SEQUENCE itd_usuari_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1000
  CACHE 1;
ALTER TABLE itd_usuari_seq
  OWNER TO interdoc;

CREATE SEQUENCE itd_usuarientitat_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1000
  CACHE 1;
ALTER TABLE itd_usuarientitat_seq
  OWNER TO interdoc;

-- Taula Idioma --
CREATE TABLE itd_idioma
(
  idiomaid character varying(5) NOT NULL,
  nom character varying(50) NOT NULL,
  suportat boolean NOT NULL DEFAULT true,
  ordre integer NOT NULL DEFAULT 0,
  CONSTRAINT itd_idioma_pk PRIMARY KEY (idiomaid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE itd_idioma
  OWNER TO interdoc;

-- Index: car_idioma_pk_i

-- DROP INDEX car_idioma_pk_i;

CREATE INDEX itd_idioma_pk_i
  ON itd_idioma
  USING btree
  (idiomaid COLLATE pg_catalog."default");


ALTER TABLE itd_usuari
  ADD CONSTRAINT itd_usuari_idioma_idi_fk FOREIGN KEY (idiomaid)
      REFERENCES itd_idioma (idiomaid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;




-- Taula de Usuaris -- 

CREATE TABLE itd_usuari
(
  usuariid bigint NOT NULL DEFAULT nextval('itd_usuari_seq'::regclass),
  username character varying(255) NOT NULL,
  idiomaid character varying(5) NOT NULL,
  darreraentitat bigint,
  nom character varying(255) NOT NULL,
  llinatge1 character varying(255) NOT NULL,
  llinatge2 character varying(255),
  email character varying(255),
  nif character varying(255),
  CONSTRAINT itd_usuari_pk PRIMARY KEY (usuariid),
  CONSTRAINT itd_usuari_entitat_fk FOREIGN KEY (darreraentitat)
      REFERENCES itd_entitat (entitatid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT itd_usuari_nif_uk UNIQUE (nif),
  CONSTRAINT itd_usuari_username_uk UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE itd_usuari
  OWNER TO interdoc;

-- Index: fki_itd_usuari_entitat_fk

-- DROP INDEX fki_itd_usuari_entitat_fk;

CREATE INDEX fki_itd_usuari_entitat_fk
  ON itd_usuari
  USING btree
  (darreraentitat);

-- Index: itd_usuari_darreraentitat_fk_i

-- DROP INDEX itd_usuari_darreraentitat_fk_i;

CREATE INDEX itd_usuari_darreraentitat_fk_i
  ON itd_usuari
  USING btree
  (darreraentitat);

-- Index: itd_usuari_idiomaid_fk_i

-- DROP INDEX itd_usuari_idiomaid_fk_i;

CREATE INDEX itd_usuari_idiomaid_fk_i
  ON itd_usuari
  USING btree
  (idiomaid COLLATE pg_catalog."default");



-- Taula UsuariEntitat --
CREATE TABLE itd_usuarientitat
(
  usuarientitatid bigint NOT NULL DEFAULT nextval('itd_usuarientitat_seq'::regclass),
  usuariid bigint NOT NULL,
  entitatid bigint NOT NULL,
  actiu boolean NOT NULL,
  CONSTRAINT itd_usuarientitat_pk PRIMARY KEY (usuarientitatid),
  CONSTRAINT itd_usuent_entitat_fk FOREIGN KEY (entitatid)
      REFERENCES itd_entitat (entitatid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT itd_usuent_usuari_fk FOREIGN KEY (usuariid)
      REFERENCES itd_usuari (usuariid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT itd_usuent_usu_ent_uk UNIQUE (usuariid, entitatid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE itd_usuarientitat
  OWNER TO interdoc;

-- Index: car_usuarientitat_pk_i

-- DROP INDEX car_usuarientitat_pk_i;

CREATE INDEX itd_usuarientitat_pk_i
  ON itd_usuarientitat
  USING btree
  (usuarientitatid);

-- Index: car_usuent_entitatid_fk_i

-- DROP INDEX car_usuent_entitatid_fk_i;

CREATE INDEX itd_usuent_entitatid_fk_i
  ON itd_usuarientitat
  USING btree
  (entitatid);

-- Index: car_usuent_usuariid_fk_i

-- DROP INDEX car_usuent_usuariid_fk_i;

CREATE INDEX itd_usuent_usuariid_fk_i
  ON itd_usuarientitat
  USING btree
  (usuariid);
  
  
INSERT INTO itd_idioma (idiomaid, nom, suportat, ordre)
VALUES
    ('ca', 'Català', TRUE, 0),
    ('es', 'Castellano', TRUE, 1);


