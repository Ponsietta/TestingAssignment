package src.system.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import src.system.ChatSession;
import tests.stubs.ChatProviderStubSuccess;

/**
 * Servlet implementation class LogIn
 */
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String user = "rebmar"; //assuming we will always use this username to log on
	private int incorrectLoginCounter = 0;
	private long incorrectLoginStartTime = 0;
	private long incorrectLoginDisableStartTime = 0;
	private boolean accountEnabled = true;
	private long startTime = 0;
       
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String username = request.getParameter("username");   
		 String password = request.getParameter("password");
		 String friendID = "1";		 
		 
		 ChatSession chatSession = new ChatSession(new ChatProviderStubSuccess());
		 int result = chatSession.initSession(username, password, friendID);
		 HttpSession session = request.getSession(true);
		 
		 session.setAttribute("violationCounter", 0);
		 
		 PrintWriter out = response.getWriter();
		 
		 if (user.equals(username))
		 {
			 //account is disabled due to parent lock violation
			 if ("true".equals(session.getAttribute("parentLockDisable")))
			 {
				 startTime = (long) session.getAttribute("parentLockDisabledStartTime");
				 if(System.currentTimeMillis()-startTime<120000)
				 {
				 	//account disabled
					 accountEnabled = false;
					 request.setAttribute("parentLockDisable", "true");
					 request.setAttribute("accountEnabled", "false");
					 request.setAttribute("loginsuccess", "false");
					 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
				     rd.forward(request, response);
				 }
				 else{
					 //enable account
					 accountEnabled = true;
					 request.setAttribute("parentLockDisable", "false");
					 request.setAttribute("accountEnabled", "true");
				 }
			 }
			  
			 //try with locking 
			//if account enabled or the lock time has expired then log ons should be allowed
			 if ((accountEnabled || System.currentTimeMillis()-incorrectLoginDisableStartTime>30000)
					 && !"true".equals(request.getAttribute("parentLockDisable")))
			 {
				
				 incorrectLoginDisableStartTime = 0;
				 
				 if (result == 0){
					 
					 session.setAttribute("chatSession", chatSession);
					 session.setAttribute("username", username);
					 
					 response.sendRedirect("Chat.jsp");
				 }
				 else 
				 {
					 if (result == 1)
					 {
						 if (incorrectLoginCounter == 0)
							 incorrectLoginStartTime = System.currentTimeMillis();
						 
						 incorrectLoginCounter++;
						 //if less than a minute has passed
						 if (System.currentTimeMillis()-incorrectLoginStartTime < 60000)
						 {
							 if (incorrectLoginCounter < 3)
							 {
								//ok
								 accountEnabled = true;
								 request.setAttribute("accountEnabled", "true");
								 request.setAttribute("loginsuccess", "false");
								 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
							     rd.forward(request, response);
							 }
							 else{
								 //disable
								 incorrectLoginDisableStartTime = System.currentTimeMillis();
								 accountEnabled = false;
								 request.setAttribute("accountEnabled", "false");
								 request.setAttribute("loginsuccess", "false");
								 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
							     rd.forward(request, response);
							 }
						 }
						 else {
							 accountEnabled = true;
							 request.setAttribute("accountEnabled", "true");
							 request.setAttribute("loginsuccess", "false");
							 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
						     rd.forward(request, response);
							 incorrectLoginStartTime = System.currentTimeMillis();
							 incorrectLoginCounter = 1;
						 }
						 				     
					     out.println ();
					 }
					 else if (result == 2)
					 {
						 request.setAttribute("providerfailure", "true");
						 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
					     rd.forward(request, response);
					     
					     out.println ();
					 }				
				 }
			 }
			 else if(!"true".equals(request.getAttribute("parentLockDisable")))
			 {
				 if(incorrectLoginDisableStartTime==0)
				 {
					 
				 }
				 request.setAttribute("accountEnabled", "false");
				 request.setAttribute("loginsuccess", "false");
				 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
			     rd.forward(request, response);
			 }
			 
		 }
		 else
		 {
			 //do not use locking strategy
			 if (result == 0){
				 session = request.getSession(true);
				 session.setAttribute("chatSession", chatSession);
				 session.setAttribute("username", username);
				 
				 response.sendRedirect("Chat.jsp");
			 }
			 else {
				 if (result == 1)
				 {
					 request.setAttribute("accountEnabled", "true");
					 request.setAttribute("loginsuccess", "false");
					 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
				     rd.forward(request, response);
				     
				     out.println ();
				 }
				 else if (result == 2)
				 {
					 request.setAttribute("providerfailure", "true");
					 RequestDispatcher rd = request.getRequestDispatcher("/LogIn.jsp");
				     rd.forward(request, response);
				     
				     out.println ();
				 }	
			 }
		 }
		 
		 
		 
		 
		 
		 
		 
		 
	}

}
