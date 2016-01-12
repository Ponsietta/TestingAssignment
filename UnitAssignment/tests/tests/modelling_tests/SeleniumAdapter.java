package tests.modelling_tests;
import tests.web_tests.StepDefinitions.*;

/**
 * Acts as an interface between the model 
 * and the selenium tests created in the web 
 * testing section of the assignment.
 */

public class SeleniumAdapter {

	public CommonSteps commonSteps = new CommonSteps();
	public ChatSteps chatSteps = new ChatSteps();
	public LoginSteps loginSteps = new LoginSteps();
	
	public SeleniumAdapter()
	{
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
	}
	
	public void CheckMessageNotInChat()
	{
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
