package tests.stubs;

import src.system.ChatProvider;

public class ChatProviderStubSuccess implements ChatProvider 
{
	
	private String username = null;
	
	@Override
	public String getName() 
	{
		return this.getClass().getName();
	}

	@Override
	public int sendMessageTo(String friendID, String msg) 
	{
		if(friendID == null)
			return 1;
		
		if(username == null)
			return 2;
		
		return 0;
	}

	@Override
	public String onMessageReceived(String text) 
	{
		try 
		{
			Thread.sleep(2000);	
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return text;
	}

	@Override
	public int connect(String username, String password) 
	{

		if (username == null || username.trim() == "" || password == null || password.trim() == "" 
		|| !username.equals("rebmar") || !password.equals("enternow"))
		{
			return 1;
		}
		
		this.username = username;

		return 0;
	}

	@Override
	public int getMaxMessageLength() 
	{
		return 50;
	}

	
}
