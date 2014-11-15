package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvEJB.IVehicleMgmtRemote;

public class VehicleMgmtIT extends ITBase {
	private static final Logger logger = LoggerFactory.getLogger(VehicleMgmtIT.class);

    private InitialContext jndi;
    
    private static final String vehicleMgmtJNDI = System.getProperty("jndi.name.vehiclemgmt");
    
    private IVehicleMgmtRemote vehicleManager;

    @Before
    public void setUp() throws NamingException {
    	assertNotNull("jndi.name.vehiclemgmt not supplied", vehicleMgmtJNDI);
    	
    	logger.debug("getting jndi initial context");
        jndi=new InitialContext();
        logger.debug("jndi={}", jndi.getEnvironment());
        jndi.lookup("jms");
        
        logger.debug("jndi name:{}", vehicleMgmtJNDI);
        
        vehicleManager = (IVehicleMgmtRemote) jndi.lookup(vehicleMgmtJNDI);
        logger.debug("vehicleManager={}", vehicleManager);
    }

    @Test
    public void testPing() throws NamingException {
        logger.info("*** testPing ***");
        
        vehicleManager.ping();
    }
}