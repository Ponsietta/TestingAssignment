package tests.unit_tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import src.system.ChatProvider;
import src.system.ChatSession;

public class InitSessionTest {
	
	ChatSession chatSession;
	ChatProvider provider;
	String username = "rebmar";
	String password = "enternow";
	String friend = "1";

	@Before
	public void setUp() throws Exception {
		provider = mock(ChatProvider.class);
		chatSession = new ChatSession(provider);
	}


	@After
	public void tearDown() throws Exception {
		provider = null;
		chatSession = null;
	}

	@Test
	public void initSuccess() {
		when(provider.connect(username, password)).thenReturn(0);
		int result = chatSession.initSession(username, password, friend);
		
		assertEquals(0, result);
	}
	
	@Test
	public void initInvalidLogin() {
		when(provider.connect(username, password)).thenReturn(1);
		int result = chatSession.initSession(username, password, friend);
		
		assertEquals(1, result);
	}
	
	@Test
	public void initTimeOut(){
		when(provider.connect(username,password)).thenAnswer(new Answer<String>() {
			   @Override
			   public String answer(InvocationOnMock invocation){
			     try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			     return "2";
			   }
			});
		
		int result = chatSession.initSession(username, password, friend);
		
		assertEquals(2, result);
	}

}
