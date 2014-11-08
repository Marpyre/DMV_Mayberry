package smarple1dmv.blimpl;

import java.util.List;

import smarple1dmv.bo.VehicleRegistration;

public interface IVehicleMgmt {
	
	List<VehicleRegistration> getRegistrations(int index, int count)
			throws DmvException;
	
}
