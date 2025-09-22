package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.persistence.model.Usuari_;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.UsuariAtribut;
import es.caib.interdoc.service.model.UsuariDTO;

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
@Local(UsuariRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UsuariRepositoryBean extends AbstractCrudRepository<Usuari, Long> implements UsuariRepository {

	protected UsuariRepositoryBean() {
		super(Usuari.class);
	}

	@Override
	public List<UsuariDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
			Map<UsuariAtribut, Object> filter, List<Ordre<UsuariAtribut>> ordenacio) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsuariDTO> criteriaQuery = builder.createQuery(UsuariDTO.class);
		Root<Usuari> root = criteriaQuery.from(Usuari.class);

		criteriaQuery.select(builder.construct(UsuariDTO.class, root.get(Usuari_.usuariId), root.get(Usuari_.username),
				root.get(Usuari_.nom), root.get(Usuari_.llinatge1), root.get(Usuari_.llinatge2), root.get(Usuari_.email), root.get(Usuari_.nif)));

		UsuariCriteriaHelper usuariCriteriaHelper = new UsuariCriteriaHelper(builder, root);
		criteriaQuery.where(usuariCriteriaHelper.getPredicates(filter));
		criteriaQuery.orderBy(usuariCriteriaHelper.getOrderList(ordenacio));

		TypedQuery<UsuariDTO> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		return query.getResultList();
	}

	@Override
	public UsuariDTO findByUsername(String codi) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsuariDTO> criteriaQuery = builder.createQuery(UsuariDTO.class);
		Root<Usuari> root = criteriaQuery.from(Usuari.class);

		criteriaQuery.select(builder.construct(UsuariDTO.class, root.get(Usuari_.usuariId), root.get(Usuari_.username)));

		// TODO afegir la columna de ACTIU

		UsuariCriteriaHelper usuariCriteriaHelper = new UsuariCriteriaHelper(builder, root);
		criteriaQuery.where(usuariCriteriaHelper.getPredicate(UsuariAtribut.username, codi));

		TypedQuery<UsuariDTO> query = entityManager.createQuery(criteriaQuery);
		List<UsuariDTO> resultats = query.getResultList();
		if (resultats.size() > 0)
			return resultats.get(0);
		else
			return null;
	}

	@Override
	public List<Usuari> getAll() {
		TypedQuery<Usuari> query = entityManager.createNamedQuery(Usuari.GET_ALL, Usuari.class);
		return query.getResultList();
	}

	@Override
	public long countByFilter(Map<UsuariAtribut, Object> filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Usuari> root = criteriaQuery.from(Usuari.class);

		criteriaQuery.select(builder.count(root));

		UsuariCriteriaHelper usuariCriteriaHelper = new UsuariCriteriaHelper(builder, root);
		criteriaQuery.where(usuariCriteriaHelper.getPredicates(filter));

		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
}
