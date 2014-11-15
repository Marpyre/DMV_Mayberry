package smarple1dmv.blimpl;

import java.util.List;

import smarple1dmv.bo.Person;

public interface IPersonMgmt {

	List<Person> getPeople(int index, int count) throws DmvException;
	
}