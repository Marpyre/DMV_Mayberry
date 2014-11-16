package smarple1DmvEJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;

@Stateless
public class DriverMgmtEJB implements IDriverMgmtLocal, IDriverMgmtRemote {
	private static Logger logger = LoggerFactory
			.getLogger(ReservationEJB.class);

    private static final String PERSISTENCE_UNIT = "bo";
    
    @PersistenceContext(unitName=PERSISTENCE_UNIT)
    private EntityManager em;
	
	@Override
	public void ping() {
		logger.debug("ping called");
	}

	@Override
	public PersonDTO getDriverByID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDTO getDriverByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}