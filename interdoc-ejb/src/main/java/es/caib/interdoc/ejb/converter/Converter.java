package es.caib.interdoc.ejb.converter;


/**
 * Interfície per representar un conversor entre un Entity i un DTO
 *
 * @param <E> classe del Entity
 * @param <U> classe del Usuari
 * @param <D> classe del DTO
 * @author areus
 */
public interface Converter<E, D> {

    D toDTO(E entity);

    E toEntity(D dto);

    void updateFromDTO(E entity, D dto);

}
