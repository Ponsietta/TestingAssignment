package tests.modelling_tests;
import java.util.concurrent.TimeUnit;
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
	
	public void loginValid()
	{
		loginSteps.GivenIAmAUserTryingToLogIn();
		loginSteps.WhenILoginUsingValidCredentials();
		chatSteps.ThenIShouldBeTakenToTheChatPage();
	}
	
	public void loginInvalid()
	{
		loginSteps.GivenIAmAUserTryingToLogIn();
		loginSteps.WhenILoginUsingInvalidCredentials();
		loginSteps.ThenIShouldSeeAnErrorMessage();
		loginSteps.ThenIShouldRemainOnTheLoginPage();
	}
	
	public void SendMessageValid()
	{
		loginSteps.GivenIAmALoggedInUser();
		chatSteps.GivenIAmOnTheChatPage();
		chatSteps.WhenISendAValidMessage();
		chatSteps.ThenTheMessageShouldAppearInMyChatWindow();
	}
	
	public void SendMessageTriggerParentalLock()
	{
		loginSteps.GivenIAmALoggedInUser();
		chatSteps.GivenIAmOnTheChatPage();
		chatSteps.WhenISendAMessageWith("Fudge");
		chatSteps.ThenAnErrorWillTellMeThatTheMessageWasNotSent();
		chatSteps.ThenTheMessageShouldNotAppearInMyTextWindow();
	}
	
	public void logout()
	{
		loginSteps.GivenIAmALoggedInUser();
		chatSteps.GivenIAmOnTheChatPage();
		loginSteps.WhenISendClickOnLogOut();
		loginSteps.ThenIShouldBeLoggedOut();
	}

	

	
}
