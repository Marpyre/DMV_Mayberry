package smarple1DmvEJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1dmv.blimpl.DmvIngestorMgmt;
import smarple1dmv.blimpl.DmvUtil;
import smarple1dmv.dao.PersonDAO;
import smarple1dmv.dao.VehicleDAO;

@Stateless
public class DmvTestUtilEJB implements IDmvTestUtilLocal, IDmvTestUtilRemote {
    private static Logger log = LoggerFactory.getLogger(DmvTestUtilEJB.class);

    private static final String PERSISTENCE_UNIT = "bo";
    
    @PersistenceContext(unitName=PERSISTENCE_UNIT)
    private EntityManager em;
    
    DmvUtil dmvUtil;
    
    DmvIngestorMgmt ingesterManager;
    
    PersonDAO personDAO;
    VehicleDAO vehicleDAO;
    
	@Override
	public void ping() {
		log.debug("ping called");
	}

	@Override
	public void resetAll() {
		log.debug("reset all answer");
		
		dmvUtil = new DmvUtil(em);
		
		dmvUtil.resetDB();	
		
		log.debug("reset all complete");
	}

	@Override
	public void populate() throws JAXBException, XMLStreamException, Exception {
		log.debug("ingest all answer");
		
		ingesterManager = new DmvIngestorMgmt();
		
		personDAO = new PersonDAO();
		((PersonDAO)personDAO).setEntityManager(em);
		vehicleDAO = new VehicleDAO();
        ((VehicleDAO)vehicleDAO).setEntityManager(em);
        
        ingesterManager.setPersonDAO(personDAO);
        ingesterManager.setVehicleDAO(vehicleDAO);
		
		ingesterManager.ingest();
		
		log.debug("reset all complete");
	}
}
