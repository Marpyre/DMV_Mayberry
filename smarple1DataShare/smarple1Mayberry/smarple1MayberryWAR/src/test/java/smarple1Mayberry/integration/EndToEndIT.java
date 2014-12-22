package smarple1Mayberry.integration;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.LocationDTO;
import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.ResidenceDTO;
import smarple1DmvDTO.VehicleRegistrationDTO;
import smarple1MayberryBO.Activity;
import smarple1MayberryBO.POI;

public class EndToEndIT extends ITBase {
	private static final Logger logger = LoggerFactory
			.getLogger(EndToEndIT.class);

	long personID;

	@Test
	public void endToEnd() throws JAXBException, XMLStreamException, Exception {

		runAs(adminHandler);

		// 1. Reset all eDmv and eMayberry tables (using the DmvTestUtilEJB and
		// MayberryTestUtilEJB)
		logger.info("*** resetAll ***");
		testUtilInterface.resetAll();
		poiInterface.resetDB();
		logger.info("*** resetAll complete ***");

		// 2. Populate the eDmv tables (using the DmvIngestor)
		logger.info("*** populate ***");
		testUtilInterface.populate();
		logger.info("*** populate complete ***");

		runAs(sysRaleighHandler);

		// 3. Create a new Driver in eDmv (using the DriverMgmtEJB)
		logger.info("*** create driver ***");
		createDriver();
		logger.info("*** create driver complete ***");

		// 4. List Drivers, including the newly added Driver in eDmv (using the
		// DriverMgmtEJB)
		logger.info("*** list drivers ***");
		listDrivers();
		logger.info("*** list drivers complete ***");

		// 5. Move Residence for a Driver in eDmv (using DriverMgmtEJB)
		logger.info("*** move residence ***");
		changeResidence();
		logger.info("*** move residence complete ***");

		// 6. Add a Vehicle Registration in eDmv (using VehicleMgmtEJB)
		logger.info("*** add vehicle registration ***");
		addVehicleRegistration();
		logger.info("*** add vehicle registration complete ***");

		// 7. List Vehicle Registrations, including the newly created Vehicle
		// Registration, in eDmv (using DriverMgmtEJB)
		logger.info("*** list vehicle registrations ***");
		listVehicleRegistrations();
		// logger.info("Vehicle Registrations: " + registrations.toString());
		logger.info("*** list vehicle registrations complete ***");

		runAs(andyHandler);

		// 8. Create a Person of Interest in eMayberry that matches someone in
		// eDmv (using PoiMgmtEJB)
		logger.info("*** Create POI ***");
		POI poi = poiInterface.addPOI(personID);
		logger.info("*** Create POI complete ***");

		// 9. Add an Activity for the Person of Interest in eMayberry (using
		// PoiMgmtEJB)
		logger.info("*** Add Activity ***");
		poiInterface.addActivity(poi, Activity.Code.Gang);
		logger.info("*** Add Activity complete ***");

		// 10. Search for the Person of Interest in eDmv (using PersonMgmtEJB).
		// Optionally associate the identity of the correct information in
		// eMayberry (using PoiMgmtEJB)
		// 11. Add a dangerous Activity for the Person of Interest in eMayberry
		// (using PoiMgmtEJB)

	}

	public void createDriver() {
		PersonDTO newPerson = new PersonDTO();
		newPerson.setFirstName("Bradley");
		newPerson.setMiddleName("Wiggins");
		newPerson.setLastName("Chandry");
		newPerson.setId(personManager.addPerson(newPerson));
		logger.info("ID" + newPerson.getId());

		personID = newPerson.getId();
	}

	public void listDrivers() {
		int index = 0;

		List<PersonDTO> drivers = personManager.getPeople(index, index + 20);

		while (drivers.size() == index + 20) {
			index += 20;
			drivers.addAll(personManager.getPeople(index, index + 20));
		}

		logger.info("DriverList: ");
		logger.info(drivers.toString());
	}

	public void changeResidence() throws Exception {

		PersonDTO p = new PersonDTO();
		p.setFirstName("Vincent");
		p.setLastName("Kompany");
		p.setId(personManager.addPerson(p));

		LocationDTO l = new LocationDTO();
		l.setCity("Alexandria");

		Date startDate = new Date(12, 12, 12);

		ResidenceDTO newResidence = new ResidenceDTO();
		newResidence.setPerson(p);
		newResidence.setLocation(l);
		newResidence.setStartDate(startDate);

		personManager.changeResidence(newResidence);
	}

	public void addVehicleRegistration() {

		VehicleRegistrationDTO vrDTO = new VehicleRegistrationDTO();
		vrDTO.setMake("Dodge");
		vrDTO.setModel("Caravan");

		vehicleManager.addVehicleRegistration(vrDTO);
	}

	public void listVehicleRegistrations() {

		int index = 0;

		List<VehicleRegistrationDTO> vehicles = vehicleManager
				.getRegistrations(index, index + 20);

		while (vehicles.size() == index + 20) {
			index += 20;
			vehicles.addAll(vehicleManager.getRegistrations(index, index + 20));
		}

		logger.info(vehicles.toString());
	}
}