package smarple1dmv.dao;

import java.util.List;

import javax.persistence.EntityManager;

import smarple1dmv.bo.Person;

public class PersonDAO implements IPersonDAO{
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
	
	 public void update(Person person){
		 em.merge(person);
	 }

	@SuppressWarnings("unchecked")
    public List<Person> getPeople(int index, int count)  {
	    return (List<Person>)em.createQuery("select p from Person p")
	                             .setFirstResult(index)
	                             .setMaxResults(count)
	                             .getResultList();
	}
}