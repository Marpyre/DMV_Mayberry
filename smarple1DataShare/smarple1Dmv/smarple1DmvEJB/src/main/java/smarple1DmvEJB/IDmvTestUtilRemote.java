package smarple1DmvEJB;

import javax.ejb.Remote;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

@Remote
public interface IDmvTestUtilRemote {
	void populate() throws JAXBException, XMLStreamException, Exception;
	void resetAll();
	void ping();
}
