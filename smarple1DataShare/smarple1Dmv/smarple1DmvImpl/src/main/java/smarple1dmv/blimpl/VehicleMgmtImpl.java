package smarple1dmv.blimpl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import smarple1dmv.dao.VehicleDAO;
import smarple1dmv.bo.VehicleRegistration;

public class VehicleMgmtImpl {
	private static final Log log = LogFactory.getLog(VehicleMgmtImpl.class);

	private VehicleDAO vehicleDAO;
	
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
	    this.vehicleDAO = vehicleDAO;
	}
	
    public List<VehicleRegistration> getRegistrations(int index, int count)
            throws DmvException {
        if (count < 0) {
            throw new DmvException("count must be >= 0");
        }
        
        //try {
            return vehicleDAO.getRegistrations(index, count);
        //}
        /*catch (DAOException ex) {
            log.error("error getting registrations", ex);
            throw new DmvException("error getting registrations:" + ex);
        }*/
    }	
}
