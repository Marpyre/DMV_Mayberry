package smarple1Mayberry.integration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import smarple1MayberryJMS.Publisher;

public class JMSPublisherIT {

	private static final Log log = LogFactory.getLog(JMSPublisherIT.class);

	protected ConnectionFactory connFactory;
	protected Destination destination;

	protected String username = "sysMayberry";
	protected String password = "password1!";

	protected String connFactoryJNDI = "/jms/RemoteConnectionFactory";
	protected String destinationJNDI = "topic/ejava/projects/datashare/mayberry-poi";

	@Test
	public void jmsPublisherTest() throws Exception {

		log.debug("*** JMSPublisherIT Start ***");

		Context jndi = new InitialContext();
		connFactory = (ConnectionFactory) jndi.lookup(connFactoryJNDI);
		destination = (Destination) jndi.lookup(destinationJNDI);

		Publisher poiChangePublisher = new Publisher(username, password);

		poiChangePublisher.setConnFactory(connFactory);
		poiChangePublisher.setDestination(destination);
		poiChangePublisher.setIsDangerous(true); //so that mayberry POIListener "hears" it
		poiChangePublisher
				.setDescription("JMS Publisher IT Test from Mayberry.");

		poiChangePublisher.sendMessage();

		log.debug("*** JMSPublisherIT Complete ***");
	}
}