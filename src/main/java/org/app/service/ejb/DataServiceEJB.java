package org.app.service.ejb;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.EntityBase;

@Path("service")
@Stateless
@LocalBean
public class DataServiceEJB extends EntityRepositoryBase<EntityBase> implements DataService{
	private static Logger logger = Logger.getLogger(DataServiceEJB.class.getName());
	// nu uita!!
	private EntityRepository<EntityBase> entityRepository;
	
    @PostConstruct
	public void init(){
		logger.info("Porneste :");		
	}	

	public DataServiceEJB() {
		super();
		logger.info("INIT DEF CONSTRUCTOR : " + this.em);		
	}

	
	/********************************************************************/
	@GET
	@Produces("text/html")
	public String sayRest() {
		return "Resting(test OK!!)... ";
	}	
	/********************************************************************/
}
