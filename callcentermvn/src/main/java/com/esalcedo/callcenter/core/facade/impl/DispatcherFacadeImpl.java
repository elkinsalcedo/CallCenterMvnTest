package com.esalcedo.callcenter.core.facade.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.esalcedo.callcenter.core.enums.ServiceAgentStatus;
import com.esalcedo.callcenter.core.enums.ServiceAgentType;
import com.esalcedo.callcenter.core.facade.CallFacade;
import com.esalcedo.callcenter.core.facade.DispatcherFacade;
import com.esalcedo.callcenter.core.facade.ServiceAgentAvailableFacade;
import com.esalcedo.callcenter.core.facade.ServiceAgentFacade;
import com.esalcedo.callcenter.core.util.ServiceAgentAvailable;
import com.esalcedo.callcenter.core.util.Util;
import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * @author esalcedo
 *	
 */
public class DispatcherFacadeImpl implements DispatcherFacade, Runnable, Serializable{
	
	private static final long serialVersionUID = 1L;
	private CallFacade callFacadeImpl;
	private ServiceAgentFacadeImpl serviceAgentFacadeImpl;
	private LinkedBlockingQueue<ServiceAgentFacadeImpl> serviceAgentsQueue;
	private LinkedBlockingQueue<CallFacadeImpl> callQueue;
	private ServiceAgentAvailableFacade serviceAgentAvailableFacadeImpl;
	private boolean running;
	
	public DispatcherFacadeImpl() {
		serviceAgentsQueue = new LinkedBlockingQueue<ServiceAgentFacadeImpl>();
		callQueue = new LinkedBlockingQueue<CallFacadeImpl>();
		serviceAgentAvailableFacadeImpl = new ServiceAgentAvailable();
	}
	
	public void initializeServiceAgent(Integer operatorAgentNumbers, Integer supervisorAgentNumbers, Integer directorAgentNumber) {
		Integer totalAgent = 1;
		
		for (int ope = 1; ope <= operatorAgentNumbers; ope++) {
			//System.out.println("ope -> " +  ope);
			serviceAgentFacadeImpl = new ServiceAgentFacadeImpl(ope, ServiceAgentType.OPERATOR);
			serviceAgentsQueue.add(serviceAgentFacadeImpl);
			totalAgent++;
			serviceAgentFacadeImpl.start();
		}
		
		for (int sup = 1; sup <= supervisorAgentNumbers; sup++) {
			//System.out.println("sup -> " +  totalAgent);
			serviceAgentFacadeImpl = new ServiceAgentFacadeImpl(totalAgent, ServiceAgentType.SUPERVISOR);
			serviceAgentsQueue.add(serviceAgentFacadeImpl);
			totalAgent++;
			serviceAgentFacadeImpl.start();
		}
		
		for (int dir = 1; dir <= directorAgentNumber; dir++) {
			//System.out.println("dir -> " +  totalAgent);
			serviceAgentFacadeImpl = new ServiceAgentFacadeImpl(totalAgent, ServiceAgentType.DIRECTOR);
			serviceAgentsQueue.add(serviceAgentFacadeImpl);
			totalAgent++;
			serviceAgentFacadeImpl.start();
		}
		
		Util.log( "All Service agents are available... waiting call !!!");
	}
	
	
	public void initializeCalls(Integer maxCalls, Integer minDurationCall, Integer maxDurationCall) throws InterruptedException{
		for (int c = 1; c<= maxCalls; c++) {
			callFacadeImpl = new CallFacadeImpl();
			CallFacadeImpl call = callFacadeImpl.prepareCall(c, minDurationCall, maxDurationCall);
			callQueue.add(call);
		}
	}
	
	/***
	 * Dispatch a call to service agent available.
	 */
	private synchronized void dispatchCall() throws Exception {
		ServiceAgentFacadeImpl serviceAgentAvailable = serviceAgentAvailableFacadeImpl.findServiceAgentAvailable(this.serviceAgentsQueue);
		if (null != serviceAgentAvailable) {
			CallFacadeImpl currentCall = callQueue.poll();
			serviceAgentAvailable.setCallToQueue(currentCall);
			Util.log("Receiving call #" + currentCall.getNumber() + " with duration of " +  currentCall.getDuration() + " seconds. ");
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			if (null != callQueue && callQueue.size() > 0) {
				try {
					this.dispatchCall();
					TimeUnit.SECONDS.sleep(1);
				}catch(Exception ex) {
					Thread.interrupted();
				}
			}
		}
	}
	
	public synchronized void start() {
		running = true;
		new Thread( this ).start();
	}
	
	public synchronized void stop() {
        running = false;
    }
	
	public void dispatch() throws InterruptedException{
		Iterator<CallFacadeImpl> callItr = callQueue.iterator();
		while (callItr.hasNext()) {
			this.start();
		}
	}
}
