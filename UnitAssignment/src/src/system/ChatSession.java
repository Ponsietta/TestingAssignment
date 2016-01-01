package src.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

	public int initSession(String username, String password, String s_friendID)
	{		
		this.friendID = s_friendID;		
 		
 		ExecutorService executor = Executors.newCachedThreadPool();
 		Callable<Object> task = new Callable<Object>() {
 		   public Object call() {
 		      return provider.connect(username, password);
 		   }
 		};
 		
 		Future<Object> future = executor.submit(task);
 		try 
 		{
 			return (int)future.get(5, TimeUnit.SECONDS); 
 		} 
 		catch (TimeoutException ex) 
 		{
 			return 2;
 		}
 		catch (InterruptedException e) {
 		   // handle the interrupts
 		} catch (ExecutionException e) {
 		   // handle other exceptions
 		}
 		finally{
 			future.cancel(true);
 		}		
		
 		return 0;
	}
	
	public int sendMessage(String text, String parentlock)
	{		
		boolean pLock;
        pLock = Boolean.parseBoolean(parentlock);

        //Text too short
        if (text == null || text.isEmpty())
            return 3;

        String lMessage = text.toLowerCase();

        //Text too long
        if (text.length() > provider.getMaxMessageLength())
            return 2;

        
        if (pLock)
        {
            if (lMessage.contains("fudge") || lMessage.contains("yikes") || lMessage.contains("pudding"))
            {
                return 4;
            }
        }       
        
        int result=-1;
        
        ExecutorService executor = Executors.newCachedThreadPool();
 		Callable<Object> task = new Callable<Object>() {
 		   public Object call() {
 		      return provider.sendMessageTo(friendID, text);
 		   }
 		};
 		
 		Future<Object> future = executor.submit(task);
 		try 
 		{
 			result = (int)future.get(5, TimeUnit.SECONDS); 
 		} 
 		catch (TimeoutException ex) 
 		{
 			return 1;
 		}
 		catch (InterruptedException e) 
 		{
 		   return -1;
 		} 
 		catch (ExecutionException e) 
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
	
	public void onMessageRecieved(String text)
	{
		Date date = new Date();
		ChatMessage message = new ChatMessage(date, text);
		receivedMessages.add(message);
	}
	

}
