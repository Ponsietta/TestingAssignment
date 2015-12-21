package src.system;

public class ChatProviderStubLongMessage implements ChatProvider {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sendMessageTo(String friendID, String msg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onMessageReceived(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int connect(String username, String password, String friendID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxMessageLength() {
		// TODO Auto-generated method stub
		return 25;
	}

	
}
