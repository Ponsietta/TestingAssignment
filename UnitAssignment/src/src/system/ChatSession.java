package src.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class ChatSession 
{
	public List<ChatMessage> receivedMessages; 
	public ChatProvider provider;
	private String friendID;
	
	public ChatSession(ChatProvider provider)
	{
		this.receivedMessages = new ArrayList<ChatMessage>();
		this.provider = provider;
	}

	//This method is used to connect to a ChatProvider instance, using a particular username and password
	public int initSession(String username, String password, String s_friendID)
	{		
		this.friendID = s_friendID;		
 		
		//Attempts to execute the ChatProvider's connect() method
 		ExecutorService executor = Executors.newCachedThreadPool();
 		Callable<Object> task = new Callable<Object>() {
 		   public Object call() {
 		      return provider.connect(username, password);
 		   }
 		};
 		
 		//Attempts a 5 second wait for the provider to return
 		Future<Object> future = executor.submit(task);
 		try 
 		{
 			return (int)future.get(5, TimeUnit.SECONDS); 
 		} 
 		catch (Exception ex) 
 		{
 			return 2;
 		}
 		finally{
 			future.cancel(true);
 		}		
	}
	
	//This method is used to validate and send a message to the Chat Provider
	public int sendMessage(String text, String parentlock)
	{		
		boolean pLock;
        pLock = Boolean.parseBoolean(parentlock);

        //Text too short
        if (text == null || text.isEmpty())
            return 3;

        String lMessage = text.toLowerCase();

        //Text too long given the Chat Provider's restrictions
        if (text.length() > provider.getMaxMessageLength())
            return 2;

        
        //Check if the parent lock is violated
        if (pLock)
        {
            if (lMessage.contains("fudge") || lMessage.contains("yikes") || lMessage.contains("pudding"))
            {
                return 4;
            }
        }       
        
        int result=-1;
        
        //Attempts to call the Chat Provider's sendMessageTo() method using the chat message
        ExecutorService executor = Executors.newCachedThreadPool();
 		Callable<Object> task = new Callable<Object>() {
 		   public Object call() {
 		      return provider.sendMessageTo(friendID, text);
 		   }
 		};
 		
 		//Attempts to wait for 5 seconds for the Chat Provider's call to return
 		Future<Object> future = executor.submit(task);
 		try 
 		{
 			result = (int)future.get(5, TimeUnit.SECONDS); 
 		} 
 		catch (TimeoutException ex) 
 		{
 			return 1;
 		}
 		catch (Exception e) 
 		{
 		   return -1;
 		} 
 		finally{
 			future.cancel(true);
 		}
 		
 		if(result==0)
 			return 0;
 		else if(result==1)
 			return 5;
        
        return -1;	
	}
	
	//This method places any messages received from the remote party in an ArrayList
	public void onMessageRecieved(String text)
	{
		Date date = new Date();
		ChatMessage message = new ChatMessage(date, text);
		receivedMessages.add(message);
	}
	

}
