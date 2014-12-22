package smarple1MayberryEJB;

import java.util.List;

import javax.ejb.Remote;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvDTO.ResidenceDTO;
import smarple1MayberryBO.Activity.Code;
import smarple1MayberryBO.POI;

@Remote
public interface POIRemote {
	POI addPOI(long id);

	List<POI> findPOIMatches(String firstName, String lastName, int index);

	List<POI> getAllPOI(int index, int count);

	long countPOI();

	void resetDB();

	POI addPOI(String name);

	void addActivity(POI poi, Code type);

	public PersonDTO getPerson(long dmvId);

	public List<ResidenceDTO> getResidenceInfo(long dmvID) throws Exception;
}