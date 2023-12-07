package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.AccesConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.AccesRepository;
import es.caib.interdoc.persistence.model.Acces;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.model.AccesAtribut;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació dels casos d'ús de manteniment de aplicacions.
 * És responsabilitat d'aquesta capa definir el limit de les transaccions i la seguretat.
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma
 * els errors JPA amb les excepcions de servei com la {@link RecursNoTrobatException}
 *
 * @author jagarcia
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(AccesServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccesServiceFacadeBean implements AccesServiceFacade {

    @Inject
    private AccesRepository repository;

    @Inject
    private AccesConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(AccesDTO dto) {
        Acces acces = converter.toEntity(dto);
        repository.create(acces);
        return acces.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        Acces acces = repository.getReference(id);
        repository.delete(acces);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<AccesDTO> findById(Long id) {
        Acces acces = repository.findById(id);
        AccesDTO accesDTO = converter.toDTO(acces);
        return Optional.ofNullable(accesDTO);
    }

    @Override
    @PermitAll
    public Pagina<AccesDTO> findFiltered(int firstResult, int maxResult, Map<AccesAtribut, Object> filter,
                                             List<Ordre<AccesAtribut>> ordenacio) {

        List<AccesDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }

	@Override
	@PermitAll
	public Optional<List<AccesDTO>> findByRefenciaId(Long referenciaId) {
		return Optional.ofNullable(repository.findByRefenciaId(referenciaId)); 
	}
}
