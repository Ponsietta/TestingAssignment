package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.system.ChatProviderStubSuccess;
import src.system.ChatSession;

public class MessageReceivedTest {

	ChatSession chatSession;

	@Before
	public void setUp() throws Exception {
		chatSession = new ChatSession(new ChatProviderStubSuccess());
	}

	@After
	public void tearDown() throws Exception {
		chatSession = null;
	}
	
	@Test
	public void BeforeMessageReceived() {
		int size = chatSession.receivedMessages.size();
		
		assertEquals(0, size);
	}
	
	@Test
	public void AfterMessageReceived() {
		int size = chatSession.receivedMessages.size();
		chatSession.onMessageRecieved("this is the message");
		int sizeAfter = chatSession.receivedMessages.size();
		
		assertEquals(size+1, sizeAfter);
	}

}
