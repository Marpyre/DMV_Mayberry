package smarple1mayberry.bl;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

import smarple1mayberry.bo.Activity;
import smarple1mayberry.bo.POI;

public class JPATestBase {
	private static Log log = LogFactory.getLog(JPATestBase.class);
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
		cleanup();
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
		Query query = em.createQuery("select a from Activity a");
		for (Activity act : (List<Activity>) query.getResultList()) {
			em.remove(act);
		}

		query = em.createQuery("select p from POI p");
		for (POI poi : (List<POI>) query.getResultList()) {
			em.remove(poi);
		}

		em.getTransaction().begin();
		em.getTransaction().commit();
	}
}