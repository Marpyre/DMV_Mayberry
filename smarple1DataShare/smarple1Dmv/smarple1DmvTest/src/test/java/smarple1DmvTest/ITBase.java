package smarple1DmvTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.junit.After;
import org.junit.Before;

import smarple1DmvEJB.IDmvTestUtilRemote;
import smarple1DmvEJB.IDriverMgmtRemote;
import smarple1DmvEJB.IPersonMgmtRemote;
import smarple1DmvEJB.IVehicleMgmtRemote;

public class ITBase {

	protected BasicCallbackHandler adminHandler = new BasicCallbackHandler("admin1", "password1!");
	protected BasicCallbackHandler knownHandler = new BasicCallbackHandler("known", "password1!");
	protected BasicCallbackHandler kathyHandler = new BasicCallbackHandler("kathy", "password1!");
	protected BasicCallbackHandler sherriHandler = new BasicCallbackHandler("sherri", "password1!");
	protected BasicCallbackHandler sysRaleighHandler = new BasicCallbackHandler("sysRaleigh", "password1!");
	protected BasicCallbackHandler sysMayberryHandler = new BasicCallbackHandler("sysMayberry", "password1!");
	protected BasicCallbackHandler andyHandler = new BasicCallbackHandler("andy", "password1!");
	protected BasicCallbackHandler barneyHandler = new BasicCallbackHandler("barney", "password1!");
	protected BasicCallbackHandler gomerHandler = new BasicCallbackHandler("gomer", "password1!");
	
	protected InitialContext jndi;
	protected IDmvTestUtilRemote testUtilInterface;
    protected IDriverMgmtRemote driverManager;
    protected IPersonMgmtRemote personManager;
    protected IVehicleMgmtRemote vehicleManager;
	
	protected final String dmvTestUtilJDNI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/DmvTestUtilEJB!"
					+ IDmvTestUtilRemote.class.getName());
    
    protected static final String driverMgmtJNDI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/DriverMgmtEJB!"
					+ IDriverMgmtRemote.class.getName());

	protected static final String personMgmtJNDI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/PersonMgmtEJB!"
					+ IPersonMgmtRemote.class.getName());
	    
    protected static final String vehicleMgmtJNDI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/VehicleMgmtEJB!"
					+ IVehicleMgmtRemote.class.getName());
	
	@Before
	public void setUp() throws JAXBException, XMLStreamException, Exception {
		jndi = new InitialContext();
		jndi.lookup("jms");

		testUtilInterface = (IDmvTestUtilRemote) jndi.lookup(dmvTestUtilJDNI);
        driverManager = (IDriverMgmtRemote) jndi.lookup(driverMgmtJNDI);
		personManager = (IPersonMgmtRemote) jndi.lookup(personMgmtJNDI);
		vehicleManager = (IVehicleMgmtRemote) jndi.lookup(vehicleMgmtJNDI);
		
		runAs(adminHandler);
		testUtilInterface.populate();
	}
	
	@After
	public void tearDown() throws Exception {
		runAs(adminHandler);
		testUtilInterface.resetAll();
	}
	
    public void runAs(CallbackHandler login) throws NamingException, IOException {
        InputStream is = getClass().getResourceAsStream("/jboss-ejb-client.properties");
        try {
            Properties props = new Properties();
            props.load(is);
            BasicCallbackHandler.setLogin(login);
            EJBClientConfiguration cfg = new PropertiesBasedEJBClientConfiguration(props);
            ContextSelector<EJBClientContext> contextSelector = new ConfigBasedEJBClientContextSelector(cfg);
            EJBClientContext.setSelector(contextSelector);
        } finally {
            is.close();
        }
    }
}
