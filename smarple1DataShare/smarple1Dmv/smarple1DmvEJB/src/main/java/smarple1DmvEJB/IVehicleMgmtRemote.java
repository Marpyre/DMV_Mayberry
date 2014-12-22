package smarple1DmvEJB;

import java.util.List;

import javax.ejb.Remote;

import smarple1DmvDTO.VehicleRegistrationDTO;

@Remote
public interface IVehicleMgmtRemote {
	void ping();
	void addVehicleRegistration(VehicleRegistrationDTO vrDTO);
	List<VehicleRegistrationDTO> getRegistrations(int index, int count);
}
