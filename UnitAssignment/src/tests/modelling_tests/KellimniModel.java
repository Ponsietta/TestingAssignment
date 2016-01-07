package tests.modelling_tests;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.Action;

public class KellimniModel implements FsmModel
{
	private SeleniumAdapter mAdapter = new SeleniumAdapter();
	
	private KellimniModelStates currState = KellimniModelStates.SHOW_LOGIN_PAGE;
	
	@Action
    public void loginValid() 
	{

    }
    public boolean loginValidGuard() 
    {
    	//for now
    	return false;
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
			mAdapter.reset();
	}

}
