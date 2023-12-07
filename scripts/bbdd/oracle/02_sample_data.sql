
-- Crear Usuaris Aplicació per defecte
INSERT INTO itd_aplicacio (aplicacioid, usuari, clau, nom, codidir3, datacreacio, estat) VALUES (1, 'caib', 'interdoc', 'Usuari Aplicació Caib', 'A04019899', CURRENT_TIMESTAMP, 1);
INSERT INTO itd_aplicacio (aplicacioid, usuari, clau, nom, codidir3, datacreacio, estat) VALUES (2, 'fbit', 'interdoc', 'Usuari Aplicació Fundació Bit', 'A04019281', CURRENT_TIMESTAMP, 1);

-- Crear Entitats inicials
INSERT INTO itd_entitat (entitatid, nom, codidir3, datacreacio, actiu) VALUES (1, 'CAIB', 'A04003003', CURRENT_TIMESTAMP, 1);

-- Plugins necessaris
INSERT INTO itd_plugin (pluginid, nom, propietats, classe, datacreacio, actiu, entitatid, tipus) VALUES (1, 'Arxiu', '# Properties arxiu
es.caib.interdoc.plugins.arxiu.serieDocumental=S0001
es.caib.interdoc.plugins.arxiu.classificacio=organo1_PRO_123456789
es.caib.interdoc.plugins.arxiu.aplicacio=Tests
es.caib.interdoc.plugins.arxiu.tancarExpedient=true
es.caib.interdoc.plugins.arxiu.csv.validation.url=https://proves.caib.es/concsv/view.xhtml?hash=
es.caib.interdoc.plugins.arxiu.csv.url=https://proves.caib.es/concsv/rest/printable/uuid/', 'es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl', CURRENT_TIMESTAMP, 1, 1, 1);

INSERT INTO itd_plugin (pluginid, nom, propietats, classe, datacreacio, actiu, entitatid, tipus) VALUES (2, 'Firma', '# Properties Firma Simple
es.caib.interdoc.plugins.firma.profilecades=PROFILE_CADES
es.caib.interdoc.plugins.firma.profilepades=ENVIAFIB_PADES
es.caib.interdoc.plugins.firma.profilexades=PROFILE_XADES
es.caib.interdoc.plugins.firma.administracionId=
es.caib.interdoc.plugins.firma.nombre=
es.caib.interdoc.plugins.firma.email=
es.caib.interdoc.plugins.firma.languageUI=ca
es.caib.interdoc.plugins.firma.languageSign=ca
es.caib.interdoc.plugins.firma.localizacion=Palma
es.caib.interdoc.plugins.firma.motivo=Test
es.caib.interdoc.plugins.firma.tipodocumentalid=99
es.caib.interdoc.plugins.firma.signId=1
es.caib.interdoc.plugins.firma.alias=preprod-dgmad
# preprod-dgmad afirmades-firma
es.caib.interdoc.plugins.firma.applicationId=CAIBDEV2.REGWEB
es.caib.interdoc.plugins.firma.debug=true;
es.caib.interdoc.plugins.firma.profile=PROFILE_PADES', 'es.caib.interdoc.plugins.apifirmasimple.FirmaPluginImpl', CURRENT_TIMESTAMP, 1, 1, 2);


