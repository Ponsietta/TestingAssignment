package src.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogIn
 */
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		 
		   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		 String username = request.getParameter("username");   
		 String password = request.getParameter("password");
		 String friendID = "1";
		 
		 
		 ChatSession chatSession = new ChatSession(new ChatProviderStubSuccess());
		 int result = chatSession.initSession(username, password, friendID);
		 
		 PrintWriter out = response.getWriter();
		 
		 if (result == 0){
			 HttpSession session = request.getSession(true);
			 session.setAttribute("chatSession", chatSession);
			 session.setAttribute("username", username);
			 
			 response.sendRedirect("Chat.jsp");
		 }
		 else 
		 {
			 if (result == 1)
			 {
				 request.setAttribute("loginsuccess", "false");
				 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
			     rd.forward(request, response);
			     
			     out.println ();
			 }
			 else if (result == 2)
				 out.println ();
		 }
		 
	}

}
