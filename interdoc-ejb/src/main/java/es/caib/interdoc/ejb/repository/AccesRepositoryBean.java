package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Acces;
import es.caib.interdoc.persistence.model.Acces_;
import es.caib.interdoc.service.model.AccesAtribut;
import es.caib.interdoc.service.model.AccesDTO;
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
import java.util.Optional;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(AccesRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccesRepositoryBean extends AbstractCrudRepository<Acces, Long>
        implements AccesRepository {

    protected AccesRepositoryBean() {
        super(Acces.class);
    }


    @Override
    public List<AccesDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<AccesAtribut, Object> filter,
                                                        List<Ordre<AccesAtribut>> ordenacio) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccesDTO> criteriaQuery = builder.createQuery(AccesDTO.class);
        Root<Acces> root = criteriaQuery.from(Acces.class);

        criteriaQuery.select(builder.construct(AccesDTO.class,
                root.get(Acces_.id),
                root.get(Acces_.identificacio),
                root.get(Acces_.tipusIdentificacio),
                root.get(Acces_.ip),
                root.get(Acces_.referenciaId),
                root.get(Acces_.dataCreacio)));

        AccesCriteriaHelper accesCriteriaHelper = new AccesCriteriaHelper(builder, root);
        criteriaQuery.where(accesCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(accesCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<AccesDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<AccesAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Acces> root = criteriaQuery.from(Acces.class);

        criteriaQuery.select(builder.count(root));

        AccesCriteriaHelper accesCriteriaHelper = new AccesCriteriaHelper(builder, root);
        criteriaQuery.where(accesCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }


	@Override
	public List<AccesDTO> findByRefenciaId(Long referenciaId) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccesDTO> criteriaQuery = builder.createQuery(AccesDTO.class);
        Root<Acces> root = criteriaQuery.from(Acces.class);
		
        criteriaQuery.select(builder.construct(AccesDTO.class,
        		root.get(Acces_.id),
                root.get(Acces_.identificacio),
                root.get(Acces_.tipusIdentificacio),
                root.get(Acces_.ip),
                root.get(Acces_.referenciaId),
                root.get(Acces_.dataCreacio)
        		));
        
        AccesCriteriaHelper accesCriteriaHelper = new AccesCriteriaHelper(builder, root);
        criteriaQuery.where(accesCriteriaHelper.getPredicate(AccesAtribut.referenciaId, referenciaId));
        
        TypedQuery<AccesDTO> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
		
	}
}
