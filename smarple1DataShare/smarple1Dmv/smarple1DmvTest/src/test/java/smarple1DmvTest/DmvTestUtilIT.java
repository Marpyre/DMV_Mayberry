package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvEJB.IDmvTestUtilRemote;

public class DmvTestUtilIT extends ITBase {
	private static final Logger logger = LoggerFactory
			.getLogger(DmvTestUtilIT.class);

	private InitialContext jndi;

	private static final String dmvTestUtilJDNI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/DmvTestUtilEJB!"
					+ IDmvTestUtilRemote.class.getName());

	private IDmvTestUtilRemote testUtilInterface;

	@Before
	public void setUp() throws NamingException {
		assertNotNull("jndi.name.dmvtestutil not supplied", dmvTestUtilJDNI);

		logger.debug("getting jndi initial context");
		jndi = new InitialContext();
		logger.debug("jndi={}", jndi.getEnvironment());
		jndi.lookup("jms");

		logger.debug("jndi name:{}", dmvTestUtilJDNI);

		testUtilInterface = (IDmvTestUtilRemote) jndi.lookup(dmvTestUtilJDNI);
		logger.debug("testUtilInterface={}", testUtilInterface);
	}

	@Test
	public void testPing() throws NamingException {
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
}