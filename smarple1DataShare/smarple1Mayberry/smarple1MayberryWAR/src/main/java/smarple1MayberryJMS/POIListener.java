package smarple1MayberryJMS;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class POIListener implements Runnable {
	private static final Log log = LogFactory.getLog(POIListener.class);
	protected ConnectionFactory connFactory;
	protected Destination destination;

	protected String name;

	protected boolean durable = false;
	protected String selector = null;
	protected String username;
	protected String password;

	public POIListener(String name) {
		this.name = name;
	}

	public void setConnFactory(ConnectionFactory connFactory) {
		this.connFactory = connFactory;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void execute() throws Exception {
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			connection = username == null ? connFactory.createConnection()
					: connFactory.createConnection(username, password);
			connection.setClientID(name);
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			if (durable == false) {
				try {
					session.unsubscribe(name);
				} catch (JMSException ignored) {
				}
				consumer = session.createConsumer(destination, selector);
			} else {
				consumer = session.createDurableSubscriber((Topic) destination,
						name, selector, false);
			}
			connection.start();

			while (true) {
				Message message = consumer.receive(3000);
				if (message != null) {

					StringBuilder text = new StringBuilder();
					text.append(name + " received message Id="
							+ message.getJMSMessageID());

					Boolean isDangerous = message
							.getBooleanProperty("isDangerous");
					text.append(", Dangerous!");
					if (message instanceof TextMessage) {
						text.append(", body="
								+ ((TextMessage) message).getText());
					}
					System.out.println(text.toString());
					log.debug(text.toString());
				}
			}
			// log.info("subscriber " + name + " stopping");
			// connection.stop();
		} finally {
			connection.stop();
			if (consumer != null) {
				consumer.close();
			}
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void run() {
		try {
			execute();
		} catch (Exception ex) {
			log.fatal("error running " + name, ex);
		}
	}

	public static void main(String args[]) {
		try {
			String connFactoryJNDI = "/jms/RemoteConnectionFactory";
			String destinationJNDI = "topic/ejava/projects/datashare/mayberry-poi";
			String name = "Publisher0";
			String username = "sysMayberry";
			String password = "password1!";

			POIListener subscriber = new POIListener(name);
			Context jndi = new InitialContext();

			subscriber.setConnFactory((ConnectionFactory) jndi
					.lookup(connFactoryJNDI));
			subscriber.setDestination((Destination) jndi
					.lookup(destinationJNDI));

			subscriber.setUsername(username);
			subscriber.setPassword(password);

			subscriber.setSelector("isDangerous=true");

			subscriber.execute();
		} catch (Exception ex) {
			log.fatal("SMARPLE1 MAYBERRY POIListener ERROR", ex);
			System.exit(-1);
		}
	}
}