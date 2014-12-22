package smarple1DmvJMS;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Publisher {
	private static final Log log = LogFactory.getLog(Publisher.class);
	protected ConnectionFactory connFactory = null;
	protected Destination destination = null;
	protected String username = null;
	protected String password = null;
	protected String description = null;
	protected Boolean residenceChange = null;
	protected Boolean physicalDetailsChange = null;
	protected int personID = 0;

	public Publisher(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void setConnFactory(ConnectionFactory connFactory) {
		this.connFactory = connFactory;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setResidenceChange(Boolean residenceChange) {
		this.residenceChange = residenceChange;
	}

	public void setPhysicalDetailsChange(Boolean physicalDetailsChange) {
		this.physicalDetailsChange = physicalDetailsChange;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public void sendMessage() throws Exception {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			connection = connFactory.createConnection(username, password);
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(destination);

			TextMessage message = session.createTextMessage();
			if (residenceChange != null) {
				message.setBooleanProperty("residenceChange", residenceChange);
			}
			if (physicalDetailsChange != null) {
				message.setBooleanProperty("physicalDetailsChange",
						physicalDetailsChange);
			}
			if (personID != 0) {
				message.setObjectProperty("personID", personID);
			}
			message.setText(description);
			log.info("Publishing message: " + description);
			connection.stop();
		} finally {
			if (producer != null) {
				producer.close();
			}
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}