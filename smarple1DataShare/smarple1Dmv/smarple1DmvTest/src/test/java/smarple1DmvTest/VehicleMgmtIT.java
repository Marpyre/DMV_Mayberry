package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleMgmtIT extends ITBase {
	private static final Logger logger = LoggerFactory.getLogger(VehicleMgmtIT.class);

    @Before
    public void setUp() throws JAXBException, XMLStreamException, Exception {
    	super.setUp();
    	
    	assertNotNull("jndi.name.vehiclemgmt not supplied", vehicleMgmtJNDI);
    	
    	logger.debug("getting jndi initial context");
        logger.debug("jndi={}", jndi.getEnvironment());
        logger.debug("jndi name:{}", vehicleMgmtJNDI);
        logger.debug("vehicleManager={}", vehicleManager);
        
        runAs(sherriHandler);
    }

    @Test
    public void testPing() throws NamingException {
        logger.info("*** testPing ***");
        vehicleManager.ping();
    }
}