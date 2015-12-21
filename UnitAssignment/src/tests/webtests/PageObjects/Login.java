package tests.webtests.PageObjects;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import tests.webtests.*;

public class Login 
{
	WebDriver driver;

    private String loginPageLinkID = "loginLink";
    private String logoutBtnID = "logoutButton";
    private String usernameID = "Email";
    private String passwordID = "Password";
    private String loginBtnID = "login";
    private String loggedInUserID = "loginCredential";

    public Login(WebDriver driverIn)
    { 
        driver = driverIn;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void GoToLoginPage()
    {
        driver.findElement(By.id(loginPageLinkID)).click();
    }

    public void LoginToChatValid()
    {
        driver.findElement(By.id(usernameID)).sendKeys("marziacutajar@live.co.uk");
        driver.findElement(By.id(passwordID)).sendKeys("Marzia_admin2");
        driver.findElement(By.id(loginBtnID)).click();
    }

    public void LoginToChatInvalid()
    {
        driver.findElement(By.id(usernameID)).sendKeys(HelperMethods.GetRandomString());
        driver.findElement(By.id(passwordID)).sendKeys(HelperMethods.GetRandomString());
        driver.findElement(By.id(loginBtnID)).click();
    }

    public void LogOut()
    {
        driver.findElement(By.id(logoutBtnID)).click();
    }

    public boolean CheckLoggedIn()
    {
        try
        {
            driver.findElement(By.id(loggedInUserID));
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

    }

    public void CheckLoggedOut()
    {
        assertTrue(driver.findElement(By.id(loginPageLinkID)).isDisplayed());
    }
    public void CheckErrorMessage()
    {
    	assertTrue(driver.findElement(By.xpath("//div[contains(@class,'validation-summary-errors')]")).isDisplayed());
    }

    public void CheckThatOnLoginPage()
    {
    	assertTrue(driver.findElement(By.xpath("//div/h2")).getText().contains("Initiate Chat Session"));
    }
}
