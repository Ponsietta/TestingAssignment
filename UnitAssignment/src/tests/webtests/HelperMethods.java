package tests.webtests;
import java.util.Random;

public abstract class HelperMethods 
{
	public static String GetRandomString()
    {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        
        Random r = new Random();
        char[] text = new char[7];
        for (int i = 0; i < text.length; i++)
        {
            text[i] = chars.charAt(r.nextInt(chars.length()));
        }
        
        return new String(text);
    }
}
