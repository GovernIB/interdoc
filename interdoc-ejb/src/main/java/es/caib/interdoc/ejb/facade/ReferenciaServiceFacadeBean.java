package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.ejb.converter.ReferenciaConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.ReferenciaRepository;
import es.caib.interdoc.persistence.model.Referencia;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.ReferenciaAtribut;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import javax.annotation.security.PermitAll;
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
@Local(ReferenciaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ReferenciaServiceFacadeBean implements ReferenciaServiceFacade {

    @Inject
    private ReferenciaRepository repository;

    @Inject
    private ReferenciaConverter converter;

    @Override
    @PermitAll
    public Long create(ReferenciaDTO dto) {
        Referencia referencia = converter.toEntity(dto);
        repository.create(referencia);
        return referencia.getId();
    }

    @Override
    public void update(ReferenciaDTO dto) throws RecursNoTrobatException {
        Referencia referencia = repository.getReference(dto.getId());
        converter.updateFromDTO(referencia, dto);
    }

    @Override
    public void delete(Long id) throws RecursNoTrobatException {
        Referencia referencia = repository.getReference(id);
        repository.delete(referencia);
    }

    @Override
    public Optional<ReferenciaDTO> findById(Long id) throws RecursNoTrobatException {
        Referencia referencia = repository.findById(id);
        ReferenciaDTO referenciaDTO = converter.toDTO(referencia);
        return Optional.ofNullable(referenciaDTO);
    }
    
    @Override
    @PermitAll
    public Optional<ReferenciaDTO> findByReferencia(String referencia) throws RecursNoTrobatException{
    	List<ReferenciaDTO> referenciaDto = repository.findByReferencia(referencia);
    	return Optional.ofNullable(referenciaDto.get(0));
    }
    
    @Override
    @PermitAll
    public Optional<ReferenciaDTO> findByUUID(String uuid) throws RecursNoTrobatException{
    	List<ReferenciaDTO> referenciaDto = repository.findByUUID(uuid);
    	return Optional.ofNullable(referenciaDto.get(0));
    }
    
    @Override
    @PermitAll
    public Optional<ReferenciaDTO> findByCSV(String csv) throws RecursNoTrobatException{
    	List<ReferenciaDTO> referenciaDto = repository.findByCSV(csv);
    	return Optional.ofNullable(referenciaDto.get(0));
    }

    @Override
    @PermitAll
    public Pagina<ReferenciaDTO> findFiltered(int firstResult, int maxResult, Map<ReferenciaAtribut, Object> filter,
                                             List<Ordre<ReferenciaAtribut>> ordenacio) {

        List<ReferenciaDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
}
