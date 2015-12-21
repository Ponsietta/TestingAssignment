package src.system;


import java.util.Date;

public class ChatMessage 
{
	public String timestamp;
	public String content;
	
	public ChatMessage(Date date, String content)
	{
		timestamp = date.toString();
		this.content = content;
	}
}
