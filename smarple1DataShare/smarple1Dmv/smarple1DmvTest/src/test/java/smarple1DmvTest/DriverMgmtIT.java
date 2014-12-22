package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverMgmtIT extends ITBase {
	private static final Logger logger = LoggerFactory.getLogger(DriverMgmtIT.class);

    @Before
    public void setUp() throws JAXBException, XMLStreamException, Exception {
    	super.setUp();
    	
    	assertNotNull("jndi.name.drivermgmt not supplied", driverMgmtJNDI);
    	
    	logger.debug("getting jndi initial context");
        logger.debug("jndi={}", jndi.getEnvironment());
        logger.debug("jndi name:{}", driverMgmtJNDI);
        logger.debug("driverManager={}", driverManager);
        
        runAs(knownHandler);
    }

    @Test
    public void testPing() throws NamingException {
        logger.info("*** testPing ***");
        driverManager.ping();
    }
}