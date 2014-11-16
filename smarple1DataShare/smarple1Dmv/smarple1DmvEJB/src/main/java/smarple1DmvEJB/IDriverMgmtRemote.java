package smarple1DmvEJB;

import javax.ejb.Remote;

import smarple1DmvDTO.PersonDTO;

@Remote
public interface IDriverMgmtRemote {
	void ping();
	PersonDTO getDriverByID(Long id);
	PersonDTO getDriverByName(String name);
}
