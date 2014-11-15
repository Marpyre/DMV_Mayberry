package smarple1dmv.bl;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import smarple1dmv.blimpl.DmvIngestorMgmt;
import smarple1dmv.bo.JPATestBase;
import smarple1dmv.dao.PersonDAO;
import smarple1dmv.dao.VehicleDAO;

public class DmvIngestorTest extends JPATestBase {
	private static Log log = LogFactory.getLog(DmvIngestorTest.class);

	private PersonDAO personDAO;
	private VehicleDAO vehicleDAO;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		personDAO = new PersonDAO();
		((PersonDAO) personDAO).setEntityManager(em);
		vehicleDAO = new VehicleDAO();
		((VehicleDAO) vehicleDAO).setEntityManager(em);	
	}

	@Test
	public void testIngestAll() throws Exception {
		/*log.info("*** testIngestAll ***");

		DmvIngestorMgmt ingestorMgr = new DmvIngestorMgmt();

		ingestorMgr.setPersonDAO(personDAO);
		ingestorMgr.setVehicleDAO(vehicleDAO);
		ingestorMgr.ingest();

		// some testing of ingest goes here...
		assertEquals("unexpected number of people", 1000,
				em.createQuery("select count(p) from Person p", Long.class)
						.getSingleResult().intValue());
		assertEquals(
				"unexpected number of vehicle registrations",
				1000,
				em.createQuery("select count(vr) from VehicleRegistration vr",
						Long.class).getSingleResult().intValue());
		assertEquals(
				"unexpected number of owners",
				1500,
				em.createQuery(
						"select count(o) from VehicleRegistration vr JOIN vr.owners as o",
						Long.class).getSingleResult().intValue());
		assertEquals(
				"unexpected number of distinct owners",
				772,
				em.createQuery(
						"select count(distinct o) from VehicleRegistration vr JOIN vr.owners as o",
						Long.class).getSingleResult().intValue());
						*/
	}	
}