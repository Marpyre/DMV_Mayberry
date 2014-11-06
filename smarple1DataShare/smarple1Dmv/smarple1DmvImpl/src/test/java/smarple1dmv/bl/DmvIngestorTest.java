package smarple1dmv.bl;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import smarple1dmv.blimpl.DmvIngestor;
import smarple1dmv.dao.PersonDAO;
import smarple1dmv.dao.VehicleDAO;

public class DmvIngestorTest extends JPADAOTestBase {
	private static Log log = LogFactory.getLog(DmvIngestorTest.class);

	private PersonDAO personDAO;
	private VehicleDAO vehicleDAO;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		personDAO = new PersonDAO();
		((PersonDAO)personDAO).setEntityManager(em);
        vehicleDAO = new VehicleDAO();
        ((VehicleDAO)vehicleDAO).setEntityManager(em);
		
		em.getTransaction().begin();
		
		super.cleanup();
	}
	
	@Test
	public void testIngestAll() throws Exception {
		
		log.info("*** testIngestAll ***");
		
		String fileName = "xml/dmv-all.xml";
		InputStream is = Thread.currentThread()
		                       .getContextClassLoader()
		                       .getResourceAsStream(fileName);
		assertNotNull(fileName + " not found", is);
		
		DmvIngestor ingestor = new DmvIngestor();
		ingestor.setPersonDAO(personDAO);
		ingestor.setVehicleDAO(vehicleDAO);
		ingestor.setInputStream(is);
		ingestor.ingest();
		em.getTransaction().commit();
		
		//some testing of ingest goes here...		
		assertEquals("unexpected number of people", 1000,
				em.createQuery("select count(p) from Person p", Long.class)
				.getSingleResult().intValue());		
		assertEquals("unexpected number of vehicle registrations", 1000,
				em.createQuery("select count(vr) from VehicleRegistration vr", Long.class)
				.getSingleResult().intValue());
		assertEquals("unexpected number of owners", 1500,
				em.createQuery("select count(o) from VehicleRegistration vr JOIN vr.owners as o", Long.class)
				.getSingleResult().intValue());		
		assertEquals("unexpected number of distinct owners", 772,
				em.createQuery("select count(distinct o) from VehicleRegistration vr JOIN vr.owners as o", Long.class)
				.getSingleResult().intValue());
	}
}