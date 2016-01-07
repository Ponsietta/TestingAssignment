package tests.modelling_tests;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import tests.web_tests.StepDefinitions.*;

public class SeleniumAdapter {

	public CommonSteps commonSteps = new CommonSteps();
	public ChatSteps chatSteps = new ChatSteps();
	public LoginSteps loginSteps = new LoginSteps();
	
	public SeleniumAdapter()
	{
		commonSteps.StartUp();
	}
	
	//not sure if we need below method.. could be
	public void reset() 
	{
	      commonSteps.TearDown();
	      commonSteps.StartUp();
	}

	

	
}
