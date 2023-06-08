package es.caib.interdoc.api.externa;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author anadal
 *
 */
@OpenAPIDefinition(
        info = @Info(
                title = "API REST EXTERNA de interdoc",
                description = "Conjunt de Serveis REST de interdoc per ser accedits des de l'exterior", 
                version = "1.0.0",
                license = @License(
                        name = "License Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "Departament de Govern Digital a la Fundació Bit",
                        email = "otae@fundaciobit.org",
                        url = "http://otae.fundaciobit.org")

        ),
        servers = {
                //@Server(url = "../../interdocapi/externa"),
                @Server(url = "http://localhost:8080/interdocapi/externa"),
                @Server(url = "https://dev.caib.es/interdocapi/externa"),
                @Server(url = "https://proves.caib.es/interdocapi/externa"),
                @Server(url = "https://se.caib.es/interdocapi/externa"),
                @Server(url = "https://www.caib.es/interdocapi/externa") },
        externalDocs = @ExternalDocumentation(
                description = "Java Client (GovernIB Github)",
                url = "https://github.com/GovernIB/interdoc/tree/interdoc-1.0/interdoc-api-externa-client")

)
@ApplicationPath("/")
public class JAXRSConfiguration extends Application {

	protected static final Logger log = LoggerFactory.getLogger(JAXRSConfiguration.class);

    /**
     * Les aplicacions JAX-RS necessiten un constructor buid.
     */
    public JAXRSConfiguration() {
    }

    /**
     * Podem introduir tasques a realitzar per la inicialització de l'API REST.
     */
    @PostConstruct
    private void init() {
        log.info("Iniciant API REST EXTERNA de interdoc");
    }

}
