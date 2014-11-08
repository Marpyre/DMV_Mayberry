package smarple1dmv.blimpl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import smarple1dmv.dao.DAOException;
import smarple1dmv.dao.PersonDAO;
import smarple1dmv.bo.Person;

public class DriverMgmtImpl implements IPersonMgmt{
	private static final Log log = LogFactory.getLog(DriverMgmtImpl.class);
	
	private PersonDAO personDAO;
	
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}	

    public List<Person> getPeople(int index, int count) throws DmvException {
        if (count < 0) {
            throw new DmvException("count must be >= 0");
        }
        
        //try {
            return personDAO.getPeople(index, count);
       /* }
        catch (DAOException ex) {
            log.error("error getting people", ex);
            throw new DmvException("error getting people:" + ex);
        }*/
    }
    

}
