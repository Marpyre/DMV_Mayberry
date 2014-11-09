package smarple1mayberry.bl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import smarple1mayberry.bo.Activity;
import smarple1mayberry.bo.POI;

public class MayberryBlTest {

	private static Log log = LogFactory.getLog(MayberryBlTest.class);
	private static final String PERSISTENCE_UNIT = "mayberryBo";
	private static EntityManagerFactory emf;
	protected EntityManager em;

	@BeforeClass
	public static void setUpClass() {
		log.debug("Creating entity manager factory");
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	@Before
	public void setUp() throws Exception {
		log.debug("Creating entity manager");
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}

	/**
	 * This will run the test that checks the end to end scenario works.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {

		log.info("Mayberry End to End Test Started!");

		// 1. reset (using MayberryTestUtilImpl)
		cleanup();

		// 2. addPoi (PoiMgmtImpl)
		addPoi();

		// 3. addActivity (PoiMgmtImpl)
		addActivity();

		// 4. getPoi (PoiMgmtImpl)
		POI poi1 = getPoi(0);

		// 5. isDangerous (PoiMgmtImpl)
		isDangerous(poi1);
		
		//proof isDangerous check works...
		POI poi2 = getPoi(1);
		isDangerous(poi2);
		
		log.info("Mayberry End to End Test Complete!");
	}

	private void addPoi() {
		log.info("Adding POI.");

		POI poi1 = new POI();
		poi1.setFirstName("Chucky");
		em.persist(poi1);
		em.flush();

		log.info("Added POI: " + poi1.getFirstName() + ".");
	}

	private void addActivity() {
		log.info("Adding POI.");

		POI poi2 = new POI();
		poi2.setFirstName("Rick");
		em.persist(poi2);
		em.flush();

		log.info("Added POI: " + poi2.getFirstName() + ".");

		log.info("Adding Activity.");
		Activity act1 = new Activity(poi2);
		act1.setType(Activity.Code.Gang);
		em.persist(act1);
		em.flush();
		poi2.addActivity(act1);

		log.info("Added Activity: " + act1.getType());
	}

	private POI getPoi(int index) {

		log.info("Getting POI.");

		POI poiToReturn = (POI) em.createQuery("select p from POI p")
				.setFirstResult(index).getResultList().get(0);

		log.info("Got POI: " + poiToReturn.getFirstName());

		return poiToReturn;

	}

	private boolean isDangerous(POI poi) {

		log.info("Checking if POI dangerous.");

		Collection<Activity> activities = poi.getActivities();

		log.info("POI has " + activities.size() + " activities.");
		for (Activity a : activities) {
			if (a.getType().equals(Activity.Code.Gang)) {
				log.info("POI very dangerous.");
				return true;
			}
		}
		log.info("POI not dangerous.  POI has " + activities.size()
				+ " activities.");
		return false;
	}

	@After
	public void tearDown() throws Exception {
		log.debug("TearDown() started, em=" + em);
		if (em != null && em.getTransaction().isActive()) {
			EntityTransaction tx = em.getTransaction();
			if (tx.getRollbackOnly()) {
				tx.rollback();
			} else {
				tx.commit();
			}
			em.close();
			em = null;
		}
	}

	@AfterClass
	public static void tearDownClass() {
		log.debug("Closing entity manager factory");
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}

	@SuppressWarnings("unchecked")
	protected void cleanup() throws Exception {
		log.info("Clean up started.");

		// Clear activities
		Query query = em.createQuery("select a from Activity a");
		for (Activity act : (List<Activity>) query.getResultList()) {
			em.remove(act);
		}

		// Clear POIs
		query = em.createQuery("select p from POI p");
		for (POI poi : (List<POI>) query.getResultList()) {
			em.remove(poi);
		}

		em.flush();

		log.info("Clean up complete.");
	}
}
