package smarple1dmv.bo;

import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class PhotoTest extends JPATestBase {
	private static Log log = LogFactory.getLog(PhotoTest.class);

	@Test
	public void testTemplate() {
		log.info("Photo Test Begin");
		
		Person person = new Person();
		person.setFirstName("Josie");
		person.setMiddleName("J");
		person.setLastName("Jennings");
		em.persist(person);
		
		em.getTransaction().begin();
		em.flush();
		
		PhysicalDetails pd = new PhysicalDetails(person);
		pd.setHeight(68);
		em.persist(pd);
		
		em.flush();
		
		Photo photo = new Photo(pd);
		photo.setImage(new byte[10*1000]);
		em.persist(photo);

		logPhoto();
	}

	public void logPhoto() {
		Query query = em.createQuery("select p from Photo as p");
		for (Object o : query.getResultList()) {
			log.info("photo:" + o);
		}
	}
}