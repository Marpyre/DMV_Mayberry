package smarple1DmvEJB;

import java.util.List;

import javax.ejb.Remote;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.VehicleRegistrationDTO;

@Remote
public interface IDriverMgmtRemote {
	void ping();
	PersonDTO getDriverByID(Long id);
	PersonDTO getDriverByName(String name);
	void createDriver(PersonDTO person);
	List<PersonDTO> getDrivers(int index, int count);
	void addRegistration(VehicleRegistrationDTO vr, Long id);
}
