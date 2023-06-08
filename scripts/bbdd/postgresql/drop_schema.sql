
    alter table ITD_PROCEDIMENT 
       drop constraint ITD_PROCEDIMENT_UNITAT_FK;

    drop table if exists ITD_PROCEDIMENT cascade;

    drop table if exists ITD_UNITATORGANICA cascade;

    drop sequence if exists ITD_PROCEDIMENT_SEQ;

    drop sequence if exists ITD_UNITATORGANICA_SEQ;
