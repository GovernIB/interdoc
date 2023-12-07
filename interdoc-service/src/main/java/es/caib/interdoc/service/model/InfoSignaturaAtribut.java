package es.caib.interdoc.service.model;

public enum InfoSignaturaAtribut implements Atribut {
    id,
    signOperation,
    signType,
    signAlgorithm,
    signMode,
    signaturesTableLocation,
    timestampIncluded,
    policyIncluded,
    eniTipoFirma,
    eniPerfilFirma,
    eniRolFirma,
    eniSignerName,
    eniSignerAdministrationId,
    checkAdministrationIdOfSigner,
    checkDocumentModifications,
    checkValidationSignature,
    signDate
}
