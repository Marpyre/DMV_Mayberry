package smarple1dmv.bo;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class VehicleRegistrationTest extends JPATestBase {
	private static Log log = LogFactory.getLog(VehicleRegistrationTest.class);

	@Test
	public void testTemplate() {
			log.info("Vehicle Registration Test");
			
			Person person1 = new Person();
			person1.setFirstName("Susan");
			person1.setMiddleName("B");
			person1.setLastName("Anthony");
			em.persist(person1);
			
			Person person2 = new Person();
			person2.setFirstName("Franklin");
			person2.setMiddleName("D");
			person2.setLastName("Roosevelt");
			em.persist(person2);
			
			VehicleRegistration vr1 = new VehicleRegistration(person1);
			VehicleRegistration vr2 = new VehicleRegistration();
			vr1.addOwner(person2);
			vr2.addOwner(person2);
			
			em.persist(vr1);
			em.persist(vr2);
			
			log.info("Vehicle Registration Person1:" + person1.getRegistrations());
			log.info("Vehicle Registration Person2:" + person2.getRegistrations());
			
			log.info("Owners for Vehicle 1:" + vr1.getOwners());
			log.info("Owners for Vehicle 2:" + vr2.getOwners());
		
			em.flush();
			em.clear();
			logVehicleRegistration();
	}

	public void logVehicleRegistration() {
		Query query = em.createQuery("select v from VehicleRegistration as v");
		for (Object o : query.getResultList()) {
			log.info("Vehicle Registration:" + o);
		}
	}
}