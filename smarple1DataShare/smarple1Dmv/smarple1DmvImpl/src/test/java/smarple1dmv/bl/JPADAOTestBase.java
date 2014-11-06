package smarple1dmv.bl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import smarple1dmv.bo.Location;
import smarple1dmv.bo.Person;
import smarple1dmv.bo.Photo;
import smarple1dmv.bo.PhysicalDetails;
import smarple1dmv.bo.Residence;
import smarple1dmv.bo.VehicleRegistration;

/**
 * This class handles all generic JPA setUp and tearDown actions for 
 * the DMV tests 
 */
public class JPADAOTestBase {
    //this code assumes all the JDBC properties were placed in 
    //META-INF/persistence.xml when the file was copied from src to the 
    //target tree
    private static EntityManagerFactory emf;
    protected EntityManager em;

    @BeforeClass
    public static void setUpClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("bo");
    }

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        if (em != null && em.getTransaction().isActive()) {
            EntityTransaction tx = em.getTransaction();
            if (tx.getRollbackOnly()) { tx.rollback(); }
            else                      { tx.commit(); }
            em.close(); em=null;
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (emf != null) {
            emf.close(); emf=null;
        }
    }
    
    @SuppressWarnings("unchecked")
	protected void cleanup() throws Exception {
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
	}
}