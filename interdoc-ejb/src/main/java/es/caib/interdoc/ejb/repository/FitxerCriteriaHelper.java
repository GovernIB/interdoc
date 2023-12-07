package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Fitxer;
import es.caib.interdoc.persistence.model.Fitxer_;
import es.caib.interdoc.service.model.FitxerAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class FitxerCriteriaHelper extends AbstractCriteriaHelper<Fitxer, FitxerAtribut> {

    public FitxerCriteriaHelper(CriteriaBuilder builder, Root<Fitxer> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(FitxerAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(Fitxer_.id);
            case nom:
                return root.get(Fitxer_.nom);
            case dataCreacio:
                return root.get(Fitxer_.dataCreacio);
            case descripcio:
            	return root.get(Fitxer_.descripcio);
            case mime:
                return root.get(Fitxer_.mime);
            case ruta:
            	return root.get(Fitxer_.ruta);
            case tamany:
            	return root.get(Fitxer_.tamany);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
