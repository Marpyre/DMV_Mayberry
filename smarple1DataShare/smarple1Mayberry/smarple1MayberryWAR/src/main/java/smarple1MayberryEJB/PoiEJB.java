package smarple1MayberryEJB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.ResidenceDTO;
import smarple1DmvEJB.IPersonMgmtRemote;
import smarple1MayberryBO.Activity;
import smarple1MayberryBO.POI;
import smarple1MayberryJMS.Publisher;
import smarple1MayberryBL.BLImpl;
import smarple1dmv.bo.Location;
import smarple1dmv.bo.Person;
import smarple1dmv.bo.Photo;
import smarple1dmv.bo.PhysicalDetails;
import smarple1dmv.bo.Residence;
import smarple1dmv.bo.VehicleRegistration;

@Stateless
@RunAs("dmv-federated")
public class PoiEJB implements POIRemote {
	private static Logger logger = LoggerFactory.getLogger(PoiEJB.class);

	@EJB(lookup = "ejb:smarple1DmvEAR/smarple1DmvEJB/PersonMgmtEJB!smarple1DmvEJB.IPersonMgmtRemote")
	IPersonMgmtRemote personEJB;

	private static final String PERSISTENCE_UNIT = "mayberryBo";

	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connFactory;

	// @Resource(lookup = "java:/topic/ejava/projects/datashare/dmv", type =
	// Topic.class)
	@Resource(lookup = "java:/topic/ejava/projects/datashare/mayberry-poi", type = Topic.class)
	private Destination mayberryTopic;

	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public PersonDTO getPerson(long dmvId) {
		logger.debug("Getting Person from Dmv");
		PersonDTO personDmv = personEJB.getPerson(dmvId);
		logger.debug("Getting Person from Dmv Complete");
		return personDmv;
	}

	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public List<ResidenceDTO> getResidenceInfo(long dmvID) throws Exception {
		logger.debug("Getting Residence Info from Dmv");
		List<ResidenceDTO> residenceDTOs = personEJB.getResidences(dmvID);
		logger.debug("Getting Residence Info from Dmv Complete");
		return residenceDTOs;
	}

	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public POI addPOI(String name) {
		logger.debug("Adding POI");

		PersonDTO personDmv = personEJB.getPersonByName(name);
		POI poiFromDmv = new POI();

		poiFromDmv.setFirstName(personDmv.getFirstName() + "");
		poiFromDmv.setMiddleName(personDmv.getMiddleName() + "");
		poiFromDmv.setLastName(personDmv.getLastName() + "");

		poiFromDmv.setDmvID(personDmv.getId());

		em.persist(poiFromDmv);

		try {
			sendPoiChangeMessage(BLImpl.isDangerous(poiFromDmv));
		} catch (Exception e) {
			logger.debug("Error Sending POI Change Message: from POI EJB Add POI Method.");
		}

		logger.debug("Adding POI Complete");

		return poiFromDmv;
	}

	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public POI addPOI(long id) {
		logger.debug("Adding POI");

		PersonDTO personDmv = personEJB.getPerson(id);
		POI poiFromDmv = new POI();

		poiFromDmv.setFirstName(personDmv.getFirstName() + "");
		poiFromDmv.setMiddleName(personDmv.getMiddleName() + "");
		poiFromDmv.setLastName(personDmv.getLastName() + "");

		em.persist(poiFromDmv);

		try {
			sendPoiChangeMessage(BLImpl.isDangerous(poiFromDmv));
		} catch (Exception e) {
			logger.debug("Error Sending POI Change Message: from POI EJB Add POI Method.");
		}

		logger.debug("Adding POI Complete");

		return poiFromDmv;
	}

	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public void addActivity(POI poi, Activity.Code type) {

		Activity act1 = new Activity(poi);
		act1.setType(Activity.Code.ParkingViolation);
		em.persist(act1);
		em.flush();
		poi.addActivity(act1);
		em.merge(poi);

		try {
			sendPoiChangeMessage(BLImpl.isDangerous(poi));
		} catch (Exception e) {
			logger.debug("Error Sending POI Change Message: from POI EJB Add Activity Method.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public List<POI> findPOIMatches(String firstName, String lastName, int index) {

		List<POI> poiList = (List<POI>) em
				.createQuery(
						"select p from POI p where p.firstName like :personFirstName and p.lastName like :personLastName")
				.setParameter("personFirstName", firstName)
				.setParameter("personLastName", lastName).setFirstResult(index)
				.getResultList();
		return poiList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	public List<POI> getAllPOI(int index, int count) {

		List<POI> poiList = (List<POI>) em.createQuery("select p from POI p")
				.setFirstResult(index).setMaxResults(count).getResultList();
		return poiList;
	}

	@Override
	// @RolesAllowed({ "mayberry-tactical", "mayberry-analytical" })
	@PermitAll
	public long countPOI() {

		long count = (long) em.createQuery("SELECT Count(*) FROM POI")
				.getSingleResult();
		return count;
	}

	private void sendPoiChangeMessage(boolean isDangerous) throws Exception {
		Publisher mayberryPublisher = new Publisher("sysMayberry", "password1!");
		mayberryPublisher.setConnFactory(connFactory);
		mayberryPublisher.setDestination(mayberryTopic);

		mayberryPublisher.setDescription("POI Change Message");
		mayberryPublisher.setIsDangerous(isDangerous);

		mayberryPublisher.sendMessage();
	}

	@Schedule(second = "*/10", minute = "*", hour = "*", dayOfMonth = "*", month = "*", year = "*", persistent = true)
	public void execute(Timer timer) {
		logger.info("timer fired:" + timer);
		try {
			long numPOI = countPOI();
			int index = 0;
			int count = 20;
			List<POI> POIs = new ArrayList<POI>();

			while (index < numPOI) {
				POIs.addAll(getAllPOI(index, count));
				index = index + count;
			}

			Publisher mayberryPublisher = new Publisher("sysMayberry",
					"password1!");
			mayberryPublisher.setConnFactory(connFactory);
			mayberryPublisher.setDestination(mayberryTopic);
			mayberryPublisher.setIsPoiList(true);

			StringBuilder sb = new StringBuilder();

			for (POI poi : POIs) {
				sb.append("[" + poi.getId() + ":" + poi.getFirstName() + ","
						+ poi.getLastName() + "]");
			}

			mayberryPublisher.setDescription(sb.toString());

			mayberryPublisher.sendMessage();

		} catch (Exception ex) {
			logger.error("error schedule printing POIs", ex);
		}
	}

	@SuppressWarnings("unchecked")
	@RolesAllowed("mayberry-admin")
	public void resetDB() {
		Query query = em.createQuery("select p from POI p");

		for (POI p : (List<POI>) query.getResultList()) {
			p.removeActivities();
		}

		em.flush();

		query = em.createQuery("select a from Activity a");
		for (Activity a : (List<Activity>) query.getResultList()) {
			em.remove(a);
		}

		em.flush();

		for (POI p : (List<POI>) query.getResultList()) {
			em.remove(p);
		}

		em.flush();
	}
}
