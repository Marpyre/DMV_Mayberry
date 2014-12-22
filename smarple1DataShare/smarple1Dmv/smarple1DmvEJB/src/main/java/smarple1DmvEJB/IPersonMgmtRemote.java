package smarple1DmvEJB;

import java.util.List;

import javax.ejb.Remote;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.ResidenceDTO;

@Remote
public interface IPersonMgmtRemote {
	void ping();

	PersonDTO getPerson(long id);

	PersonDTO getPersonByName(String name);

	List<PersonDTO> getPeople(int index, int count);

	long addPerson(PersonDTO newPerson);

	void changeResidence(ResidenceDTO newResidence) throws Exception;

	public List<ResidenceDTO> getResidences(long id)
			throws Exception;
}
