package smarple1DmvEJB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.LocationDTO;
import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.ResidenceDTO;
import smarple1DmvJMS.Publisher;
import smarple1dmv.bo.Location;
import smarple1dmv.bo.Person;
import smarple1dmv.bo.Residence;
import smarple1dmv.dao.PersonDAO;

@Stateless
public class PersonMgmtEJB implements IPersonMgmtLocal, IPersonMgmtRemote {
	private static Logger logger = LoggerFactory.getLogger(PersonMgmtEJB.class);

	private static final String PERSISTENCE_UNIT = "bo";

	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connFactory;

	@Resource(lookup = "java:/topic/ejava/projects/datashare/dmv", type = Topic.class)
	// @Resource(lookup = "java:/topic/ejava/projects/datashare/mayberry-poi",
	// type = Topic.class)
	private Destination dmvTopic;

	private PersonDAO personDAO;

	@PostConstruct
	public void init() {
		personDAO = new PersonDAO();
		((PersonDAO) personDAO).setEntityManager(em);
	}

	@Override
	@PermitAll
	public void ping() {
		logger.debug("ping called");
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public PersonDTO getPerson(long id) {
		logger.debug("getPerson called");

		Person person = personDAO.getPerson(id);

		return personToDTO(person);
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public PersonDTO getPersonByName(String name) {
		logger.debug("getPersonByName called");

		List<Person> people = (List<Person>) personDAO.getPeopleByName(name);
		if (people.size() < 1) {
			return null;
		}

		return personToDTO(people.get(0));
	}

	@Override
	@RolesAllowed({ "dmv-tactical" })
	public long addPerson(PersonDTO newPerson) {
		logger.debug("addPerson called");

		Person person = dtoToPerson(newPerson);

		personDAO.createPerson(person);

		return person.getId();
	}

	private Person dtoToPerson(PersonDTO personDTO) {
		if (personDTO == null) {
			return null;
		}
		Person person = new Person();
		person.setId(personDTO.getId());
		person.setFirstName(personDTO.getFirstName());
		person.setMiddleName(personDTO.getMiddleName());
		person.setLastName(personDTO.getLastName());
		person.setNameSuffix(personDTO.getNameSuffix());
		return person;
	}

	private PersonDTO personToDTO(Person person) {
		if (person == null) {
			return null;
		}
		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(person.getId());
		personDTO.setFirstName(person.getFirstName());
		personDTO.setMiddleName(person.getMiddleName());
		personDTO.setLastName(person.getLastName());
		personDTO.setNameSuffix(person.getNameSuffix());

		return personDTO;
	}

	private Location dtoToLocation(LocationDTO locationDTO) {
		if (locationDTO == null) {
			return null;
		}
		Location location = new Location();
		location.setId(locationDTO.getId());
		location.setCity(locationDTO.getCity());
		location.setState(locationDTO.getState());
		location.setStreetName(locationDTO.getStreetName());
		location.setStreetNo(locationDTO.getStreetNo());
		location.setZip(locationDTO.getZip());
		return location;
	}

	@SuppressWarnings("unused")
	private LocationDTO locationToDTO(Location location) {
		if (location == null) {
			return null;
		}
		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setId(location.getId());
		locationDTO.setCity(location.getCity());
		locationDTO.setState(location.getState().toString());
		locationDTO.setStreetName(location.getStreetName());
		locationDTO.setStreetNo(location.getStreetNo());
		locationDTO.setZip(location.getZip());
		return locationDTO;
	}

	private Residence dtoToResidence(ResidenceDTO residenceDTO) {
		if (residenceDTO == null) {
			return null;
		}
		Residence residence = new Residence();
		residence.setId(residenceDTO.getId());
		residence.setLocation(dtoToLocation(residenceDTO.getLocation()));
		residence.setPerson(dtoToPerson(residenceDTO.getPerson()));
		residence.setEndDate(residenceDTO.getEndDate());
		residence.setStartDate(residenceDTO.getStartDate());
		return residence;
	}

	@SuppressWarnings("unused")
	private ResidenceDTO residenceToDTO(Residence residence) {
		if (residence == null) {
			return null;
		}
		ResidenceDTO residenceDTO = new ResidenceDTO();
		residenceDTO.setId(residence.getId());
		residenceDTO.setLocation(locationToDTO(residence.getLocation()));
		residenceDTO.setPerson(personToDTO(residence.getPerson()));
		residenceDTO.setStartDate(residence.getStartDate());
		residenceDTO.setEndDate(residence.getStartDate());
		return residenceDTO;
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public List<PersonDTO> getPeople(int index, int count) {

		List<Person> people = personDAO.getPeople(index, count);

		List<PersonDTO> peopleDTO = new ArrayList<PersonDTO>();

		for (Person p : people) {
			peopleDTO.add(personToDTO(p));
		}

		return peopleDTO;
	}

	@Override
	@RolesAllowed({ "dmv-tactical" })
	public void changeResidence(ResidenceDTO newResidence) throws Exception {

		// Get Person
		PersonDTO p = newResidence.getPerson();
		Person person = personDAO.getPerson(p.getId());

		// Convert locationDto to Location
		LocationDTO l = newResidence.getLocation();
		Location location = dtoToLocation(l);

		personDAO
				.changeResidence(person, location, newResidence.getStartDate());

		dmvPersonChange((int) p.getId(), null, true);
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public List<ResidenceDTO> getResidences(long id) throws Exception {

		// Get Person
		Person person = personDAO.getPerson(id);

		Collection<Residence> residences = person.getResidences();

		List<ResidenceDTO> residenceDTOs = new ArrayList<ResidenceDTO>();
		
		for (Residence r : residences) {

			residenceDTOs.add(residenceToDTO(r));
		}
		return residenceDTOs;

	}

	private void dmvPersonChange(int personID, Boolean physicalDetailsChange,
			Boolean residenceChange) throws Exception {

		String username = "sysRaleigh";
		String password = "password1!";

		Publisher dmvPublisher = new Publisher(username, password);

		dmvPublisher.setConnFactory(connFactory);
		dmvPublisher.setDestination(dmvTopic);
		dmvPublisher
				.setDescription("SMARPLE1 DMV Person Residence Change Message!");
		dmvPublisher.setPersonID(personID);
		dmvPublisher.setPhysicalDetailsChange(physicalDetailsChange);
		dmvPublisher.setResidenceChange(residenceChange);

		dmvPublisher.sendMessage();
	}
}
