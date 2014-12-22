package smarple1DmvEJB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.VehicleRegistrationDTO;
import smarple1dmv.bo.VehicleRegistration;
import smarple1dmv.dao.VehicleDAO;

@Stateless
public class VehicleMgmtEJB implements IVehicleMgmtLocal, IVehicleMgmtRemote {
	private static Logger logger = LoggerFactory
			.getLogger(VehicleMgmtEJB.class);

	private static final String PERSISTENCE_UNIT = "bo";

	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	@Override
	@PermitAll
	public void ping() {
		logger.debug("ping called");
	}

	private VehicleRegistration dtoToVehicleRegistration(
			VehicleRegistrationDTO vrDTO) {
		if (vrDTO == null) {
			return null;
		}
		VehicleRegistration vr = new VehicleRegistration();
		vr.setId(vrDTO.getId());
		vr.setColor(vrDTO.getColor());
		vr.setExpiration(vrDTO.getExpiration());
		vr.setMake(vrDTO.getMake());
		vr.setModel(vrDTO.getModel());
		vr.setTagNo(vrDTO.getTagNo());
		vr.setVin(vrDTO.getVin());
		vr.setYear(vrDTO.getYear());
		return vr;
	}

	private VehicleRegistrationDTO vehicleRegistrationToDTO(
			VehicleRegistration vr) {
		if (vr == null) {
			return null;
		}
		VehicleRegistrationDTO vrDTO = new VehicleRegistrationDTO();
		vrDTO.setId(vr.getId());
		vrDTO.setColor(vr.getColor() + "");
		vrDTO.setExpiration(vr.getExpiration());
		vrDTO.setMake(vr.getMake());
		vrDTO.setModel(vr.getModel());
		vrDTO.setTagNo(vr.getTagNo());
		vrDTO.setVin(vr.getVin());
		vrDTO.setYear(vr.getYear());
		return vrDTO;
	}

	@Override
	@RolesAllowed({ "dmv-tactical" })
	public void addVehicleRegistration(VehicleRegistrationDTO vrDTO) {
		
		VehicleDAO vehicleDAO = new VehicleDAO();
		((VehicleDAO) vehicleDAO).setEntityManager(em);

		VehicleRegistration vr = dtoToVehicleRegistration(vrDTO);

		vehicleDAO.createRegistration(vr);
	}

	@Override
	@RolesAllowed({ "dmv-federated", "dmv-tactical" })
	public List<VehicleRegistrationDTO> getRegistrations(int index, int count) {

		VehicleDAO vehicleDAO = new VehicleDAO();
		((VehicleDAO) vehicleDAO).setEntityManager(em);
		
		List<VehicleRegistration> vehicles = vehicleDAO.getRegistrations(index, count);
		
		List<VehicleRegistrationDTO> vrDTO = new ArrayList<VehicleRegistrationDTO>();
		
		for(VehicleRegistration v : vehicles){
			vrDTO.add(vehicleRegistrationToDTO(v));
		}
		
		return vrDTO;
	}
}
