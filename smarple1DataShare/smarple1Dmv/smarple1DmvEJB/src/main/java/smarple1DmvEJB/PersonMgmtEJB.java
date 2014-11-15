package smarple1DmvEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;
import smarple1dmv.bo.Person;
import smarple1dmv.dao.PersonDAO;

@Stateless
public class PersonMgmtEJB implements IPersonMgmtLocal, IPersonMgmtRemote {
	private static Logger logger = LoggerFactory
			.getLogger(ReservationEJB.class);

	private static final String PERSISTENCE_UNIT = "bo";

	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	@Override
	public void ping() {
		logger.debug("ping called");
	}

	@Override
	public PersonDTO getPerson() {
		logger.debug("getPerson called");
		
		PersonDAO personDAO = new PersonDAO();
		((PersonDAO) personDAO).setEntityManager(em);

		Person person = personDAO.getPerson(1);

		return personToDTO(person);
	}

	@Override
	public PersonDTO getPersonByName(String name) {
		logger.debug("getPersonByName called");
		
		PersonDAO personDAO = new PersonDAO();
		((PersonDAO) personDAO).setEntityManager(em);

		List<Person> people = (List<Person>) personDAO.getPeopleByName(name);
		if (people.size() < 1) {
			return null;
		}
		
		return personToDTO(people.get(0));
	}

	@Override
	public void addPerson(PersonDTO newPerson) {
		logger.debug("addPerson called");
		
		PersonDAO personDAO = new PersonDAO();
		((PersonDAO) personDAO).setEntityManager(em);

		Person person = dtoToPerson(newPerson);

		personDAO.createPerson(person);

	}

	private Person dtoToPerson(PersonDTO personDTO) {
		if (personDTO == null) {
			return null;
		}
		Person person = new Person();
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
		PersonDTO personDTO = new PersonDTO(person);

		return personDTO;
	}
}
