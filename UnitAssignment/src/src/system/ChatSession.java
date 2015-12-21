package src.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatSession 
{
	public List<ChatMessage> receivedMessages; 
	private ChatProvider provider;
	private int friendID;
	
	public ChatSession(ChatProvider provider)
	{
		this.receivedMessages = new ArrayList<ChatMessage>();
		this.provider = provider;
	}

	public int initSession(String username, String password, String s_friendID)
	{
		//this can be passed in and can change
		
		int result = provider.connect(username, password, s_friendID);
		
//		int i_friendID;
//
//		try
//		{
//			i_friendID = Integer.parseInt(s_friendID);
//		}
//		catch (Exception e)
//		{
//			i_friendID = -1;
//		}
//
//		this.friendID = i_friendID;
		
        return result;
	}
	
	public int sendMessage(String text, String parentlock)
	{
		//String censoredText = checkNaughtyWords(text);
		
		if (friendID < 0){
			return 5;
		}
		
		boolean pLock;
        if (parentlock == null)
            pLock = false;
        else
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
        
        provider.onMessageReceived(text);
        return 0;
	
	}
	
	public void onMessageRecieved(String text)
	{
		Date date = new Date();
		ChatMessage message = new ChatMessage(date, text);
		receivedMessages.add(message);
	}
	

}
