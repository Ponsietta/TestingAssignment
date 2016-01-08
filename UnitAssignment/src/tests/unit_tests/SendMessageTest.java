package tests.unit_tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import src.system.ChatProvider;
import src.system.ChatSession;
import tests.stubs.ChatProviderStubTimeOut;

import static org.mockito.Mockito.*;

import java.util.concurrent.TimeoutException;

public class SendMessageTest {

	ChatSession chatSession;
	ChatProvider provider = mock(ChatProvider.class);

	@Before
	public void setUp() throws Exception {
		chatSession = new ChatSession(provider);
	}

	@After
	public void tearDown() throws Exception {
		chatSession = null;
	}
	
	@Test
	public void sendSuccess()
	{
		String text = "Fudge";
		String parentlock = "false";

		when(provider.getMaxMessageLength()).thenReturn(50);
		//ChatSession chatSession = new ChatSession(new ChatProviderStubSuccess());
		int result = chatSession.sendMessage(text, parentlock);
		assertEquals(0, result);
	}

	@Test
	public void sendTimeOut()
	{
		String message = "This is my message";
		String parentlock = "false";

		when(provider.getMaxMessageLength()).thenReturn(50);	
		
		ChatProvider chatProvider = new ChatProviderStubTimeOut();
		ChatSession chatSess = new ChatSession(chatProvider);
		
		int result = chatSess.sendMessage(message, parentlock);

		assertEquals(1, result);
	}

	@Test
	public void sendTooLong()
	{
		String message = "ThisIsALongMessageOver50CharsThisIsALongMessageOver50Chars" +
				"ThisIsALongMessageOver50CharsThisIsALongMessageOver50CharsThisIsALongMessageOver50Chars";
		String parentlock = "false";

//		ChatProvider chatProvider = new ChatProviderStubLongMessage();
//		ChatSession chatSession = new ChatSession(chatProvider);
		when(provider.getMaxMessageLength()).thenReturn(25);
		int result = chatSession.sendMessage(message, parentlock);
		assertEquals(2, result);

	}

	@Test
	public void sendTooShort()
	{
		String message = "";
		String parentlock = "false";
		
		//ChatProvider chatProvider = new ChatProviderStubSuccess();
		//ChatSession chatSession = new ChatSession(null);
		int result = chatSession.sendMessage(message, parentlock);
		assertEquals(3, result);

	}


	@Test
	public void sendParentLockedFudge()
	{
		String message = "Fudge";
		String parentlock = "true";
		
		when(provider.getMaxMessageLength()).thenReturn(50);
	//	ChatSession chatSession = new ChatSession(new ChatProviderStubSuccess());
		int result = chatSession.sendMessage(message, parentlock);
		assertEquals(4, result);

	}
	
	@Test
	public void sendParentLockedPudding()
	{
		String message = "this includes pudding";
		String parentlock = "true";
		
		when(provider.getMaxMessageLength()).thenReturn(50);
	//	ChatSession chatSession = new ChatSession(new ChatProviderStubSuccess());
		int result = chatSession.sendMessage(message, parentlock);
		assertEquals(4, result);

	}
	
	
	@Test
	public void sendParentLockedYikes()
	{
		String message = "yikes";
		String parentlock = "true";
		
		when(provider.getMaxMessageLength()).thenReturn(50);
	//	ChatSession chatSession = new ChatSession(new ChatProviderStubSuccess());
		int result = chatSession.sendMessage(message, parentlock);
		assertEquals(4, result);

	}
	

	@Test
	public void sendInvalidFriendID()
	{
		String message = "This is my message for invalid friend ID.";
		String parentlock = "true";

		chatSession.initSession("rebmar", "enternow", "a");
		when(provider.getMaxMessageLength()).thenReturn(50);
		when(provider.sendMessageTo("a", message)).thenReturn(1);
		
		int result = chatSession.sendMessage(message, parentlock);
		assertEquals(5, result);
	}


}
