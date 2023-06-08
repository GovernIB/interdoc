package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.ReferenciaXML;
import es.caib.interdoc.persistence.model.ReferenciaXML_;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.ReferenciaXMLAtribut;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(ReferenciaXMLRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ReferenciaXMLRepositoryBean extends AbstractCrudRepository<ReferenciaXML, Long>
        implements ReferenciaXMLRepository {

    protected ReferenciaXMLRepositoryBean() {
        super(ReferenciaXML.class);
    }


    @Override
    public List<ReferenciaXMLDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<ReferenciaXMLAtribut, Object> filter,
                                                        List<Ordre<ReferenciaXMLAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReferenciaXMLDTO> criteriaQuery = builder.createQuery(ReferenciaXMLDTO.class);
        Root<ReferenciaXML> root = criteriaQuery.from(ReferenciaXML.class);

        criteriaQuery.select(builder.construct(ReferenciaXMLDTO.class,
                root.get(ReferenciaXML_.id),
                root.get(ReferenciaXML_.resultat),
                root.get(ReferenciaXML_.referenciaId),
                root.get(ReferenciaXML_.dataCreacio)
        ));

        ReferenciaXMLCriteriaHelper referenciaXMLCriteriaHelper = new ReferenciaXMLCriteriaHelper(builder, root);
        criteriaQuery.where(referenciaXMLCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(referenciaXMLCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<ReferenciaXMLDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<ReferenciaXMLAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<ReferenciaXML> root = criteriaQuery.from(ReferenciaXML.class);

        criteriaQuery.select(builder.count(root));

        ReferenciaXMLCriteriaHelper referenciaXMLCriteriaHelper = new ReferenciaXMLCriteriaHelper(builder, root);
        criteriaQuery.where(referenciaXMLCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
