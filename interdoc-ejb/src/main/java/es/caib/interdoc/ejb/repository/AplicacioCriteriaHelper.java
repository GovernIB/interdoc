package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Aplicacio;
import es.caib.interdoc.persistence.model.Aplicacio_;
import es.caib.interdoc.service.model.AplicacioAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class AplicacioCriteriaHelper extends AbstractCriteriaHelper<Aplicacio, AplicacioAtribut> {

    public AplicacioCriteriaHelper(CriteriaBuilder builder, Root<Aplicacio> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(AplicacioAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(Aplicacio_.id);
            case codiDir3:
                return root.get(Aplicacio_.codiDir3);
            case nom:
                return root.get(Aplicacio_.nom);
            case usuari:
                return root.get(Aplicacio_.usuari);
            case dataCreacio:
                return root.get(Aplicacio_.dataCreacio);
            case estat:
                return root.get(Aplicacio_.estat);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
