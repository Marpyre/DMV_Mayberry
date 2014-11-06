package smarple1dmv.bo;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import smarple1dmv.bo.Location.STATE;

public class LocationTest extends JPATestBase {
	private static Log log = LogFactory.getLog(LocationTest.class);

	@Test
	public void testTemplate() {
		log.info("Location Test Begin");
		
		Location location = new Location();
		location.setStreetName("Charles Street");
		location.setCity("Baltimore");
		location.setState(STATE.MD);
		em.persist(location);

		logLocation();
	}

	public void logLocation() {
		Query query = em.createQuery("select l from Location as l");
		for (Object o : query.getResultList()) {
			log.info("Location:" + o);
		}
	}
}