package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.ReferenciaXML;
import es.caib.interdoc.persistence.model.ReferenciaXML_;
import es.caib.interdoc.service.model.ReferenciaXMLAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class ReferenciaXMLCriteriaHelper extends AbstractCriteriaHelper<ReferenciaXML, ReferenciaXMLAtribut> {

    public ReferenciaXMLCriteriaHelper(CriteriaBuilder builder, Root<ReferenciaXML> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<ReferenciaXML>}.
     */
    @Override
    protected Path<?> getPath(ReferenciaXMLAtribut atribut) {
        switch (atribut) {
            case id:
                return root.get(ReferenciaXML_.id);
            case resultat:
                return root.get(ReferenciaXML_.resultat);
            case referenciaId:
                return root.get(ReferenciaXML_.referenciaId);
            case dataCreacio:
                return root.get(ReferenciaXML_.dataCreacio);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
