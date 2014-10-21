package smarple1dmv.bo;

import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class PhysicalDetailsTest extends JPATestBase {
	private static Log log = LogFactory.getLog(PhysicalDetailsTest.class);

	@Test
	public void testTemplate() {
			log.info("Physical Details Test Begin");
			
			Person person = new Person();
			person.setFirstName("Sammy");
			person.setMiddleName("T");
			person.setLastName("Johnson");
			em.persist(person);
			
			PhysicalDetails pd = new PhysicalDetails(person);
			pd.setHeight(68);
			em.persist(pd);
			em.flush();
			
			em.clear();
			logPhysicalDetails();
	}
	
	public void logPhysicalDetails() {
		Query query = em.createQuery("select p from PhysicalDetails as p");
		for (Object o : query.getResultList()) {
			log.info("physical details:" + o);
		}
	}
}