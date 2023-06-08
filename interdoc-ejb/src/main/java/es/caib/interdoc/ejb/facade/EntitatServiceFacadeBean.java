package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.EntitatConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.EntitatRepository;
import es.caib.interdoc.persistence.model.Entitat;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.model.EntitatAtribut;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

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
@Local(EntitatServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EntitatServiceFacadeBean implements EntitatServiceFacade {

    @Inject
    private EntitatRepository repository;

    @Inject
    private EntitatConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(EntitatDTO dto) {
        Entitat entitat = converter.toEntity(dto);
        repository.create(entitat);
        return entitat.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(EntitatDTO dto) throws RecursNoTrobatException {
    	Entitat entitat = repository.getReference(dto.getId());
        converter.updateFromDTO(entitat, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
    	Entitat entitat = repository.getReference(id);
        repository.delete(entitat);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<EntitatDTO> findById(Long id) {
    	Entitat entitat = repository.findById(id);
        EntitatDTO entitatDTO = converter.toDTO(entitat);
        return Optional.ofNullable(entitatDTO);
    }

    @Override
    @PermitAll
    public List<EntitatDTO> getAll() {
        List<EntitatDTO> items = new ArrayList<EntitatDTO>();
        List<Entitat> llista = repository.getAll();
        for (Entitat a : llista ) {
            items.add(converter.toDTO(a));
        }
        return items;
    }

    @Override
    @PermitAll
    public Pagina<EntitatDTO> findFiltered(int firstResult, int maxResult, Map<EntitatAtribut, Object> filter,
                                             List<Ordre<EntitatAtribut>> ordenacio) {

        List<EntitatDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
    
}
