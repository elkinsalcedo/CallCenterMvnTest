/**
 * 
 */
package com.esalcedo.callcenter.core.facade;

import java.util.concurrent.LinkedBlockingQueue;

import com.esalcedo.callcenter.core.facade.impl.ServiceAgentFacadeImpl;

/**
 * @author esalcedo
 *
 */
public interface ServiceAgentAvailableFacade {
	/***
	 * Find a service agent available.
	 * @param serviceAgentQueue
	 * @return ServiceAgentFacadeImpl
	 */
	public ServiceAgentFacadeImpl findServiceAgentAvailable(LinkedBlockingQueue<ServiceAgentFacadeImpl> serviceAgentQueue);
}
