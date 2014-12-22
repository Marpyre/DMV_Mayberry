package smarple1DmvEJB;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.VehicleRegistrationDTO;
import smarple1dmv.blimpl.DriverMgmtImpl;
import smarple1dmv.bo.Person;
import smarple1dmv.dao.PersonDAO;

@Stateless
public class DriverMgmtEJB implements IDriverMgmtLocal, IDriverMgmtRemote {
	private static Logger log = LoggerFactory.getLogger(DriverMgmtEJB.class);

	private static final String PERSISTENCE_UNIT = "bo";

	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	PersonDAO personDAO;
	
	DriverMgmtImpl driverManager;

	@Override
	@PermitAll
	public void ping() {
		log.debug("ping called");
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public PersonDTO getDriverByID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public PersonDTO getDriverByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RolesAllowed({ "dmv-tactical" })
	public void createDriver(PersonDTO person) {
		log.debug("Create Driver Answer Start");

		driverManager = new DriverMgmtImpl();
		
		personDAO = new PersonDAO();
		((PersonDAO)personDAO).setEntityManager(em);
		
		Person newPerson = new Person();

		newPerson.setFirstName(person.getFirstName());
		newPerson.setMiddleName(person.getMiddleName());
		newPerson.setLastName(person.getLastName());
		newPerson.setNameSuffix(person.getNameSuffix());
		
		driverManager.createPerson(newPerson);

		log.debug("Create Driver Answer End");
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public List<PersonDTO> getDrivers(int index, int count) {
		
		//call get people on personmgmtejb and return those with vrs
		
		return null;
	}

	@Override
	@RolesAllowed({ "dmv-tactical" })
	public void addRegistration(VehicleRegistrationDTO vr, Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}