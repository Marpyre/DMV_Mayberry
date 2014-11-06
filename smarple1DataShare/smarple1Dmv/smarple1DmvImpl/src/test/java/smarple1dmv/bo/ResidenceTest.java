package smarple1dmv.bo;

import java.sql.Date;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ResidenceTest extends JPATestBase {
	private static Log log = LogFactory.getLog(ResidenceTest.class);


	@SuppressWarnings("deprecation")
	@Test
	public void testTemplate() {
			log.info("Residence Test Begin");
			
			Person person = new Person();
			person.setFirstName("Charles");
			person.setMiddleName("C");
			person.setLastName("Haney");
			em.persist(person);
			
			Location location = new Location();
			location.setCity("Washington, D.C.");
			em.persist(location);
			
			Residence residence = new Residence(person, location);
			residence.setStartDate(new Date(2012, 12, 12));
			residence.setEndDate(new Date(2014, 12, 12));
			em.persist(residence);
			
			logResidenceDetails();
	}

	public void logResidenceDetails() {
		Query query = em.createQuery("select r from Residence as r");
		for (Object o : query.getResultList()) {
			log.info("Residence:" + o);
		}
	}
}