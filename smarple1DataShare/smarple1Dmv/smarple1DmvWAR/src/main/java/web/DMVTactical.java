package web;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarple1DmvDTO.PersonDTO;
import smarple1DmvEJB.IPersonMgmtRemote;

/**
 * Servlet implementation class DMVTactical
 */
@WebServlet("/CommandDMVTactical/*")
public class DMVTactical extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory
			.getLogger(DMVTactical.class);

	public static final String COMMAND_PARAM = "command";

	private InitialContext jndi;

	private static final String personMgmtJNDI = System.getProperty(
			"jndi.name.dmvtestutil",
			"ejb:smarple1DmvEAR/smarple1DmvEJB/PersonMgmtEJB!"
					+ IPersonMgmtRemote.class.getName());

	private IPersonMgmtRemote personManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DMVTactical() {
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

			personManager = (IPersonMgmtRemote) jndi.lookup(personMgmtJNDI);

			if (command == null) {

				request.setAttribute("msg", "");
				
			} else if (command.equals("add")) {

				PersonDTO newPerson = new PersonDTO();
				newPerson.setFirstName(request.getParameter("fname") + "");
				newPerson.setMiddleName(request.getParameter("mname") + "");
				newPerson.setLastName(request.getParameter("lname") + "");
				newPerson.setNameSuffix(request.getParameter("sname") + "");
				
				personManager.addPerson(newPerson);
				
				request.setAttribute("msg", "Add Complete");

			} else if (command.equals("find")) {
				
				PersonDTO person = personManager.getPersonByName(request.getParameter("lname") + "");
				
				request.setAttribute("msg", person.getLastName() + " was found!");

			} else {
				throw new Exception("Illegal Action Specified.");
			}
			
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/DMVTactical.jsp");
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
