package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Traza;
import es.caib.interdoc.persistence.model.Traza_;
import es.caib.interdoc.service.model.TrazaAtribut;
import es.caib.interdoc.service.model.TrazaDTO;
import es.caib.interdoc.service.model.Ordre;

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
@Local(TrazaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TrazaRepositoryBean extends AbstractCrudRepository<Traza, Long>
        implements TrazaRepository {

    protected TrazaRepositoryBean() {
        super(Traza.class);
    }


    @Override
    public List<TrazaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<TrazaAtribut, Object> filter,
                                                        List<Ordre<TrazaAtribut>> ordenacio) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrazaDTO> criteriaQuery = builder.createQuery(TrazaDTO.class);
        Root<Traza> root = criteriaQuery.from(Traza.class);

        criteriaQuery.select(builder.construct(TrazaDTO.class,
                root.get(Traza_.id),
                root.get(Traza_.nom),
                root.get(Traza_.valor),
                root.get(Traza_.referenciaId),
                root.get(Traza_.dataCreacio)));

        TrazaCriteriaHelper trazaCriteriaHelper = new TrazaCriteriaHelper(builder, root);
        criteriaQuery.where(trazaCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(trazaCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<TrazaDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<TrazaAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Traza> root = criteriaQuery.from(Traza.class);

        criteriaQuery.select(builder.count(root));

        TrazaCriteriaHelper trazaCriteriaHelper = new TrazaCriteriaHelper(builder, root);
        criteriaQuery.where(trazaCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
