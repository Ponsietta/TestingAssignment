package tests.webtests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;

public class Driver 
{
	public static WebDriver driver;

    public static WebDriver getDriver()
    {
        if(driver==null)
            driver = new FirefoxDriver();

        return driver;
    }

    public void MaximiseWindowSize()
    {
        driver.manage().window().maximize();
    }


    public void NavigateToStartPage()
    {
        driver.navigate().to("http://localhost:35834/");
    }

    public void Quit()
    {
        driver.quit();
    }
}
