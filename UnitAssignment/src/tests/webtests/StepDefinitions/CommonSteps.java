package tests.webtests.StepDefinitions;
import tests.webtests.*;
import cucumber.api.java.Before;
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

    //@After
    public void TearDown()
    {
        commonDriver.Quit();
    }
}
