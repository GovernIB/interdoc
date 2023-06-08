package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.persistence.model.Plugin_;
import es.caib.interdoc.service.model.PluginAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class PluginCriteriaHelper extends AbstractCriteriaHelper<Plugin, PluginAtribut> {

    public PluginCriteriaHelper(CriteriaBuilder builder, Root<Plugin> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(PluginAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(Plugin_.id);
            case nom:
                return root.get(Plugin_.nom);
            case classe:
            	return root.get(Plugin_.classe);
            case propietats:
                return root.get(Plugin_.propietats);
            case dataCreacio:
                return root.get(Plugin_.dataCreacio);
            case actiu:
                return root.get(Plugin_.actiu);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
