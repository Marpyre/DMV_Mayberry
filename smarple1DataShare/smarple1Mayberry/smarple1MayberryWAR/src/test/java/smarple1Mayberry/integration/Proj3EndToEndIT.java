package smarple1Mayberry.integration;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.LocationDTO;
import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.ResidenceDTO;
import smarple1MayberryBO.Activity;
import smarple1MayberryBO.POI;

public class Proj3EndToEndIT extends ITBase {

	private static final Logger logger = LoggerFactory
			.getLogger(Proj3EndToEndIT.class);

	long personID;

	@SuppressWarnings("deprecation")
	@Test
	public void endToEnd() throws JAXBException, XMLStreamException, Exception {

		// 1. eMayberry initializes the EJB Timer

		// 2. admin1 resets all eDmv tables (using the DmvTestUtilEJB)
		// 3. admin1 populates the eDmv tables (using the DmvIngestor)
		// 4. admin1 resets the eMayberry tables (using the MayberryTestUtilEJB)
		// 5. admin1 populates the eMayberry tables (using the
		// MayberryTestUtilEJB) if necessary
		runAs(adminHandler);

		logger.info("*** resetAll ***");
		testUtilInterface.resetAll();
		poiInterface.resetDB();
		logger.info("*** resetAll complete ***");

		logger.info("*** populate ***");
		testUtilInterface.populate();
		logger.info("*** populate complete ***");

		// 6. andy adds a person of interest in eMayberry that matches one of
		// the original drivers or vehicle owners ingested.
		runAs(andyHandler);

		logger.info("*** create poi ***");
		POI poiChris = poiInterface.addPOI("Chris");
		logger.info("*** create poi complete ***");

		// 7. a new person of interest JMS message is published from eMayberry.
		// 8. andy adds a non-dangerous activity for the person of interest in
		// eMayberry.
		logger.info("*** Andy adds nondangerous activity to POI ***");
		poiInterface.addActivity(poiChris, Activity.Code.ParkingViolation);
		logger.info("*** Andy adds nondangerous activity to POI Complete ***");

		// 9. an updated person of interest JMS message is published from
		// eMayberry.

		// 10. andy searches for information on the person of interest in eDmv
		// using the eMayberry interface.
		logger.info("*** Andy searches DMV for info about POI ***");
		PersonDTO dmvPerson = poiInterface.getPerson(poiChris.getDmvID());
		logger.info("*** Andy searches DMV for info about POI Complete ***");

		// 11. kathy creates a new driver in eDmv.
		runAs(kathyHandler);

		PersonDTO newDriver = new PersonDTO();
		newDriver.setFirstName("Bradley");
		newDriver.setMiddleName("Wiggins");
		newDriver.setLastName("Chandry");

		// adds driver and saves new id
		newDriver.setId(personManager.addPerson(newDriver));

		// 12. an updated person JMS message is published from eDmv. This does
		// not trigger eMayberry to do anything since it does not match a person
		// of interest.

		// 13. kathy lists all drivers, including the new one created in eDmv

		int index = 0;

		List<PersonDTO> drivers = personManager.getPeople(index, index + 20);

		while (drivers.size() == index + 20) {
			index += 20;
			drivers.addAll(personManager.getPeople(index, index + 20));
		}

		logger.info("DriverList: ");
		logger.info(drivers.toString());

		// 14. kathy reports that a driver has moved in eDmv. Make this a driver
		// that matches a person of interest.

		LocationDTO l = new LocationDTO();
		l.setCity("Gotham");

		ResidenceDTO newResidence = new ResidenceDTO();
		newResidence.setPerson(dmvPerson); // the dmv person associated to the
											// poi Andy made earlier
		newResidence.setStartDate(new Date(12, 12, 12));
		newResidence.setLocation(l);

		personManager.changeResidence(newResidence);

		// 15. an updated person JMS message is published from eDmv.
		// 16. eMayberry sees the change, determines there is a match with one
		// of its persons of interest, and publishes its own updated person of
		// interest.

		// 17. andy adds a dangerous activity to the person of interest in
		// eMayberry.
		runAs(andyHandler);
		poiInterface.addActivity(poiChris, Activity.Code.Gang);

		// 18. an updated person of interest JMS message is published from
		// eMayberry. This time, with a property indicating the person is
		// dangerous.

		// 19. gomer sees the Java SE subscriber report activity for a dangerous
		// person of interest and accesses address information for the
		// individual in eMayberry.
		runAs(gomerHandler);

		poiInterface.getResidenceInfo(poiChris.getDmvID());

		// 20. gomer sees re-caps of the persons of interest being printed out
		// by the Java SE subscriber.

	}

	@Test
	public void checkAccessRestrictions() throws NamingException, IOException {
		logger.info("*** checkAccessRestrictions ***");

		runAs(sherriHandler);

		try {
			// should trigger EJB Access Error
			poiInterface.findPOIMatches("Charles", "Sam", 0);

			// this test should always fail if there isn't an access error
			// triggered by findPOIMatches();
			assert (true == false);
		} catch (Exception ex) {
			// EJB error causing me to be unable to catch EJBAccessException
			// specifically
			logger.info("*** Caught EJB Access Error! ***");
		}

		logger.info("*** checkAccessRestrictions Complete ***");
	}
}