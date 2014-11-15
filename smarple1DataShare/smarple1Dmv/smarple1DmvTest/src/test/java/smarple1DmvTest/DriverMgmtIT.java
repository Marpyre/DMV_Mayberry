package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvEJB.IDriverMgmtRemote;

public class DriverMgmtIT extends ITBase {
	private static final Logger logger = LoggerFactory.getLogger(DriverMgmtIT.class);

    private InitialContext jndi;
    
    private static final String driverMgmtJNDI = System.getProperty("jndi.name.drivermgmt");
    
    private IDriverMgmtRemote driverManager;

    @Before
    public void setUp() throws NamingException {
    	assertNotNull("jndi.name.drivermgmt not supplied", driverMgmtJNDI);
    	
    	logger.debug("getting jndi initial context");
        jndi=new InitialContext();
        logger.debug("jndi={}", jndi.getEnvironment());
        jndi.lookup("jms");
        
        logger.debug("jndi name:{}", driverMgmtJNDI);
        
        driverManager = (IDriverMgmtRemote) jndi.lookup(driverMgmtJNDI);
        logger.debug("driverManager={}", driverManager);
    }

    @Test
    public void testPing() throws NamingException {
        logger.info("*** testPing ***");
        
        driverManager.ping();
    }
}