package smarple1MayberryJMS;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvEJB.IPersonMgmtRemote;
import smarple1MayberryBL.BLImpl;
import smarple1MayberryBO.POI;
import smarple1MayberryEJB.POIRemote;

@RunAs("dmv-federated")
@MessageDriven(name = "DMVListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/ejava/projects/datashare/dmv"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class DMVListener implements MessageListener {

	private static final Log log = LogFactory.getLog(DMVListener.class);

	@Resource
	private MessageDrivenContext mdc;

	@EJB(lookup = "ejb:smarple1Mayberry/PoiEJB!smarple1MayberryEJB.POIRemote")
	POIRemote poiRemote;

	@EJB(lookup = "ejb:smarple1DmvEAR/smarple1DmvEJB/PersonMgmtEJB!smarple1DmvEJB.IPersonMgmtRemote")
	IPersonMgmtRemote personEJB;

	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connFactory;

	// @Resource(lookup = "java:/topic/ejava/projects/datashare/dmv", type =
	// Topic.class)
	@Resource(lookup = "java:/topic/ejava/projects/datashare/mayberry-poi", type = Topic.class)
	private Destination mayberryTopic;

	private static final String PERSISTENCE_UNIT = "mayberryBo";

	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	@Override
	@PermitAll
	public void onMessage(Message message) {
		try {
			log.debug("onMessage:" + message.getJMSMessageID());

			String dmvMessage = "";
			int dmvID = 0;

			if (message instanceof TextMessage) {
				dmvMessage = ((TextMessage) message).getText();
				dmvID = message.getIntProperty("personID");
			}

			PersonDTO dmvPerson = personEJB.getPerson(dmvID);

			List<POI> POIs = poiRemote.findPOIMatches(dmvPerson.getFirstName(),
					dmvPerson.getLastName(), 0);

			if (POIs != null) {

				if (POIs.size() > 0) {

					Publisher mayberryPublisher = new Publisher("sysMayberry",
							"password1!");
					mayberryPublisher.setConnFactory(connFactory);
					mayberryPublisher.setDestination(mayberryTopic);

					Boolean dangerFound = false;
					StringBuilder sb = new StringBuilder("");

					for (POI poi : POIs) {
						if (BLImpl.isDangerous(poi))
							dangerFound = true;
						sb.append("[" + poi.getId() + "]");
					}

					mayberryPublisher.setDescription("POI Change Message:"
							+ "ID:" + sb.toString());

					mayberryPublisher.setIsDangerous(dangerFound);

					mayberryPublisher.sendMessage();
				}
			}
			log.debug("Message:" + dmvMessage);
		} catch (Exception ex) {
			log.error("error processing message", ex);
		}
	}
}