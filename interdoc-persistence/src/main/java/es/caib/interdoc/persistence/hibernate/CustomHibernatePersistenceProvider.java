package es.caib.interdoc.persistence.hibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;

public class CustomHibernatePersistenceProvider extends HibernatePersistenceProvider {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, @SuppressWarnings("rawtypes") Map properties) {
		
		Map<String,String> projecteProperties = new HashMap();
		
		if(!properties.isEmpty()) {
			projecteProperties.putAll(properties);
		}
		
		Properties fitxerProperties = Configuracio.getSystemAndFileProperties();
		if(fitxerProperties != null) {
			fitxerProperties.forEach((k,v) -> {
               String kStr = k.toString();
	        	if (kStr.startsWith("es.caib.interdoc.hibernate")) {
	        		projecteProperties.put(kStr.replace("es.caib.interdoc.",""), v.toString());
	        	} else if(kStr.startsWith("hibernate.")) {
	        		projecteProperties.put(kStr, v.toString());
	        	}
	        });
		}
		if (Configuracio.isDesenvolupament()) {
			projecteProperties.forEach((k,v) -> {
	        	log.info("HibernateProperties: " + k + ", Value : " + v);
	        });
		}
		return super.createContainerEntityManagerFactory(info, projecteProperties);
	}

}
