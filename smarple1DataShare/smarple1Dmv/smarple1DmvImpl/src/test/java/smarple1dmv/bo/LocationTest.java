package smarple1dmv.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class LocationTest {
	private static Log log = LogFactory.getLog(LocationTest.class);
	private static final String PERSISTENCE_UNIT = "bo";
	private static EntityManagerFactory emf;
	private EntityManager em;

	@Test
	public void testTemplate() {
			log.info("testTemplate");
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.flush();
			logLocation();
			em.getTransaction().commit();
			em.close();
	}

	public void logLocation() {
		Query query = em.createQuery("select l from Location as l");
		for (Object o : query.getResultList()) {
			log.info("Location:" + o);
		}
	}
}