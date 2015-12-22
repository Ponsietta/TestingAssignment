package src.system;

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
	public void onMessageReceived(String text) 
	{
		// TODO Auto-generated method stub
		
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
