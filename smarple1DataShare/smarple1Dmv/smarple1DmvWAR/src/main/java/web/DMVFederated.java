package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class DMVFederated
 */
@WebServlet("/CommandDMVFederated/*")
public class DMVFederated extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = LoggerFactory
			.getLogger(DMVFederated.class);

	public static final String COMMAND_PARAM = "command";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DMVFederated() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet() called");
		
		String command = request.getParameter(COMMAND_PARAM);
		log.debug("command=" + command);

		try {
			if (command == null) {

				request.setAttribute("msg", "");
				
			} else if (command.equals("driverID")) {
	
				request.setAttribute("msg", "Name was found!");

			} else if (command.equals("driverName")) {
				
				request.setAttribute("msg", "Name was found!");

			} else if (command.equals("vrMm")) {
				
				request.setAttribute("msg", "Vehicle Registration was found!");

			}else if (command.equals("vrOwner")) {
				
				request.setAttribute("msg", "Vehicle Registration was found!");

			}else {
				throw new Exception("Illegal Action Specified.");
			}
			
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/DMVFederated.jsp");
			dispatcher.forward(request, response);
		}catch (Exception ex) {
			
			request.setAttribute("errorMsg", ex.getMessage());
			
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
