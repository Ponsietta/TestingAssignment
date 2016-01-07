package tests.modelling_tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import tests.web_tests.StepDefinitions.ChatSteps;
import tests.web_tests.StepDefinitions.LoginSteps;

public class SeleniumAdapter {

	//public static WebDriver driver;
	public ChatSteps chatSteps = new ChatSteps();
	public LoginSteps loginSteps = new LoginSteps();
	

	
}
