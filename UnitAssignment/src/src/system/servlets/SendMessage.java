package src.system.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import src.system.ChatSession;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int ViolationCounter = 0;
	private int MessagesSent = 0;
	private long MessagesSentStartTimer = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (MessagesSent == 0)
			MessagesSentStartTimer = System.currentTimeMillis();
		
		String message = request.getParameter("chatmessage");   
		 String lock = request.getParameter("parentalLock");
		 
		 HttpSession session = request.getSession();
		 ChatSession chatSession = (ChatSession) session.getAttribute("chatSession");
		 int result = -1; 
		 PrintWriter out = response.getWriter();
		 
		 MessagesSent++;
		 
		 if (System.currentTimeMillis()-MessagesSentStartTimer<60000)
		 {
			 if (MessagesSent <= 10)
			 {
				 session.setAttribute("parentLockDisable", "false");
				 result = chatSession.sendMessage(message, lock);
				 if (result == 4)
					 ViolationCounter++;
				 
				 if (ViolationCounter == 5){
					 ViolationCounter = 0;
					 session.setAttribute("parentLockDisable", "true");
					
					 result = 6;
				 }
				 
//				 session.setAttribute("parentLockDisable", "false");
				 out.println(result);
			 }
			 else {
				 //dont send it
				 out.println(7);
			 }
		 }
		 else {
			 MessagesSentStartTimer = System.currentTimeMillis();
			 MessagesSent = 1;
			 
			 session.setAttribute("parentLockDisable", "false");
			 result = chatSession.sendMessage(message, lock);
			 
			 if (result == 4)
				 ViolationCounter++;
			 
			 if (ViolationCounter == 5){
				 ViolationCounter = 0;
				 session.setAttribute("parentLockDisable", "true");
				 result = 6;
			 }
			 //session.setAttribute("parentLockDisable", "false");
			 out.println(result);
		 }
	
		 
	}

}
