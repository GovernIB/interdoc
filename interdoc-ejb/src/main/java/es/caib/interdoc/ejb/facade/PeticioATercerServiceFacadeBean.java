package es.caib.interdoc.ejb.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.PeticioATercerConverter;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.PeticioATercerRepository;
import es.caib.interdoc.persistence.model.PeticioATercer;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.PeticioATercerServiceFacade;
import es.caib.interdoc.service.model.*;

/**
 * Implementació dels casos d'ús de manteniment de peticions a tercers
 *
 * @author jagarcia
 */
@Logged
@Stateless
@Local(PeticioATercerServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PeticioATercerServiceFacadeBean implements PeticioATercerServiceFacade {

    @Inject
    private PeticioATercerRepository repository;

    @Inject
    private PeticioATercerConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(PeticioATercerDTO dto) {
        PeticioATercer peticio = converter.toEntity(dto);
        repository.create(peticio);
        return peticio.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(PeticioATercerDTO dto) throws RecursNoTrobatException {
        PeticioATercer peticio = repository.getReference(dto.getId());
        converter.updateFromDTO(peticio, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void updateState(Long id, EstatPublicacio state) {
        PeticioATercer peticio = repository.getReference(id);
        // TODO peticio == NULL
        if (peticio != null) {
            peticio.setEstat(state);
            repository.update(peticio);
        }
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<PeticioATercerDTO> findById(Long id) {
        PeticioATercer peticio = repository.findById(id);
        PeticioATercerDTO dto = converter.toDTO(peticio);
        return Optional.ofNullable(dto);
    }

    @Override
    @PermitAll
    public List<PeticioATercerDTO> getAll(Integer filterByState) {
        List<PeticioATercerDTO> items = new ArrayList<PeticioATercerDTO>();
        items = repository.getAll(filterByState);
        return items;
    }

    @Override
    @PermitAll
    public Pagina<PeticioATercerDTO> findFiltered(int firstResult, int maxResult, Map<PeticioATercerAtribut, Object> filter,
                                                  List<Ordre<PeticioATercerAtribut>> ordenacio) {
        // TODO
        List<PeticioATercerDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);
        return new Pagina<PeticioATercerDTO>(items, total);
    }


}
