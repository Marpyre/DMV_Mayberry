package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@WebServlet("/CommandMayberryAnalytical/*")
public class MayberryAnalytical extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MayberryTactical.class);
	public static final String COMMAND_PARAM = "command";

	// private InitialContext jndi;
	//
	// private static final String dmvTestUtilJDNI = System.getProperty(
	// "jndi.name.dmvtestutil",
	// "ejb:smarple1DmvEAR/smarple1DmvEJB/DmvTestUtilEJB!"
	// + IDmvTestUtilRemote.class.getName());
	//
	// private IDmvTestUtilRemote testUtilInterface;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MayberryAnalytical() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet() called");

		String command = request.getParameter(COMMAND_PARAM);
		log.debug("command=" + command);

		try {
			// jndi = new InitialContext();
			//
			// jndi.lookup("jms");
			//
			// testUtilInterface = (IDmvTestUtilRemote) jndi
			// .lookup(dmvTestUtilJDNI);

			if (command == null) {

				request.setAttribute("msg", "");

			} else if (command.equals("getPOI")) {

				// testUtilInterface.populate();

				request.setAttribute("msg", "POI Information Retrieved");

			} else if (command.equals("checkPOIDanger")) {

				// testUtilInterface.resetAll();

				request.setAttribute("msg", "POI Danger Check Completed");

			} else {
				throw new Exception("Illegal Action Specified.");
			}
			
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/MayberryAnalytical.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception ex) {

			request.setAttribute("errorMsg", ex.getMessage());

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
