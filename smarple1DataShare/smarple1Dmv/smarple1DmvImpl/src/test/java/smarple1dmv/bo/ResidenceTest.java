package smarple1dmv.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class ResidenceTest {
	private static Log log = LogFactory.getLog(ResidenceTest.class);
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
			logResidenceDetails();
			em.getTransaction().commit();
			em.close();
	}

	public void logResidenceDetails() {
		Query query = em.createQuery("select r from Residence as r");
		for (Object o : query.getResultList()) {
			log.info("Residence:" + o);
		}
	}
}