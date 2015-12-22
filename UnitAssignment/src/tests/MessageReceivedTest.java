package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.system.ChatProvider;
import src.system.ChatSession;

public class MessageReceivedTest {

	ChatSession chatSession;
	ChatProvider provider;

	@Before
	public void setUp() throws Exception {
		provider = mock(ChatProvider.class);
		chatSession = new ChatSession(provider);
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
