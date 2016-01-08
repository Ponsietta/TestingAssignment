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
	
	private KellimniModelStates pageState = KellimniModelStates.SHOW_LOGIN_PAGE;
	private KellimniModelStates accountState = KellimniModelStates.UNLOCKED;
	
	int parentalLockTriggerCount = 0;
	int loginInvalidCount = 0;
	int messagesSentCount = 0;	
	
	int loginTimerDisable = 30000;
	int loginTimerDuration = 60000;
	long loginTimerStartedAt = 0;
	long timeDisabled = 0;
	
	@Action
    public void loginValid() 
	{
		sAdapter.loginValid();
		pageState = KellimniModelStates.SHOW_CHAT_PAGE;
    }
	
    public boolean loginValidGuard() 
    {
    	if(accountState == KellimniModelStates.LOCKED && System.currentTimeMillis()-timeDisabled<30000)
    		accountState = KellimniModelStates.UNLOCKED;
    	return accountState == KellimniModelStates.UNLOCKED && pageState == KellimniModelStates.SHOW_LOGIN_PAGE;
    }
    
    @Action
    public void loginInvalid(){
    	
    	sAdapter.loginInvalid();
    	//if the timer hasnt been set yet, set it.
    	if (loginTimerStartedAt == 0)
    		loginTimerStartedAt = System.currentTimeMillis();
    		
    	
    	loginInvalidCount++;
    	
    	if(loginInvalidCount==3 && System.currentTimeMillis()-loginTimerStartedAt<60000)
    	{
    		loginTimerStartedAt = 0;
    		timeDisabled = System.currentTimeMillis();
    		accountState = KellimniModelStates.LOCKED;
    		loginInvalidCount = 0;
    	}
    }
    
    public boolean loginInvalidGuard(){
    	return pageState == KellimniModelStates.SHOW_LOGIN_PAGE;
    }
    
    @Action 
    public void sendMessageValid(){
    	sAdapter.SendMessageValid();
    	pageState = KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    public boolean sendMessageValidGuard(){
    	return pageState == KellimniModelStates.SHOW_CHAT_PAGE && messagesSentCount<=10;
    }
    
    @Action 
	public void sendMessageInvalid(){
    	sAdapter.SendMessageTriggerParentalLock();
    	parentalLockTriggerCount++;
    	
    	if(parentalLockTriggerCount>5)
    	{
    		System.out.println("Lock that bitch!");
    		sAdapter.logout();
    		parentalLockTriggerCount =0;
    		pageState = KellimniModelStates.SHOW_LOGIN_PAGE;
    		accountState = KellimniModelStates.LOCKED;
    	}
    	pageState = KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    public boolean sendMessageInvalidGuard(){
    	return pageState == KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    @Action
    public void logOut(){
    	sAdapter.logout();	
    	pageState = KellimniModelStates.SHOW_LOGIN_PAGE;
    	messagesSentCount = 0;
    	parentalLockTriggerCount = 0;
    }
    
    public boolean logOutGuard(){
    	return pageState == KellimniModelStates.SHOW_CHAT_PAGE;
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
			pageState = KellimniModelStates.SHOW_LOGIN_PAGE;
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
