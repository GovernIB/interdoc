package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Log;
import es.caib.interdoc.persistence.model.Log_;
import es.caib.interdoc.service.model.LogAtribut;
import es.caib.interdoc.service.model.LogDTO;
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
 * Implementació del repositori Log.
 *
 * @author jagarcia
 */
@Stateless
@Local(LogRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class LogRepositoryBean extends AbstractCrudRepository<Log, Long>
        implements LogRepository {

    protected LogRepositoryBean() {
        super(Log.class);
    }


    @Override
    public List<LogDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<LogAtribut, Object> filter,
                                                        List<Ordre<LogAtribut>> ordenacio) {


        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LogDTO> criteriaQuery = builder.createQuery(LogDTO.class);
        Root<Log> root = criteriaQuery.from(Log.class);

        criteriaQuery.select(builder.construct(LogDTO.class,
                root.get(Log_.id),
                root.get(Log_.descripcio),
                root.get(Log_.peticio),
                root.get(Log_.excepcio),
                root.get(Log_.dataCreacio)));

        LogCriteriaHelper logCriteriaHelper = new LogCriteriaHelper(builder, root);
        criteriaQuery.where(logCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(logCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<LogDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<LogAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Log> root = criteriaQuery.from(Log.class);

        criteriaQuery.select(builder.count(root));

        LogCriteriaHelper logCriteriaHelper = new LogCriteriaHelper(builder, root);
        criteriaQuery.where(logCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
