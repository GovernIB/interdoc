package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.UsuariEntitat;
import es.caib.interdoc.persistence.model.UsuariEntitat_;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

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
@Local(UsuariEntitatRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UsuariEntitatRepositoryBean extends AbstractCrudRepository<UsuariEntitat, Long> implements UsuariEntitatRepository {

	protected UsuariEntitatRepositoryBean() {
		super(UsuariEntitat.class);
	}

	@Override
	public List<UsuariEntitatDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
			Map<UsuariEntitatAtribut, Object> filter, List<Ordre<UsuariEntitatAtribut>> ordenacio) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsuariEntitatDTO> criteriaQuery = builder.createQuery(UsuariEntitatDTO.class);
		Root<UsuariEntitat> root = criteriaQuery.from(UsuariEntitat.class);

		criteriaQuery.select(builder.construct(UsuariEntitatDTO.class, root.get(UsuariEntitat_.usuariEntitatId), root.get(UsuariEntitat_.usuariId),
				root.get(UsuariEntitat_.entitatId), root.get(UsuariEntitat_.actiu)));

		UsuariEntitatCriteriaHelper usuariCriteriaHelper = new UsuariEntitatCriteriaHelper(builder, root);
		criteriaQuery.where(usuariCriteriaHelper.getPredicates(filter));
		criteriaQuery.orderBy(usuariCriteriaHelper.getOrderList(ordenacio));

		TypedQuery<UsuariEntitatDTO> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		return query.getResultList();
	}

	@Override
	public UsuariEntitatDTO findByUsuariId(Long usuariId) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsuariEntitatDTO> criteriaQuery = builder.createQuery(UsuariEntitatDTO.class);
		Root<UsuariEntitat> root = criteriaQuery.from(UsuariEntitat.class);

		criteriaQuery.select(builder.construct(UsuariEntitatDTO.class, root.get(UsuariEntitat_.usuariEntitatId), root.get(UsuariEntitat_.usuariId)));

		// TODO afegir la columna de ACTIU

		UsuariEntitatCriteriaHelper usuariCriteriaHelper = new UsuariEntitatCriteriaHelper(builder, root);
		criteriaQuery.where(usuariCriteriaHelper.getPredicate(UsuariEntitatAtribut.usuariId, usuariId));

		TypedQuery<UsuariEntitatDTO> query = entityManager.createQuery(criteriaQuery);
		List<UsuariEntitatDTO> resultats = query.getResultList();
		if (resultats.size() > 0)
			return resultats.get(0);
		else
			return null;
	}

	@Override
	public List<UsuariEntitat> getAll() {
		TypedQuery<UsuariEntitat> query = entityManager.createNamedQuery(UsuariEntitat.GET_ALL, UsuariEntitat.class);
		return query.getResultList();
	}

	@Override
	public long countByFilter(Map<UsuariEntitatAtribut, Object> filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<UsuariEntitat> root = criteriaQuery.from(UsuariEntitat.class);

		criteriaQuery.select(builder.count(root));

		UsuariEntitatCriteriaHelper usuariEntitatCriteriaHelper = new UsuariEntitatCriteriaHelper(builder, root);
		criteriaQuery.where(usuariEntitatCriteriaHelper.getPredicates(filter));

		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
}
