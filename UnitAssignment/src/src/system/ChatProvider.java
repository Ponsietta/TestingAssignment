package src.system;


public interface ChatProvider 
{
	//public chatProvider(String username, String password);
	
	public String getName();
	
	public int sendMessageTo(String friendID, String msg);
	
	public void onMessageReceived(String text);
	
    int getMaxMessageLength();

	public int connect(String username, String password, String s_friendID);
}
