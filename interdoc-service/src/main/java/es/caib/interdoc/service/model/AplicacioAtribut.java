package es.caib.interdoc.service.model;

/**
 * Representació dels noms d'atributs que es poden emprar per filtrar o ordenadr aplicacions.
 * Els camps s'haurien de correspondre amb aquells presents a AplicacioDTO, i no al entity, ja que
 * aquesta és una abstracció de la capa de serveis i no de la capa de persistència.
 */
public enum AplicacioAtribut implements Atribut {
    id,
    codiDir3,
    nom,
    usuari,
    estat,
    dataCreacio
}
