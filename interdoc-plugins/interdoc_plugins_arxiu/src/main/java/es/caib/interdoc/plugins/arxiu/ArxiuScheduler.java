package es.caib.interdoc.plugins.arxiu;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.apache.log4j.Logger;

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
public class ArxiuScheduler {

	protected final Logger log = Logger.getLogger(getClass());

	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;

	@Inject
	protected ArxiuController arxiu;

	@Resource
	public TimerService timerService;

	public void setTimerService(TimerService timerService) {
		this.timerService = timerService;
	}

	@PostConstruct
	public void init() {

		log.info("Programant ArxiuScheduler...");

		try {

			ScheduleExpression expression = new ScheduleExpression();
			expression.dayOfWeek("Sun,Mon,Tue,Wed,Thu,Fri,Sat");
			expression.hour(1); // TODO parametritzat per plugin property
			expression.minute(0);
			expression.second(0);
			expression.timezone("Europe/Madrid");

			if (Configuracio.isDesenvolupament()) {
				log.info("---------------------------  SCHEDULER STARTUP ------------------------ ");
				log.info("> > > SCHEDULER TIMER: " + expression.toString());
			}

			TimerConfig config = new TimerConfig();
			config.setInfo("Tancar expedients oberts d'arxiu");
			config.setPersistent(false);

			timerService.createCalendarTimer(expression, config);

		} catch (Exception e) {
			log.error("Error alhora d'inicialitzar el scheduler de tancar expedients: " + e.getMessage());
		}

	}
	
	@Timeout
    public void execute(Timer timer) {
		
		try {
			// TODO
			
			List<InfoArxiuDTO> arxius = infoArxiuService.getExpedientsOberts(ExpedientEstat.OBERT.name());
			if (Configuracio.isDesenvolupament())
				arxius.forEach(x -> log.info("ExpId: " + x.getArxiuExpedientID() + " - docId: " + x.getArxiuDocumentID()));
					
			// Comprobar que no está tancat
					
			// Tancar expedient
			
			log.info("tancant expedients");
					
					
			
		}catch (Exception e) {
			log.error("Error tancant els expedients oberts desde ArxiuScheduler: " + e.getMessage());
		}
		
		
	}

}
