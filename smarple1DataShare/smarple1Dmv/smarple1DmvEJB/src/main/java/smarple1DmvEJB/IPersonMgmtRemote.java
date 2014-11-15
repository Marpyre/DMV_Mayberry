package smarple1DmvEJB;

import javax.ejb.Remote;

import smarple1DmvDTO.PersonDTO;

@Remote
public interface IPersonMgmtRemote {
	void ping();
	PersonDTO getPerson();
	PersonDTO getPersonByName(String name);
	void addPerson(PersonDTO newPerson);
}
