package smarple1dmv.bo;

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

import smarple1dmv.blimpl.DmvUtil;

public class JPATestBase {
	private static Log log = LogFactory.getLog(JPATestBase.class);
	private static final String PERSISTENCE_UNIT = "bo";
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
		cleanup();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("TearDown() started, em=" + em);
		cleanup();
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
            emf.close(); emf=null;
        }
	}

	protected void cleanup() throws Exception {
		DmvUtil dmvUtil = new DmvUtil(em);
		dmvUtil.resetDB();
	}
}