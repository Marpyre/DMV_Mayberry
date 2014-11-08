package smarple1dmv.dao;

import java.util.List;

import smarple1dmv.bo.VehicleRegistration;

public interface IVehicleDAO {

	void createRegistration(VehicleRegistration registration)
			throws DAOException;

	List<VehicleRegistration> getRegistrations(int index, int count)
			throws DAOException;
}