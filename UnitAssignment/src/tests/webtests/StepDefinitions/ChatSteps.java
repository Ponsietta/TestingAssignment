package tests.webtests.StepDefinitions;

import tests.webtests.PageObjects.*;
import tests.webtests.*;
import cucumber.api.java.en.*;

public class ChatSteps 
{
	private Chat chat;

    public ChatSteps()
    {
        chat = new Chat(Driver.getDriver());
    }

    @Given("^I am on the chat page$")
    public void GivenIAmOnTheChatPage()
    {
        chat.GoToChatPage();
    }
    
    @When("^I send a valid message$")
    public void WhenISendAValidMessage()
    {
        chat.SendValidMessage();
    }

    @When("^I send a message with '(.*)'$")
    public void WhenISendAMessageWith(String p0)
    {
        chat.SendParentalLockedMessage(p0);
    }
    
    @Then("^I should be taken to the chat page$")
    public void ThenIShouldBeTakenToTheChatPage()
    {
        chat.CheckThatOnChatPage();
    }
    
    @Then("^the message should appear in my chat window$")
    public void ThenTheMessageShouldAppearInMyChatWindow()
    {
        chat.CheckMessageSent();
    }
    
    @Then("^an error will tell me that the message was not sent$")
    public void ThenAnErrorWillTellMeThatTheMessageWasNotSent()
    {
        chat.CheckErrorMessage();
    }
    
    @Then("^the message should not appear in my text window$")
    public void ThenTheMessageShouldNotAppearInMyTextWindow()
    {
        chat.CheckMessageNotSent();
    }

}
