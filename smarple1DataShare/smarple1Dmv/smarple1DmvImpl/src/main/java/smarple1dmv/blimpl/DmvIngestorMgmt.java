package smarple1dmv.blimpl;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import smarple1dmv.dao.PersonDAO;
import smarple1dmv.dao.VehicleDAO;

public class DmvIngestorMgmt {

	private DmvIngestor ingestor;
	String fileName = "xml/dmv-all.xml";
	
    public DmvIngestorMgmt(){
    	ingestor = new DmvIngestor();
    	InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName);
    	ingestor.setInputStream(is);
    }
    
	public void setPersonDAO(PersonDAO personDAO) {
	    ingestor.setPersonDAO(personDAO);
	}
	
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		ingestor.setVehicleDAO(vehicleDAO);
	}
	
	public void ingest() throws JAXBException, XMLStreamException, Exception{
		ingestor.ingest();
	}
}