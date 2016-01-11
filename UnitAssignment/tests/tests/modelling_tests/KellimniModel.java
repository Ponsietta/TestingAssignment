package tests.modelling_tests;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.GreedyTester;
import net.sourceforge.czt.modeljunit.StopOnFailureListener;
import net.sourceforge.czt.modeljunit.VerboseListener;
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage;
import net.sourceforge.czt.modeljunit.coverage.StateCoverage;
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;
import net.sourceforge.czt.modeljunit.Action;

public class KellimniModel implements FsmModel
{
	private SeleniumAdapter sAdapter = new SeleniumAdapter();
	
	private KellimniModelStates pageState = KellimniModelStates.LOGGED_OUT;
	private KellimniAccountStates accountState = KellimniAccountStates.UNLOCKED;
	private KellimniMessagingState messagingState = null;
	
	int parentalLockTriggerCount = 0;
	int loginInvalidCount = 0;
	int messagesSentCount = 0;	
	
	int invalidLoginsDisabledTimer = 30000;
	int invalidLoginsTimerDuration = 60000;	
	int parentalLockDisabledTimer = 120000;	
	int sentMessagesLockTimer = 60000;
	
	long loginTimerStartedAt = 0;
	long firstSentMessageTimerStartedAt = 0;
	long timeDisabled = 0;
	
	@Action
    public void loginValid() 
	{
		sAdapter.loginValid();
		pageState = KellimniModelStates.LOGGED_IN;
		messagingState = KellimniMessagingState.ENABLED;
    }
	
    public boolean loginValidGuard() 
    {
    	if(accountState == KellimniAccountStates.INVALID_LOGIN_LOCKED)
    	{
    		if(System.currentTimeMillis()-timeDisabled>invalidLoginsDisabledTimer)
    		{
    			accountState = KellimniAccountStates.UNLOCKED;
    		}
    	}
    	else if(accountState == KellimniAccountStates.PARENTAL_LOCKED)
    	{
    		if(System.currentTimeMillis()-timeDisabled>parentalLockDisabledTimer)
    		{
    			accountState = KellimniAccountStates.UNLOCKED;
    		}
    	}
    	
    	return accountState == KellimniAccountStates.UNLOCKED && pageState == KellimniModelStates.LOGGED_OUT;
    }
    
    @Action
    public void loginInvalid(){
    	
    	sAdapter.loginInvalid();
    	//if the timer hasn't been set yet, set it.
    	if (loginTimerStartedAt == 0)
    		loginTimerStartedAt = System.currentTimeMillis();
    		
    	
    	loginInvalidCount++;
    	
    	if(loginInvalidCount==3 && System.currentTimeMillis()-loginTimerStartedAt<invalidLoginsTimerDuration)
    	{
    		loginTimerStartedAt = 0;
    		timeDisabled = System.currentTimeMillis();
    		accountState = KellimniAccountStates.INVALID_LOGIN_LOCKED;
    		loginInvalidCount = 0;
    	}
    }
    
    public boolean loginInvalidGuard(){
    	return pageState == KellimniModelStates.LOGGED_OUT;
    }
    
    @Action 
    public void sendMessageValid()
    {
    	if (firstSentMessageTimerStartedAt == 0)
    		firstSentMessageTimerStartedAt = System.currentTimeMillis();
    	
    	sAdapter.SendMessageValid();
    	messagesSentCount++;
    }
    
    public boolean sendMessageValidGuard(){
    	
    	if(messagesSentCount==10)
    	{
    		if(System.currentTimeMillis()-firstSentMessageTimerStartedAt<sentMessagesLockTimer)
    		{
    			messagingState = KellimniMessagingState.DISABLED;
    			return false;
    		}
    		else 
    		{
    			messagesSentCount=0;
    			messagingState = KellimniMessagingState.ENABLED;
    		}
    	}
    	
    	return pageState == KellimniModelStates.LOGGED_IN && messagingState == KellimniMessagingState.ENABLED;
    }
    
    @Action 
	public void sendMessageInvalid(){
    	sAdapter.SendMessageTriggerParentalLock();
    	parentalLockTriggerCount++;
    	
    	if(parentalLockTriggerCount>5)
    	{
    		sAdapter.logout();
    		parentalLockTriggerCount = 0;
    		pageState = KellimniModelStates.LOGGED_OUT;
    		accountState = KellimniAccountStates.PARENTAL_LOCKED;
    	}
    }
    
    public boolean sendMessageInvalidGuard(){
    	return pageState == KellimniModelStates.LOGGED_IN && messagingState == KellimniMessagingState.ENABLED;
    }
    
    @Action
    public void logOut(){
    	sAdapter.logout();	
    	pageState = KellimniModelStates.LOGGED_OUT;
    	messagesSentCount = 0;
    	parentalLockTriggerCount = 0;
    	firstSentMessageTimerStartedAt = 0;
    }
    
    public boolean logOutGuard(){
    	return pageState == KellimniModelStates.LOGGED_IN;
    }
    
	@Override
	public Object getState() 
	{		
		return pageState;
	}

	@Override
	public void reset(boolean arg0) 
	{				
		//TODO MUST LOG OUT IN RESET OTHERWISE THERE WILL BE PROBLEMS LOGGING IN
		if (arg0)
		{
			//TODO needs editing
			//sAdapter.reset();
			sAdapter.logout();
			pageState = KellimniModelStates.LOGGED_OUT;
			//CHANGE MADE
			accountState = KellimniAccountStates.UNLOCKED;
		}
	}
	
	public static void main(String[] argv) {
		KellimniModel model = new KellimniModel();
		GreedyTester tester = new GreedyTester(model);
		tester.buildGraph();
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        
        long startTime = System.currentTimeMillis();
        
        //do{
        	tester.generate(30);
        
        //}while(System.currentTimeMillis()-startTime<900000);
        
        tester.printCoverage();
	}

}
