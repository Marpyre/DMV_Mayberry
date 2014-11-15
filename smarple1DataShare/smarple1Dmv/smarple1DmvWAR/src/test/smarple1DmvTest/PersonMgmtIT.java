package smarple1DmvTest;

import static org.junit.Assert.assertNotNull;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvEJB.IPersonMgmtRemote;

public class PersonMgmtIT extends ITBase {
	private static final Logger logger = LoggerFactory.getLogger(PersonMgmtIT.class);

    private InitialContext jndi;
    
    private static final String personMgmtJNDI = System.getProperty("jndi.name.personmgmt");
    
    private IPersonMgmtRemote personManager;

    @Before
    public void setUp() throws NamingException {
    	assertNotNull("jndi.name.personmgmt not supplied", personMgmtJNDI);
    	
    	logger.debug("getting jndi initial context");
        jndi=new InitialContext();
        logger.debug("jndi={}", jndi.getEnvironment());
        jndi.lookup("jms");
        
        logger.debug("jndi name:{}", personMgmtJNDI);
        
        personManager = (IPersonMgmtRemote) jndi.lookup(personMgmtJNDI);
        logger.debug("personManager={}", personManager);
    }

    @Test
    public void testPing() throws NamingException {
        logger.info("*** testPing ***");
        
        personManager.ping();
    }
    
    @Test
    public void testGetPerson() throws NamingException{
        logger.info("*** testGetPerson ***");
        
        personManager.getPerson();
    }
    
    @Test
    public void testGetPersonByName() throws NamingException {
        logger.info("*** testGetPersonByName ***");
        
        personManager.getPersonByName("Joss");
    }
    
    @Test
    public void testPersonLogic() throws NamingException {
        logger.info("*** person logic ***");
        
        PersonDTO person = new PersonDTO();
        person.setFirstName("Bob");
        person.setMiddleName("Bob");
        person.setLastName("Bob");
        person.setNameSuffix("Mr.");
        
        personManager.addPerson(person);
        
        PersonDTO personBob = personManager.getPersonByName("Bob");
        
        assert(personBob.getLastName().equals("Bob"));
    }
}