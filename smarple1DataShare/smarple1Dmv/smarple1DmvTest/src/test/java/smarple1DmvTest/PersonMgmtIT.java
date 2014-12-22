package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;

public class PersonMgmtIT extends ITBase {
	private static final Logger logger = LoggerFactory
			.getLogger(PersonMgmtIT.class);

	@Before
	public void setUp() throws JAXBException, XMLStreamException, Exception {
		super.setUp();
		
		assertNotNull("jndi.name.personmgmt not supplied", personMgmtJNDI);

		logger.debug("getting jndi initial context");
		logger.debug("jndi={}", jndi.getEnvironment());
		logger.debug("jndi name:{}", personMgmtJNDI);
		logger.debug("personManager={}", personManager);
	}

	@Test
	public void testPing() throws NamingException, IOException {
		logger.info("*** testPing ***");
		runAs(knownHandler);
		personManager.ping();
	}

	@Test
	public void testGetPersonByName() throws NamingException, IOException {
		logger.info("*** testGetPersonByName ***");
		runAs(sysMayberryHandler);
		personManager.getPersonByName("Joss");
	}

	@Test
	public void testPersonLogic() throws NamingException, IOException {
		logger.info("*** person logic ***");

		runAs(kathyHandler);
		
		PersonDTO person = new PersonDTO();
		person.setFirstName("Bob");
		person.setMiddleName("Bob");
		person.setLastName("Bob");
		person.setNameSuffix("Mr.");

		long bobId = personManager.addPerson(person);

		PersonDTO personBob = personManager.getPerson(bobId);

		assert (personBob.getLastName().equals("Bob"));
	}
}