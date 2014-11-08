package smarple1dmv.dao;

import java.util.List;

import smarple1dmv.bo.Person;

public interface IPersonDAO {

	void createPerson(Person person) throws DAOException;

	Person getPerson(long id) throws DAOException;

	List<Person> getPeople(int index, int count) throws DAOException;
}