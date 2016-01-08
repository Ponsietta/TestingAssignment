package tests.modelling_tests;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.RandomTester;
import net.sourceforge.czt.modeljunit.StopOnFailureListener;
import net.sourceforge.czt.modeljunit.VerboseListener;
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage;
import net.sourceforge.czt.modeljunit.coverage.StateCoverage;
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;
import net.sourceforge.czt.modeljunit.Action;

public class KellimniModel implements FsmModel
{
	private SeleniumAdapter sAdapter = new SeleniumAdapter();
	
	private KellimniModelStates currState = KellimniModelStates.SHOW_LOGIN_PAGE;
	
	int parentalLockTriggerCount = 0;
	int loginInvalidCount = 0;
	int messagesSentCount = 0;
	
	@Action
    public void loginValid() 
	{
		sAdapter.loginValid();
		currState = KellimniModelStates.SHOW_CHAT_PAGE;
    }
	
    public boolean loginValidGuard() 
    {
    	return currState == KellimniModelStates.SHOW_LOGIN_PAGE;
    }
    
    @Action
    public void loginInvalid(){
    	sAdapter.loginInvalid();
    	//currState = KellimniModelStates.SHOW_LOGIN_PAGE;
    }
    
    public boolean loginInvalidGuard(){
    	return currState == KellimniModelStates.SHOW_LOGIN_PAGE;
    }
    
    @Action 
    public void sendMessageValid(){
    	sAdapter.SendMessageValid();
    	currState = KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    public boolean sendMessageValidGuard(){
    	return currState == KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    @Action 
	public void sendMessageInvalid(){
    	sAdapter.SendMessageTriggerParentalLock();
    	currState = KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    public boolean sendMessageInvalidGuard(){
    	return currState == KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
    @Action
    public void logOut(){
    	sAdapter.logout();	
    	currState = KellimniModelStates.SHOW_LOGIN_PAGE;
    }
    
    public boolean logOutGuard(){
    	return currState == KellimniModelStates.LOCKED || currState == KellimniModelStates.SHOW_CHAT_PAGE;
    }
    
	@Override
	public Object getState() 
	{		
		return currState;
	}

	@Override
	public void reset(boolean arg0) 
	{		
		currState = KellimniModelStates.SHOW_LOGIN_PAGE;
		
		if (arg0)
			sAdapter.reset();
	}
	
	public static void main(String[] argv) {
		KellimniModel model = new KellimniModel();
		RandomTester tester = new RandomTester(model);
		tester.buildGraph();
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(20);
        tester.printCoverage();
	}

}
