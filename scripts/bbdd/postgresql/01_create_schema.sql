
    create sequence ITD_PROCEDIMENT_SEQ start 1 increment 1;
    create sequence ITD_UNITATORGANICA_SEQ start 1 increment 1;

    create table ITD_PROCEDIMENT (
       ID int8 not null,
        CODISIA varchar(8) not null,
        NOM varchar(50) not null,
        UNITATORGANICAID int8 not null,
        constraint ITD_PROCEDIMENT_PK primary key (ID)
    );

    create table ITD_UNITATORGANICA (
       ID int8 not null,
        CODIDIR3 varchar(9) not null,
        DATACREACIO date not null,
        ESTAT int4 not null,
        NOM varchar(50) not null,
        constraint ITD_UNITAT_PK primary key (ID)
    );

    create index ITD_PROCEDIMENT_PK_I on ITD_PROCEDIMENT (ID);
    create index ITD_PROCEDIMENT_CODISIA_UK_I on ITD_PROCEDIMENT (CODISIA);
    create index ITD_PROCEDIMENT_UNITAT_FK_I on ITD_PROCEDIMENT (UNITATORGANICAID);

    alter table ITD_PROCEDIMENT 
       add constraint ITD_PROCEDIMENT_CODISIA_UK unique (CODISIA);

    create index ITD_UNITAT_PK_I on ITD_UNITATORGANICA (ID);
    create index ITD_UNITAT_CODIDIR3_UK_I on ITD_UNITATORGANICA (CODIDIR3);

    alter table ITD_UNITATORGANICA 
       add constraint ITD_UNITAT_CODIDIR3_UK unique (CODIDIR3);

    alter table ITD_PROCEDIMENT 
       add constraint ITD_PROCEDIMENT_UNITAT_FK 
       foreign key (UNITATORGANICAID) 
       references ITD_UNITATORGANICA;
