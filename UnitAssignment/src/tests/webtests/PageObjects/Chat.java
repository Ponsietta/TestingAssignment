package tests.webtests.PageObjects;

import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class Chat 
{
	private WebDriver driver;

    private String chatPageLinkID = "chat";
    private String messageBoxID = "chatmessage";
    private String sendMsgBtnID = "sendbutton";
    private String parentalLockBtnID = "parentalLock";

    private String message;

    public Chat(WebDriver driverIn)
    {
        driver = driverIn;
    }

    public void GoToChatPage()
    {
        driver.findElement(By.id(chatPageLinkID)).click();
    }

    public void SendValidMessage()
    {
        driver.findElement(By.id(messageBoxID)).sendKeys(message="Hey!");
        driver.findElement(By.id(sendMsgBtnID)).click();
    }

    public void SendParentalLockedMessage(String badWordIn)
    {
        WebElement locked = driver.findElement(By.id(parentalLockBtnID));
        if(!locked.isSelected())
            locked.click();
               
        driver.findElement(By.id(messageBoxID)).sendKeys(message=badWordIn);
        driver.findElement(By.id(sendMsgBtnID)).click();
    }

    public void CheckThatOnChatPage()
    {
    	driver.findElement(By.xpath("//div[@class='panel-heading'][contains(text(), 'RECENT CHAT HISTORY')]"));
        assertTrue(true);
    }

    public void CheckErrorMessage()
    {
        assertEquals(driver.switchTo().alert().getText(), "No naughty words please!");
        driver.switchTo().alert().dismiss();
    }

    public void CheckMessageSent()
    {
    	assertTrue(driver.findElements(By.xpath("//div[@class='media-body'][contains(text(),'"+message+"')]")).size() > 0);
    }

    public void CheckMessageNotSent()
    {
    	assertTrue(driver.findElements(By.xpath("//div[@class='media-body'][contains(text(),'"+message+"')]")).size()==0);
    }
}

