package smarple1DmvTest;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvEJB.IReservationRemote;
import static org.junit.Assert.*;

public class ReservationIT extends ITBase {
	private static final Logger logger = LoggerFactory.getLogger(ReservationIT.class);

    private InitialContext jndi;
    
    private static final String reservationJNDI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/ReservationEJB!"
					+ IReservationRemote.class.getName());
    
    private IReservationRemote reservationist;

    @Before
    public void setUp() throws NamingException {
    	assertNotNull("jndi.name.reservation not supplied", reservationJNDI);
    	
    	logger.debug("getting jndi initial context");
        jndi=new InitialContext();
        logger.debug("jndi={}", jndi.getEnvironment());
        jndi.lookup("jms");
        
        logger.debug("jndi name:{}", reservationJNDI);
        
        reservationist = (IReservationRemote) jndi.lookup(reservationJNDI);
        logger.debug("reservationist={}", reservationist);
    }

    @Test
    public void testPing() throws NamingException {
        logger.info("*** testPing ***");
        
        reservationist.ping();
    }
}