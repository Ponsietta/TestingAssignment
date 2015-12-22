package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.system.ChatProvider;
import src.system.ChatSession;

public class InitSessionTest {
	
	ChatSession chatSession;
	ChatProvider provider;

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
		String username = "reb";
		String password = "pass";
		String friend = "1";
		
		when(provider.connect(username, password)).thenReturn(0);
		int result = chatSession.initSession("reb", "pass", "1");
		
		assertEquals(0, result);
		
	}
	
	@Test
	public void initInvalidLogin() {
		
		String username = "reb";
		String password = "pass";
		String friend = "1";
		
		when(provider.connect(username, password)).thenReturn(1);
		int result = chatSession.initSession(username, password, friend);
		
		assertEquals(1, result);
		
	}
	
	@Test
	public void initTimeOut(){
		String username = "reb";
		String password = "pass";
		String friend = "1";
		
		when(provider.connect(username, password)).thenReturn(2);
		int result = chatSession.initSession("reb", "pass", "1");
		
		assertEquals(2, result);
	}

}
