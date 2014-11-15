package smarple1dmv.bo;

import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class PersonTest extends JPATestBase {
	private static Log log = LogFactory.getLog(PersonTest.class);

	@Test
	public void testTemplate() {
			log.info("Person Test Begin");
			
			Person person = new Person();
			person.setFirstName("Just");
			person.setMiddleName("N");
			person.setLastName("Case");
			
			em.persist(person);
			
			logPeople();
	}

	public void logPeople() {
		Query query = em.createQuery("select p from Person as p");
		for (Object o : query.getResultList()) {
			log.info("person:" + o);
		}
	}
}