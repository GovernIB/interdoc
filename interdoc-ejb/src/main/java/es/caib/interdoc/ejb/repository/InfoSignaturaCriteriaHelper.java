package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.InfoSignatura;
import es.caib.interdoc.persistence.model.InfoSignatura_;
import es.caib.interdoc.service.model.InfoSignaturaAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class InfoSignaturaCriteriaHelper extends AbstractCriteriaHelper<InfoSignatura, InfoSignaturaAtribut> {

    public InfoSignaturaCriteriaHelper(CriteriaBuilder builder, Root<InfoSignatura> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(InfoSignaturaAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(InfoSignatura_.id);
            case signOperation:
            	return root.get(InfoSignatura_.signOperation);
            case signType:
            	return root.get(InfoSignatura_.signType);
            case signAlgorithm:
            	return root.get(InfoSignatura_.signAlgorithm);
            case signMode:
            	return root.get(InfoSignatura_.signMode);
            case signaturesTableLocation:
            	return root.get(InfoSignatura_.signaturesTableLocation);
            case timestampIncluded:
            	return root.get(InfoSignatura_.timestampIncluded);
            case policyIncluded:
            	return root.get(InfoSignatura_.policyIncluded);
            case eniTipoFirma:
            	return root.get(InfoSignatura_.eniTipoFirma);
            case eniPerfilFirma:
            	return root.get(InfoSignatura_.eniPerfilFirma);
            case eniRolFirma:
            	return root.get(InfoSignatura_.eniRolFirma);
            case eniSignerName:
            	return root.get(InfoSignatura_.eniSignerName);
            case eniSignerAdministrationId:
            	return root.get(InfoSignatura_.eniSignerAdministrationId);
            case checkAdministrationIdOfSigner:
            	return root.get(InfoSignatura_.checkAdministrationIdOfSigner);
            case checkDocumentModifications:
            	return root.get(InfoSignatura_.checkDocumentModifications);
            case checkValidationSignature:
            	return root.get(InfoSignatura_.checkValidationSignature);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
