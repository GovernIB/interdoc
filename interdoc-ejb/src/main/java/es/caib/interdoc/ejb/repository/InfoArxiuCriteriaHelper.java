package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.InfoArxiu;
import es.caib.interdoc.persistence.model.InfoArxiu_;
import es.caib.interdoc.service.model.InfoArxiuAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class InfoArxiuCriteriaHelper extends AbstractCriteriaHelper<InfoArxiu, InfoArxiuAtribut> {

    public InfoArxiuCriteriaHelper(CriteriaBuilder builder, Root<InfoArxiu> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(InfoArxiuAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(InfoArxiu_.id);
            case originalFileUrl:
            	return root.get(InfoArxiu_.originalFileUrl);
            case csv: 
            	return root.get(InfoArxiu_.csv);
            case csvGenerationDefinition:
            	return root.get(InfoArxiu_.csvGenerationDefinition);
            case csvValidationWeb:
            	return root.get(InfoArxiu_.csvValidationWeb);
            case arxiuExpedientId:
            	return root.get(InfoArxiu_.arxiuExpedientId);
            case arxiuDocumentId:
            	return root.get(InfoArxiu_.arxiuDocumentId);
            case printableUrl:
            	return root.get(InfoArxiu_.printableUrl);
            case eniFileUrl:
            	return root.get(InfoArxiu_.eniFileUrl);
            case validationFileUrl:
            	return root.get(InfoArxiu_.validationFileUrl);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
