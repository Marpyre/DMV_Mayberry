package smarple1dmv.bl;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

import smarple1dmv.blimpl.DmvIngestor;
import smarple1dmv.bo.JPATestBase;
import smarple1dmv.dao.PersonDAO;
import smarple1dmv.dao.VehicleDAO;

public class DmvBlTest extends JPATestBase {

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
		
		populateDB();
				
		// 3. addDriver (using DriverMgmtImpl)
		// 4. moveResidence (using DriverMgmtImpl)
		// 5. addRegistration (using VehicleMgmtImpl)
		// 6. getRegistrationByMakeModel (using VehicleMgmtImpl)
		// 7. getPeopleByName (using PeopleMgmtImpl)
		// 8. getPerson (using PeopleMgmtImpl)
		// 9. getRegistrationByOwner (using VehicleMgmtImpl)
	}
	
	public void populateDB() throws Exception{
		PersonDAO personDAO = new PersonDAO();
		((PersonDAO)personDAO).setEntityManager(em);
        VehicleDAO vehicleDAO = new VehicleDAO();
        ((VehicleDAO)vehicleDAO).setEntityManager(em);
		
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
