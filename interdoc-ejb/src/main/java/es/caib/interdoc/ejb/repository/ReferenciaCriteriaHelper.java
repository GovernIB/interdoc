package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Referencia;
import es.caib.interdoc.persistence.model.Referencia_;
import es.caib.interdoc.service.model.ReferenciaAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class ReferenciaCriteriaHelper extends AbstractCriteriaHelper<Referencia, ReferenciaAtribut> {

    public ReferenciaCriteriaHelper(CriteriaBuilder builder, Root<Referencia> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Referencia>}.
     */
    @Override
    protected Path<?> getPath(ReferenciaAtribut atribut) {
        switch (atribut) {
            case id:
                return root.get(Referencia_.id);
            case csvId:
                return root.get(Referencia_.csvId);
            case uuId:
                return root.get(Referencia_.uuId);
            case referencia:
            	return root.get(Referencia_.referencia);
            case direccio:
                return root.get(Referencia_.direccio);
            case hash:
                return root.get(Referencia_.hash);
            case emisor:
                return root.get(Referencia_.emisor);
            case receptor:
                return root.get(Referencia_.receptor);
            case urlVisible:
                return root.get(Referencia_.urlVisible);
            case formatFirma:
                return root.get(Referencia_.formatFirma);
            case dataCreacio:
                return root.get(Referencia_.dataCreacio);
            case expedientId:
                return root.get(Referencia_.expedientId);
            case estatExpedientId:
                return root.get(Referencia_.estatExpedientId);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
