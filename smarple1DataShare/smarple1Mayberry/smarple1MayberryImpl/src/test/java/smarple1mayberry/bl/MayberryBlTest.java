package smarple1mayberry.bl;

import org.junit.Test;

import smarple1mayberry.bo.Activity;
import smarple1mayberry.bo.POI;

public class MayberryBlTest extends JPATestBase {

	/**
	 * This will run the test that checks the end to end scenario works.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		// 1. reset (using MayberryTestUtilImpl)
		super.cleanup();

		// 2. addPoi (PoiMgmtImpl)
		addPoi();

		// 3. addActivity (PoiMgmtImpl)
		addActivity();

		// 4. getPoi (PoiMgmtImpl)
		// 5. isDangerous (PoiMgmtImpl)

	}

	private void addPoi() {
		POI poi1 = new POI();
		poi1.setFirstName("Chucky");
		em.persist(poi1);
	}

	private void addActivity() {
		POI poi2 = new POI();
		poi2.setFirstName("Rick");
		em.persist(poi2);
		em.getTransaction().begin();
		em.flush();

		// Getting constraint error because poi_id not
		// allowed to be null? I thought if I flushed the parent element to the
		// database, an id would be generated.
		
		// Activity act1 = new Activity(poi2);
		// em.persist(act1); 
	}
}
