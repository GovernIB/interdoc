package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.UsuariEntitatConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.UsuariEntitatRepository;
import es.caib.interdoc.persistence.model.UsuariEntitat;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació dels casos d'ús de manteniment de aplicacions. És
 * responsabilitat d'aquesta capa definir el limit de les transaccions i la
 * seguretat. Les excepcions específiques es llancen mitjançant
 * l'{@link ExceptionTranslate} que transforma els errors JPA amb les excepcions
 * de servei com la {@link RecursNoTrobatException}
 *
 * @author jagarcia
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(UsuariEntitatServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UsuariEntitatServiceFacadeBean implements UsuariEntitatServiceFacade {

	@Inject
	private UsuariEntitatRepository repository;

	@Inject
	private UsuariEntitatConverter converter;

	@Override
	@RolesAllowed(Constants.ITD_ADMIN)
	public Long create(UsuariEntitatDTO dto) {
		UsuariEntitat usuariEntitat = converter.toEntity(dto);
		repository.create(usuariEntitat);
		return usuariEntitat.getUsuariEntitatId();
	}

	@Override
	@RolesAllowed(Constants.ITD_ADMIN)
	public void update(UsuariEntitatDTO dto) throws RecursNoTrobatException {
		UsuariEntitat usuariEntitat = repository.getReference(dto.getUsuariEntitatId());
		converter.updateFromDTO(usuariEntitat, dto);
	}

	@Override
	@RolesAllowed(Constants.ITD_ADMIN)
	public void delete(Long usuariEntitatId) throws RecursNoTrobatException {
		UsuariEntitat usuariEntitat = repository.getReference(usuariEntitatId);
		repository.delete(usuariEntitat);
	}

	@Override
	@RolesAllowed({ Constants.ITD_USER, Constants.ITD_ADMIN })
	public Optional<UsuariEntitatDTO> findById(Long usuariEntitatId) {
		UsuariEntitat usuariEntitat = repository.findById(usuariEntitatId);
		UsuariEntitatDTO usuariEntitatDTO = converter.toDTO(usuariEntitat);
		return Optional.ofNullable(usuariEntitatDTO);
	}

	@Override
	@PermitAll
	public Optional<UsuariEntitatDTO> findByUsuariId(Long usuariId) {
		return Optional.ofNullable(repository.findByUsuariId(usuariId));
	}

	@Override
	@PermitAll
	public List<UsuariEntitatDTO> getAll() {
		List<UsuariEntitatDTO> items = new ArrayList<UsuariEntitatDTO>();
		List<UsuariEntitat> llista = repository.getAll();
		for (UsuariEntitat a : llista) {
			items.add(converter.toDTO(a));
		}
		return items;
	}

	@Override
	@PermitAll
	public Pagina<UsuariEntitatDTO> findFiltered(int firstResult, int maxResult, Map<UsuariEntitatAtribut, Object> filter,
			List<Ordre<UsuariEntitatAtribut>> ordenacio) {

		List<UsuariEntitatDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
		long total = repository.countByFilter(filter);

		return new Pagina<>(items, total);
	}

}
