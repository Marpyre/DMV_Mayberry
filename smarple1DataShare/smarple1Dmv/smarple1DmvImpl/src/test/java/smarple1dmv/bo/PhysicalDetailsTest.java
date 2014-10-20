package smarple1dmv.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class PhysicalDetailsTest {
	private static Log log = LogFactory.getLog(PhysicalDetailsTest.class);
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
			logPhysicalDetails();
			em.getTransaction().commit();
			em.close();
	}

	public void logPhysicalDetails() {
		Query query = em.createQuery("select p from PhysicalDetails as p");
		for (Object o : query.getResultList()) {
			log.info("physical details:" + o);
		}
	}
}