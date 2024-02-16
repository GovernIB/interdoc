package es.caib.interdoc.plugins.arxiu;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.plugins.arxiu.api.ExpedientEstat;

/**
 * Classe per crear un scheduler per tancar asincronament tots els expedients que quedin en estat OBERT
 * @author jagarcia
 *
 */
@Startup
@Singleton
@ApplicationScoped
@Stateless(name="ArxiuSchedulerEJB")
public class ArxiuScheduler {

	protected final Logger log = Logger.getLogger(getClass());

	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;

	protected static final long TRANSACTION_TIMEOUT_IN_SEC = 180;
	
	@Inject
	protected ArxiuController arxiu;

	@PostConstruct
	public void init() {

		log.info("Programant ArxiuScheduler...");

		try {

			String schedulerHour = (arxiu != null && arxiu.getPlugin() != null) ? arxiu.getPlugin().getSchedulerExpression() : "15";
				
			ScheduleExpression expression = new ScheduleExpression();
			expression.dayOfWeek("Sun,Mon,Tue,Wed,Thu,Fri,Sat");
			expression.hour(schedulerHour);
			expression.minute(35);
			expression.second(0);
			expression.timezone("Europe/Madrid");

			if (Configuracio.isDesenvolupament()) {
				log.info("---------------------------  SCHEDULER STARTUP ------------------------ ");
				log.info("> > > SCHEDULER TIMER: " + expression.toString());
			}

		} catch (Exception e) {
			log.error("Error alhora d'inicialitzar el scheduler de tancar expedients: " + e.getMessage());
		}

	}
	
	@TransactionTimeout(value = TRANSACTION_TIMEOUT_IN_SEC)
	@Schedule(hour = "5", persistent = false)
    public void eliminarExpedientsOberts() {
		
		log.info("Comença eliminarExpedientsOberts()");
		long startTime = System.currentTimeMillis();
		
		try {
			
			List<InfoArxiuDTO> arxius = infoArxiuService.getExpedientsOberts(ExpedientEstat.OBERT.name());
			if (Configuracio.isDesenvolupament())
				arxius.forEach(x -> log.info("ExpId: " + x.getArxiuExpedientId() + " - docId: " + x.getArxiuDocumentId()));
					
			// Comprobar que no está tancat		
			// Tancar expedient
			
			log.info("tancant expedients");	
			
		}catch (Exception e) {
			log.error("Error tancant els expedients oberts desde ArxiuScheduler: " + e.getMessage());
		}
		
		long endTime = System.currentTimeMillis();
        log.info("Total time: " + (endTime - startTime));
        log.info("Acaba eliminarExpedientsOberts()");
		
	}
	

}
