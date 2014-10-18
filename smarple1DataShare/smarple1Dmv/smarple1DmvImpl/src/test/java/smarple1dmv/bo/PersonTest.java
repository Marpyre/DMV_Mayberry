package smarple1dmv.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonTest {
	private static Log log = LogFactory.getLog(PersonTest.class);
	private static final String PERSISTENCE_UNIT = "bo";
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void setUpClass() {
		log.debug("creating entity manager factory");
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	@Before
	public void setUp() throws Exception {
		log.debug("creating entity manager");
		em = emf.createEntityManager();
		cleanup();
	}

	@Test
	public void testTemplate() {
		log.info("testTemplate");
	}

	@After
	public void tearDown() throws Exception {
		try {
			log.debug("tearDown() started, em=" + em);
			em.getTransaction().begin();
			em.flush();
			logPeople();
			em.getTransaction().commit();
			em.close();
			log.debug("tearDown() complete, em=" + em);
		} catch (Exception ex) {
			log.fatal("tearDown failed", ex);
			throw ex;
		}
	}
		
		public void logPeople() {
	        Query query = em.createQuery("select p from Smarple1dmv_person as p");
	        for (Object o: query.getResultList()) {
	            log.info("person:" + o);
	        }        
	    }
	
	public void cleanup() {
        em.getTransaction().begin();
        Query query = em.createNativeQuery("delete from Smarple1dmv_person");
        int rows = query.executeUpdate();
        em.getTransaction().commit();
        log.info("removed " + rows + " rows");
    }
}