package smarple1dmv.bl;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import smarple1dmv.blimpl.DmvIngestor;
import smarple1dmv.bo.JPATestBase;
import smarple1dmv.bo.Person;
import smarple1dmv.dao.PersonDAO;
import smarple1dmv.dao.VehicleDAO;

public class DmvBlTest extends JPATestBase {

	PersonDAO personDAO;
	VehicleDAO vehicleDAO;
	
	/**
	 * This will run the test that checks the end to end scenario works. The
	 * steps for the scenario are commented out and will be implemented and
	 * tested accordingly later.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		// 1. reset (using DmvTestUtilImpl)
		super.cleanup();

		// 2. populate (using DmvIngestor)
		personDAO = new PersonDAO();
		((PersonDAO)personDAO).setEntityManager(em);
		vehicleDAO = new VehicleDAO();
        ((VehicleDAO)vehicleDAO).setEntityManager(em);
		
		populateDB();
				
		// 3. addDriver (using DriverMgmtImpl)
		
		
		// 4. moveResidence (using DriverMgmtImpl)
		
		
		// 5. addRegistration (using VehicleMgmtImpl)
		
		
		// 6. getRegistrationByMakeModel (using VehicleMgmtImpl)
		
		
		// 7. getPeopleByName (using PeopleMgmtImpl)
		personDAO.getPeopleByName("Bob");
		
		// 8. getPerson (using PeopleMgmtImpl)
		
		
		// 9. getRegistrationByOwner (using VehicleMgmtImpl)
		
	}
	
	public void populateDB() throws Exception{
		
		em.getTransaction().begin();
		
		String fileName = "xml/dmv-all.xml";
		InputStream is = Thread.currentThread()
		                       .getContextClassLoader()
		                       .getResourceAsStream(fileName);
		assertNotNull(fileName + " not found", is);
		
		DmvIngestor ingestor = new DmvIngestor();
		ingestor.setPersonDAO(personDAO);
		ingestor.setVehicleDAO(vehicleDAO);
		ingestor.setInputStream(is);
		ingestor.ingest();
		em.getTransaction().commit();
	}

}
