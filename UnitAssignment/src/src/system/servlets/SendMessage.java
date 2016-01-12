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
		
		//If no messages have been sent, start the timer
		if (MessagesSent == 0)
			MessagesSentStartTimer = System.currentTimeMillis();
		
		//Get the message and parent lock value from the request
		String message = request.getParameter("chatmessage");   
		 String lock = request.getParameter("parentalLock");
		 
		 HttpSession session = request.getSession();
		 ChatSession chatSession = (ChatSession) session.getAttribute("chatSession");
		 int result = -1; 
		 PrintWriter out = response.getWriter();
		
		 ViolationCounter = (int)session.getAttribute("violationCounter");
		 
		 //If a minute has not yet passed since when the first message was sent
		 if (System.currentTimeMillis()-MessagesSentStartTimer<60000)
		 {
			 //If 10 or less messages have been sent
			 if (MessagesSent <= 10)
			 { 
				 session.setAttribute("parentLockDisable", "false");
				 result = chatSession.sendMessage(message, lock);
				 
				 //If the message violated the parent lock
				 if (result == 4)
					 ViolationCounter++;
				 
				 //Increase the amount of messages sent, only if the message was valid
				 if (result == 0)
					 MessagesSent++;
				 
				 //If the user has violated parent lock 5 times
				 if (ViolationCounter == 5){
					 
					 //Reset the counter
					 //Set appropriate variables
					 ViolationCounter = 0;
					 session.setAttribute("parentLockDisable", "true");
					 session.setAttribute("parentLockDisabledStartTime", System.currentTimeMillis());
					
					 //Return 6 which indicates that the parent lock violation has occurred and account must be diabled
					 result = 6;
				 }
				 
				 session.setAttribute("violationCounter", ViolationCounter);
				 out.println(result);
			 }
			 else {
				 //don't send the message and return 7 which indicates more than 10 msgs/min have been sent
				 out.println(7);
			 }
		 }
		 else { //If a minute has passed since the first message was sent
			 
			 //Reset timers and counters
			 MessagesSentStartTimer = System.currentTimeMillis();
			 MessagesSent = 1;
			 
			 session.setAttribute("parentLockDisable", "false");
			 result = chatSession.sendMessage(message, lock);
			 
			 //If message was a valid message, increment number of messages sent
			 if (result == 0)
				 MessagesSent++;
			 
			 //If message was invalid, increment number of parent lock violations made
			 if (result == 4)
				 ViolationCounter++;
			 
			 //If 5 parent lock violations were made
			 if (ViolationCounter == 5){
				 //Reset counters and set appropriate variables and locks
				 //Return result 6 to indicate a parent lock violation so as to disable the account
				 ViolationCounter = 0;
				 session.setAttribute("parentLockDisable", "true");
				 session.setAttribute("parentLockDisabledStartTime", System.currentTimeMillis());
				 result = 6;
			 }
			 
			 session.setAttribute("violationCounter", ViolationCounter);

			 out.println(result);
		 }
	
		 
	}

}
