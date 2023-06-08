package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Log;
import es.caib.interdoc.persistence.model.Log_;
import es.caib.interdoc.service.model.LogAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class LogCriteriaHelper extends AbstractCriteriaHelper<Log, LogAtribut> {

    public LogCriteriaHelper(CriteriaBuilder builder, Root<Log> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Acces>}.
     */
    @Override
    protected Path<?> getPath(LogAtribut atribut) {
        switch (atribut) {
            case id:
                return root.get(Log_.id);
            case descripcio:
                return root.get(Log_.descripcio);
            case peticio:
                return root.get(Log_.peticio);
            case excepcio:
            	return root.get(Log_.excepcio);
            case dataCreacio:
                return root.get(Log_.dataCreacio);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
