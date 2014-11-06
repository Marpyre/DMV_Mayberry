package smarple1dmv.bo;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

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
            emf.close(); emf=null;
        }
	}

	@SuppressWarnings("unchecked")
	protected void cleanup() throws Exception {
		em.getTransaction().begin();
		
		Query query = em.createQuery("select p from Photo p");
		for (Photo p : (List<Photo>) query
				.getResultList()) {
			em.remove(p);
		}
		
		em.flush();
		
		query = em.createQuery("select pd from PhysicalDetails pd");
		for (PhysicalDetails pd : (List<PhysicalDetails>) query
				.getResultList()) {
			em.remove(pd);
		}
		
		em.flush();
	
		query = em.createQuery("select r from Residence r");
		for (Residence r : (List<Residence>) query
				.getResultList()) {
			em.remove(r);
		}
		
		em.flush();
		
		query = em.createQuery("select l from Location l");
		for (Location l : (List<Location>) query
				.getResultList()) {
			em.remove(l);
		}
		
		em.flush();
		
		query = em.createQuery("select vr from VehicleRegistration vr");
		for (VehicleRegistration reg : (List<VehicleRegistration>) query
				.getResultList()) {
			reg.getOwners().clear();
			em.remove(reg);
		}
		
		query = em.createQuery("select p from Person p");
		for (Person person : (List<Person>) query.getResultList()) {
			em.remove(person);
		}
		
		em.flush();
		
		em.getTransaction().commit();
	}
}