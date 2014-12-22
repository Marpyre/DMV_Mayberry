package smarple1dmv.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import smarple1dmv.bo.Location;
import smarple1dmv.bo.Person;
import smarple1dmv.bo.Residence;

public class PersonDAO implements IPersonDAO {
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public void createPerson(Person person) {
		em.persist(person);
	}

	public Person getPerson(long id) {
		return em.find(Person.class, id);
	}

	public void update(Person person) {
		em.merge(person);
	}

	public void addNewResidence(Person person, Location location, Date startDate) {
		em.persist(location);

		Residence residence = new Residence(person, location);
		residence.setStartDate(startDate);
		em.persist(residence);
	}

	public void changeResidence(Person person, Location location, Date startDate) {
		em.persist(location);

		Residence residence = new Residence(person, location);
		residence.setStartDate(startDate);
		em.persist(residence);
	}

	/*
	 * public void remove(Person person){ em.remove(person); }
	 */

	@SuppressWarnings("unchecked")
	public List<Person> getPeople(int index, int count) {
		return (List<Person>) em.createQuery("select p from Person p")
				.setFirstResult(index).setMaxResults(count).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPeopleByName(String firstName) {
		return (List<Person>) em
				.createQuery(
						"select p from Person p where p.firstName like :personFirstName")
				.setParameter("personFirstName", firstName).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPersonByDTO(String firstName, String middleName,
			String lastName, String suffix) {
		return (List<Person>) em
				.createQuery(
						"select p from Person p where p.firstName like :personFirstName and p.lastName like :personLastName and p.middleName like :personMiddleName and p.nameSuffix like :personNameSuffix")
				.setParameter("personFirstName", firstName)
				.setParameter("personLastName", lastName)
				.setParameter("personMiddleName", middleName)
				.setParameter("personNameSuffix", suffix).getResultList();
	}
}