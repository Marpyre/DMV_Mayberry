package smarple1DmvTest;

import javax.naming.InitialContext;

import org.junit.AfterClass;

import smarple1DmvEJB.IDmvTestUtilRemote;

public class ITBase {

	@AfterClass
	public static void tearDownClass() throws Exception {
		InitialContext jndi;

		final String dmvTestUtilJDNI = System.getProperty(
				"jndi.name.dmvtestutil",
				"ejb:smarple1DmvEAR/smarple1DmvEJB/DmvTestUtilEJB!"
						+ IDmvTestUtilRemote.class.getName());

		IDmvTestUtilRemote testUtilInterface;

		jndi = new InitialContext();
		jndi.lookup("jms");

		testUtilInterface = (IDmvTestUtilRemote) jndi.lookup(dmvTestUtilJDNI);

		testUtilInterface.resetAll();
	}
}
