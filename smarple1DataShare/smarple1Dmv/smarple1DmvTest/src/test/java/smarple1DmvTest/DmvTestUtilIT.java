package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.ejb.EJBAccessException;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DmvTestUtilIT extends ITBase {
	private static final Logger logger = LoggerFactory
			.getLogger(DmvTestUtilIT.class);

	@Before
	public void setUp() throws JAXBException, XMLStreamException, Exception {
		super.setUp();

		assertNotNull("jndi.name.dmvtestutil not supplied", dmvTestUtilJDNI);

		logger.debug("getting jndi initial context");
		logger.debug("jndi={}", jndi.getEnvironment());
		logger.debug("jndi name:{}", dmvTestUtilJDNI);
		logger.debug("testUtilInterface={}", testUtilInterface);

		runAs(adminHandler);
	}

	@Test
	public void testPing() throws NamingException, IOException {
		logger.info("*** testPing ***");
		testUtilInterface.ping();
	}

	@Test
	public void resetAll() {
		logger.info("*** resetAll ***");
		testUtilInterface.resetAll();
		logger.info("*** resetAll complete ***");
	}

	@Test
	public void populate() throws JAXBException, XMLStreamException, Exception {
		logger.info("*** populate ***");
		testUtilInterface.populate();
		logger.info("*** populate complete ***");
	}

	@Test
	public void checkAccessRestrictions() throws NamingException, IOException {
		logger.info("*** checkAccessRestrictions ***");

		runAs(kathyHandler);
		try {
			testUtilInterface.resetAll(); // should trigger EJB Access Error

			// should always fail if no access error for resetAll();
			assert (true == false);
		} catch (EJBAccessException ex) {
			logger.info("*** Caught EJB Access Error! ***");
		}

		logger.info("*** checkAccessRestrictions Complete ***");
	}
}