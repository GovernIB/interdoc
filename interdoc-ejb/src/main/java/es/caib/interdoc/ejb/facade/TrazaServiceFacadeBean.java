package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.TrazaConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.TrazaRepository;
import es.caib.interdoc.persistence.model.Traza;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.TrazaServiceFacade;
import es.caib.interdoc.service.model.TrazaAtribut;
import es.caib.interdoc.service.model.TrazaDTO;
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
@Local(TrazaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TrazaServiceFacadeBean implements TrazaServiceFacade {

    @Inject
    private TrazaRepository repository;

    @Inject
    private TrazaConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(TrazaDTO dto) {
        Traza traza = converter.toEntity(dto);
        repository.create(traza);
        return traza.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(TrazaDTO dto) throws RecursNoTrobatException {
        Traza traza = repository.getReference(dto.getId());
        converter.updateFromDTO(traza, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        Traza traza = repository.getReference(id);
        repository.delete(traza);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<TrazaDTO> findById(Long id) {
        Traza traza = repository.findById(id);
        TrazaDTO trazaDTO = converter.toDTO(traza);
        return Optional.ofNullable(trazaDTO);
    }

    @Override
    @PermitAll
    public Pagina<TrazaDTO> findFiltered(int firstResult, int maxResult, Map<TrazaAtribut, Object> filter,
                                             List<Ordre<TrazaAtribut>> ordenacio) {

        List<TrazaDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<TrazaDTO>(items, total);
    }
}
