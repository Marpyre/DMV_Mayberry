package smarple1dmv.bl;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.sql.Date;

import org.junit.Test;

import smarple1dmv.blimpl.DmvIngestor;
import smarple1dmv.bo.JPATestBase;
import smarple1dmv.bo.Location;
import smarple1dmv.bo.Person;
import smarple1dmv.bo.VehicleRegistration;
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
		Person newDriver = new Person();
		newDriver.setFirstName("Sam");
		newDriver.setLastName("Gnarley");
		
		personDAO.createPerson(newDriver);
		
		Location location = new Location();
		location.setCity("Washington, D.C.");
		
		personDAO.addNewResidence(newDriver, location, new Date(2012, 12, 12));
		
		// 4. moveResidence (using DriverMgmtImpl)
		
		Location location2 = new Location();
		location2.setCity("Westminster");
		
		personDAO.changeResidence(newDriver, location2, new Date(2014, 12, 12));
		
		// 5. addRegistration (using VehicleMgmtImpl)
		
		VehicleRegistration registration = new VehicleRegistration(newDriver);
		registration.setMake("Dodge");
		registration.setModel("Ram");
		
		vehicleDAO.createRegistration(registration);
		
		// 6. getRegistrationByMakeModel (using VehicleMgmtImpl)
		vehicleDAO.getRegistrationByMakeModel("Dodge", "Ram");
		
		// 7. getPeopleByName (using PeopleMgmtImpl)
		personDAO.getPeopleByName("Bob");
		
		// 8. getPerson (using PeopleMgmtImpl)
		Person firstPerson = personDAO.getPeople(0, 1).get(0);
		
		// 9. getRegistrationByOwner (using VehicleMgmtImpl)
		firstPerson.getRegistrations();
		
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
