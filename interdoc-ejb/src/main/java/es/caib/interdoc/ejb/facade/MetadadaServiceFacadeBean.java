package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.MetadadaConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.MetadadaRepository;
import es.caib.interdoc.persistence.model.Metadada;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.MetadadaServiceFacade;
import es.caib.interdoc.service.model.MetadadaAtribut;
import es.caib.interdoc.service.model.MetadadaDTO;
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
@Local(MetadadaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MetadadaServiceFacadeBean implements MetadadaServiceFacade {

    @Inject
    private MetadadaRepository repository;

    @Inject
    private MetadadaConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(MetadadaDTO dto) {
        Metadada metadada = converter.toEntity(dto);
        repository.create(metadada);
        return metadada.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(MetadadaDTO dto) throws RecursNoTrobatException {
        Metadada metadada = repository.getReference(dto.getId());
        converter.updateFromDTO(metadada, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        Metadada metadada = repository.getReference(id);
        repository.delete(metadada);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<MetadadaDTO> findById(Long id) {
        Metadada metadada = repository.findById(id);
        MetadadaDTO metadadaDTO = converter.toDTO(metadada);
        return Optional.ofNullable(metadadaDTO);
    }

    @Override
    @PermitAll
    public Pagina<MetadadaDTO> findFiltered(int firstResult, int maxResult, Map<MetadadaAtribut, Object> filter,
                                             List<Ordre<MetadadaAtribut>> ordenacio) {

        List<MetadadaDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<MetadadaDTO>(items, total);
    }
}
