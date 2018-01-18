package com.esalcedo.callcentermvn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import com.esalcedo.callcenter.core.facade.impl.DispatcherFacadeImpl;

/**
 * Unit test for simple App.
 */
public class CallCenterMvnAppTest {
	
	public static final Integer MAX_CALLS = 10;
	private static final Integer minDurationCall = 5;
	private static final Integer maxDurationCall = 10;
	
	private DispatcherFacadeImpl dispatcherFacadeImpl;

	@Test
	public void dispatcherCallsByServicesAgent() throws InterruptedException{
		Integer operatorAgentNumbers = 7;
		Integer supervisorAgentNumbers = 2;
		Integer directorAgentNumber = 1;
		
		dispatcherFacadeImpl = new DispatcherFacadeImpl();
		dispatcherFacadeImpl.initializeServiceAgent(operatorAgentNumbers, supervisorAgentNumbers, directorAgentNumber);
		dispatcherFacadeImpl.start();
		TimeUnit.SECONDS.sleep(1);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcherFacadeImpl);
        TimeUnit.SECONDS.sleep(1);
		dispatcherFacadeImpl.initializeCalls(MAX_CALLS, minDurationCall, maxDurationCall);
		executorService.awaitTermination(MAX_CALLS * 3, TimeUnit.SECONDS);
	}

}
