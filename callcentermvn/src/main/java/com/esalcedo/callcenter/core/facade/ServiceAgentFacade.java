package com.esalcedo.callcenter.core.facade;

import com.esalcedo.callcenter.core.enums.ServiceAgentType;
import com.esalcedo.callcenter.core.facade.impl.CallFacadeImpl;
import com.esalcedo.callcenter.core.facade.impl.ServiceAgentFacadeImpl;

/**
 * @author esalcedo
 *
 */
public interface ServiceAgentFacade {
	
	/**
	 * Allow setter a call to Queue.
	 * @param call
	 */
	public void setCallToQueue(CallFacadeImpl call);
	
	/*
	 * Start Service Agent thread.
	 */
	public void start();
	
	
}
