package smarple1dmv.bo;

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
        em.getTransaction().begin();
    }

    @After
    public void tearDown() throws Exception {
        try {
            log.debug("TearDown() started, em=" + em);
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
                em.getTransaction().commit();            
            } else if (!em.getTransaction().getRollbackOnly()) {
                em.getTransaction().commit();                           
            } else {
                em.getTransaction().rollback();
            }
            em.close();
            log.debug("TearDown() complete, em=" + em);
        }
        catch (Exception ex) {
            log.fatal("TearDown failed", ex);
            throw ex;
        }
     }
    
    @AfterClass
    public static void tearDownClass() {
        log.debug("Closing entity manager factory");
        if (emf!=null) { emf.close(); }
    }
   
    public void cleanup() {
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    
    protected EntityManager createEm() {
        return emf.createEntityManager();
    }
}