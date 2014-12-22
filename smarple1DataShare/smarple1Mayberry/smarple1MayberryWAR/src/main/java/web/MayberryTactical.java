package web;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import smarple1DmvEJB.IDmvTestUtilRemote;
import smarple1MayberryEJB.POIRemote;

@WebServlet("/CommandMayberryTactical/*")
public class MayberryTactical extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MayberryTactical.class);
	public static final String COMMAND_PARAM = "command";

	// Mayberry EJB Interface
	protected POIRemote poiInterface;

	// Mayberry EJB JNDI
	protected static final String poiJNDI = "ejb:/smarple1Mayberry/PoiEJB!smarple1MayberryEJB.POIRemote";

	protected InitialContext jndi;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MayberryTactical() {
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
			jndi = new InitialContext();

			jndi.lookup("jms");

			poiInterface = (POIRemote) jndi.lookup(poiJNDI);

			if (command == null) {

				request.setAttribute("msg", "");

			} else if (command.equals("createPOI")) {

				//poiInterface.addActivity(null, null);

				request.setAttribute("msg", "POI Created");

			} else if (command.equals("addActivity")) {

				// testUtilInterface.resetAll();

				request.setAttribute("msg", "Activity Added");

			} else if (command.equals("getPOI")) {

				//poiInterface.getAllPOI(0, 0);

				request.setAttribute("msg", "POI Information Retrieved");

			} else if (command.equals("checkPOIDanger")) {

				//poiInterface.

				request.setAttribute("msg", "POI Danger Check Completed");

			} else {
				throw new Exception("Illegal Action Specified.");
			}

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/MayberryTactical.jsp");
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
