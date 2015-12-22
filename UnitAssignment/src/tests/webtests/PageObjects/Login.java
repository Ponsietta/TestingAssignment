package tests.webtests.PageObjects;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import tests.webtests.*;

public class Login 
{
	WebDriver driver;

    private String loginPageLinkID = "loginLink";
    private String logoutBtnID = "logoutButton";
    private String usernameID = "username";
    private String passwordID = "password";
    private String loginBtnID = "login";
    private String loggedInUserID = "loginCredential";

    public Login(WebDriver driverIn)
    { 
        driver = driverIn;
    }

    public void GoToLoginPage()
    {
        driver.findElement(By.id(loginPageLinkID)).click();
    }

    public void LoginToChatValid()
    {
        driver.findElement(By.id(usernameID)).sendKeys("rebmar");
        driver.findElement(By.id(passwordID)).sendKeys("enternow");
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
            //driver.findElement(By.id(loggedInUserID));
        	driver.findElement(By.id("logoutForm"));
            return true;
        }
        catch (org.openqa.selenium.NoSuchElementException e)
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
    	assertTrue(driver.findElement(By.xpath("//div[contains(@id,'loginerror')]")).isDisplayed());
    }

    public void CheckThatOnLoginPage()
    {
    	assertTrue(driver.findElement(By.xpath("//div/h2")).getText().contains("Log In"));
    }
}
