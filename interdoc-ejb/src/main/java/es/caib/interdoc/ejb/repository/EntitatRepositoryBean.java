package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Entitat;
import es.caib.interdoc.persistence.model.Entitat_;
import es.caib.interdoc.service.model.EntitatAtribut;
import es.caib.interdoc.service.model.EntitatDTO;
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
@Local(EntitatRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EntitatRepositoryBean extends AbstractCrudRepository<Entitat, Long>
        implements EntitatRepository {

    protected EntitatRepositoryBean() {
        super(Entitat.class);
    }


    @Override
    public List<EntitatDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<EntitatAtribut, Object> filter,
                                                        List<Ordre<EntitatAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EntitatDTO> criteriaQuery = builder.createQuery(EntitatDTO.class);
        Root<Entitat> root = criteriaQuery.from(Entitat.class);

        criteriaQuery.select(builder.construct(EntitatDTO.class,
                root.get(Entitat_.id),
                root.get(Entitat_.nom),
                root.get(Entitat_.codiDir3),
                root.get(Entitat_.dataCreacio),
                root.get(Entitat_.actiu)));

        EntitatCriteriaHelper entitatCriteriaHelper = new EntitatCriteriaHelper(builder, root);
        criteriaQuery.where(entitatCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(entitatCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<EntitatDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Entitat> getAll() {
        TypedQuery<Entitat> query = entityManager.createNamedQuery(Entitat.GET_ALL, Entitat.class);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<EntitatAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Entitat> root = criteriaQuery.from(Entitat.class);

        criteriaQuery.select(builder.count(root));

        EntitatCriteriaHelper entitatCriteriaHelper = new EntitatCriteriaHelper(builder, root);
        criteriaQuery.where(entitatCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
