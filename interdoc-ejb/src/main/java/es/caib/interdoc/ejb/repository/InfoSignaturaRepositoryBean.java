package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.InfoSignatura;
import es.caib.interdoc.persistence.model.InfoSignatura_;
import es.caib.interdoc.service.model.InfoSignaturaAtribut;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import es.caib.interdoc.service.model.Ordre;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(InfoSignaturaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class InfoSignaturaRepositoryBean extends AbstractCrudRepository<InfoSignatura, Long>
        implements InfoSignaturaRepository {

    protected InfoSignaturaRepositoryBean() {
        super(InfoSignatura.class);
    }

    @Override
    public List<InfoSignaturaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<InfoSignaturaAtribut, Object> filter,
                                                        List<Ordre<InfoSignaturaAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<InfoSignaturaDTO> criteriaQuery = builder.createQuery(InfoSignaturaDTO.class);
        Root<InfoSignatura> root = criteriaQuery.from(InfoSignatura.class);

        criteriaQuery.select(builder.construct(InfoSignaturaDTO.class,
                root.get(InfoSignatura_.id),
                root.get(InfoSignatura_.signOperation),
                root.get(InfoSignatura_.signType),
                root.get(InfoSignatura_.signAlgorithm),
                root.get(InfoSignatura_.signMode),
                root.get(InfoSignatura_.signaturesTableLocation),
                root.get(InfoSignatura_.timestampIncluded),
                root.get(InfoSignatura_.policyIncluded),
                root.get(InfoSignatura_.eniTipoFirma),
                root.get(InfoSignatura_.eniPerfilFirma),
                root.get(InfoSignatura_.eniRolFirma),
                root.get(InfoSignatura_.eniSignerName),
                root.get(InfoSignatura_.eniSignerAdministrationId),
                root.get(InfoSignatura_.checkAdministrationIdOfSigner),
                root.get(InfoSignatura_.checkDocumentModifications),
                root.get(InfoSignatura_.checkValidationSignature),
                root.get(InfoSignatura_.signDate)
                ));

        InfoSignaturaCriteriaHelper infoSignaturaCriteriaHelper = new InfoSignaturaCriteriaHelper(builder, root);
        criteriaQuery.where(infoSignaturaCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(infoSignaturaCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<InfoSignaturaDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<InfoSignatura> getAll() {
        TypedQuery<InfoSignatura> query = entityManager.createNamedQuery(InfoSignatura.GET_ALL, InfoSignatura.class);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<InfoSignaturaAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<InfoSignatura> root = criteriaQuery.from(InfoSignatura.class);

        criteriaQuery.select(builder.count(root));

        InfoSignaturaCriteriaHelper infoSignaturaCriteriaHelper = new InfoSignaturaCriteriaHelper(builder, root);
        criteriaQuery.where(infoSignaturaCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
