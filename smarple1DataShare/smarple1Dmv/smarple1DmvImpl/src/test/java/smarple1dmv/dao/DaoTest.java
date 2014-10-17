package smarple1dmv.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DaoTest {
	
	private static Log log = LogFactory.getLog(DaoTest.class);
	private static final String PERSISTENCE_UNIT = "entityMgrEx";
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	/*@Before
	public void setup() throws Exception{
		em = emf.createEntityManager();
		//cleanup();
	}
	
	@BeforeClass
	public static void setUpClass(){
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}*/
	
	@Test
	public void testTemplate(){
		log.info("TestTemplate");
	}
	
	/*@After
	public void tearDown() throws Exception{
		try{
			em.getTransaction().begin();
			em.flush();
			//logAutos();
			em.getTransaction().commit();
			em.close();
		}
		catch(Exception ex){
			throw ex;
		}
	}
	
	@AfterClass
	public static void tearDownClass(){
		emf.close();
	}*/

	public void logAutos(){
		Query query = em.createQuery("select a from Auto as a");
		for (Object o : query.getResultList()){
			//log.info("EM_AUTO: " + o);
		}
	}
	
}
