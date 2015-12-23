package tests.webtests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;

public class Driver 
{
	public static WebDriver driver;

    public static WebDriver getDriver()
    {
        if(driver==null || driver.toString().contains("null"))
        {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }

        return driver;
    }

    public void MaximiseWindowSize()
    {
        driver.manage().window().maximize();
    }


    public void NavigateToStartPage()
    {
        driver.navigate().to("http://localhost:8080/UnitAssignment/LogIn.jsp");
    }

    public void Quit()
    {
        driver.quit();
    }
}
