package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.InfoArxiu;
import es.caib.interdoc.persistence.model.InfoArxiu_;
import es.caib.interdoc.service.model.InfoArxiuAtribut;
import es.caib.interdoc.service.model.InfoArxiuDTO;
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
@Local(InfoArxiuRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class InfoArxiuRepositoryBean extends AbstractCrudRepository<InfoArxiu, Long>
        implements InfoArxiuRepository {

    protected InfoArxiuRepositoryBean() {
        super(InfoArxiu.class);
    }

    @Override
    public List<InfoArxiuDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<InfoArxiuAtribut, Object> filter,
                                                        List<Ordre<InfoArxiuAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<InfoArxiuDTO> criteriaQuery = builder.createQuery(InfoArxiuDTO.class);
        Root<InfoArxiu> root = criteriaQuery.from(InfoArxiu.class);

        criteriaQuery.select(builder.construct(InfoArxiuDTO.class,
                root.get(InfoArxiu_.id),
                root.get(InfoArxiu_.originalFileUrl),
                root.get(InfoArxiu_.csv),
                root.get(InfoArxiu_.csvGenerationDefinition),
                root.get(InfoArxiu_.csvValidationWeb),
                root.get(InfoArxiu_.arxiuExpedientId),
                root.get(InfoArxiu_.arxiuDocumentId),
                root.get(InfoArxiu_.printableUrl),
                root.get(InfoArxiu_.eniFileUrl),
                root.get(InfoArxiu_.validationFileUrl)));

        InfoArxiuCriteriaHelper infoArxiuCriteriaHelper = new InfoArxiuCriteriaHelper(builder, root);
        criteriaQuery.where(infoArxiuCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(infoArxiuCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<InfoArxiuDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<InfoArxiu> getAll() {
        TypedQuery<InfoArxiu> query = entityManager.createNamedQuery(InfoArxiu.GET_ALL, InfoArxiu.class);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<InfoArxiuAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<InfoArxiu> root = criteriaQuery.from(InfoArxiu.class);

        criteriaQuery.select(builder.count(root));

        InfoArxiuCriteriaHelper infoArxiuCriteriaHelper = new InfoArxiuCriteriaHelper(builder, root);
        criteriaQuery.where(infoArxiuCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
