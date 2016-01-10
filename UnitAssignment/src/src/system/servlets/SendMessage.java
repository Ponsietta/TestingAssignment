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
		 String message = request.getParameter("chatmessage");   
		 String lock = request.getParameter("parentalLock");
		 
		 HttpSession session = request.getSession();
		 ChatSession chatSession = (ChatSession) session.getAttribute("chatSession");
		 int result = chatSession.sendMessage(message, lock);
		 PrintWriter out = response.getWriter();
			
		 if (result == 4)
			 ViolationCounter++;
		 
		 if (ViolationCounter == 5){
			 ViolationCounter = 0;
			 response.sendRedirect("LogIn.jsp");
			 return;
		 }
		 else {
			 out.println(result);
		 }
		 
	}

}
