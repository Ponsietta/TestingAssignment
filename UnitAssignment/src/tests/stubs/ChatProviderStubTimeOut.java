package tests.stubs;

import src.system.ChatProvider;

public class ChatProviderStubTimeOut implements ChatProvider {

private String username = null;
	
	@Override
	public String getName() 
	{
		return this.getClass().getName();
	}

	@Override
	public int sendMessageTo(String friendID, String msg) 
	{
		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {}
	
		if(friendID == null)
			return 1;
		
		if(username == null)
			return 2;
		
		return 0;
	}

	@Override
	public String onMessageReceived(String text) 
	{
		return text;
	}

	@Override
	public int connect(String username, String password) 
	{

//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
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
