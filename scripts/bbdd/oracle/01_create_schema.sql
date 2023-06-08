
    create sequence ITD_PROCEDIMENT_SEQ start with 1 increment by  1;
    create sequence ITD_UNITATORGANICA_SEQ start with 1 increment by  1;

    create table ITD_PROCEDIMENT (
        ID number(19,0) not null,
        CODISIA varchar2(8 char) not null,
        NOM varchar2(50 char) not null,
        UNITATORGANICAID number(19,0) not null
    );

    create table ITD_UNITATORGANICA (
        ID number(19,0) not null,
        CODIDIR3 varchar2(9 char) not null,
        DATACREACIO date not null,
        ESTAT number(10,0) not null,
        NOM varchar2(50 char) not null
    );

    create index ITD_PROCEDIMENT_PK_I on ITD_PROCEDIMENT (ID);
    create index ITD_PROCEDIMENT_CODISIA_UK_I on ITD_PROCEDIMENT (CODISIA);
    create index ITD_PROCEDIMENT_UNITAT_FK_I on ITD_PROCEDIMENT (UNITATORGANICAID);

    alter table ITD_PROCEDIMENT
        add constraint ITD_PROCEDIMENT_PK primary key (ID);

    alter table ITD_PROCEDIMENT
        add constraint ITD_PROCEDIMENT_CODISIA_UK unique (CODISIA);

    create index ITD_UNITAT_PK_I on ITD_UNITATORGANICA (ID);
    create index ITD_UNITAT_CODIDIR3_UK_I on ITD_UNITATORGANICA (CODIDIR3);

    alter table ITD_UNITATORGANICA
        add constraint ITD_UNITAT_PK primary key (ID);

    alter table ITD_UNITATORGANICA
        add constraint ITD_UNITAT_CODIDIR3_UK unique (CODIDIR3);

    alter table ITD_PROCEDIMENT
        add constraint ITD_PROCEDIMENT_UNITAT_FK
        foreign key (UNITATORGANICAID)
        references ITD_UNITATORGANICA;

    -- Grants per l'usuari www_interdoc
    -- seqüències
    GRANT SELECT, ALTER ON ITD_PROCEDIMENT_SEQ TO WWW_INTERDOC;
    GRANT SELECT, ALTER ON ITD_UNITATORGANICA_SEQ TO WWW_INTERDOC;
    -- taules
    GRANT SELECT, INSERT, UPDATE, DELETE ON ITD_PROCEDIMENT TO WWW_INTERDOC;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ITD_UNITATORGANICA TO WWW_INTERDOC;


