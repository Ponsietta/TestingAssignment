package tests.web_tests.StepDefinitions;
import cucumber.api.java.Before;
import tests.web_tests.*;
import cucumber.api.java.After;

public class CommonSteps 
{
	private Driver commonDriver;

    @Before
    public void StartUp()
    {
        Driver.getDriver();
        commonDriver = new Driver();
        commonDriver.MaximiseWindowSize();
        commonDriver.NavigateToStartPage();
    }

    @After
    public void TearDown()
    {
        commonDriver.Quit();
    }
}
