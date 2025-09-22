package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.service.model.UsuariAtribut;
import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.persistence.model.Usuari_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis dels usuaris
 * de la capa de persistència.
 */
public class UsuariCriteriaHelper extends AbstractCriteriaHelper<Usuari, UsuariAtribut> {

    public UsuariCriteriaHelper(CriteriaBuilder builder, Root<Usuari> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(UsuariAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case usuariId:
                return root.get(Usuari_.usuariId);
            case username:
                return root.get(Usuari_.username);
            case idiomaId:
                return root.get(Usuari_.idiomaId);
            case nom:
                return root.get(Usuari_.nom);
            case llinatge1:
                return root.get(Usuari_.llinatge1);
            case llinatge2:
                return root.get(Usuari_.llinatge2);
            case email:
                return root.get(Usuari_.email);
            case nif:
                return root.get(Usuari_.nif);
            
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
