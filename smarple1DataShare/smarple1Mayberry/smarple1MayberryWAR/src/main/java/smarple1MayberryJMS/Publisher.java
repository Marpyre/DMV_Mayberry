package smarple1MayberryJMS;

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
	protected ConnectionFactory connFactory;
	protected Destination destination;
	protected String username;
	protected String password;
	protected Boolean isDangerous;
	protected String description;
	protected Boolean isPoiList;

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

	public void setIsDangerous(Boolean isDangerous) {
		this.isDangerous = isDangerous;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIsPoiList(Boolean isPoiList){
		this.isPoiList = isPoiList;
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
			if (isDangerous != null) {
				message.setBooleanProperty("isDangerous", isDangerous);
			}
			if (isPoiList != null) {
				message.setBooleanProperty("isPoiList", isPoiList);
			}
			message.setText(description);
			producer.send(message);
			log.info("Publishing message: " + description);
			log.info("POI Dangerous: " + isDangerous);
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