package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.UsuariConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.UsuariRepository;
import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.UsuariAtribut;
import es.caib.interdoc.service.model.UsuariDTO;

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
@Local(UsuariServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UsuariServiceFacadeBean implements UsuariServiceFacade {

	@Inject
	private UsuariRepository repository;

	@Inject
	private UsuariConverter converter;

	@Override
	@RolesAllowed(Constants.ITD_ADMIN)
	public Long create(UsuariDTO dto) {
		Usuari usuari = converter.toEntity(dto);
		repository.create(usuari);
		return usuari.getUsuariId();
	}

	@Override
	@RolesAllowed(Constants.ITD_ADMIN)
	public void update(UsuariDTO dto) throws RecursNoTrobatException {
		Usuari usuari = repository.getReference(dto.getUsuariId());
		converter.updateFromDTO(usuari, dto);
	}

	@Override
	@RolesAllowed(Constants.ITD_ADMIN)
	public void delete(Long usuariId) throws RecursNoTrobatException {
		Usuari usuari = repository.getReference(usuariId);
		repository.delete(usuari);
	}

	@Override
	@RolesAllowed({ Constants.ITD_USER, Constants.ITD_ADMIN })
	public Optional<UsuariDTO> findById(Long usuariId) {
		Usuari usuari = repository.findById(usuariId);
		UsuariDTO usuariDTO = converter.toDTO(usuari);
		return Optional.ofNullable(usuariDTO);
	}

	@Override
	@PermitAll
	public Optional<UsuariDTO> findByUsername(String username) {
		return Optional.ofNullable(repository.findByUsername(username));
	}

	@Override
	@PermitAll
	public List<UsuariDTO> getAll() {
		List<UsuariDTO> items = new ArrayList<UsuariDTO>();
		List<Usuari> llista = repository.getAll();
		for (Usuari a : llista) {
			items.add(converter.toDTO(a));
		}
		return items;
	}

	@Override
	@PermitAll
	public Pagina<UsuariDTO> findFiltered(int firstResult, int maxResult, Map<UsuariAtribut, Object> filter,
			List<Ordre<UsuariAtribut>> ordenacio) {

		List<UsuariDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
		long total = repository.countByFilter(filter);

		return new Pagina<>(items, total);
	}

}
