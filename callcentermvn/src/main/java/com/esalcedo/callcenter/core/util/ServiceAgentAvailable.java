/**
 * 
 */
package com.esalcedo.callcenter.core.util;
import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import com.esalcedo.callcenter.core.enums.ServiceAgentStatus;
import com.esalcedo.callcenter.core.enums.ServiceAgentType;
import com.esalcedo.callcenter.core.facade.ServiceAgentAvailableFacade;
import com.esalcedo.callcenter.core.facade.impl.ServiceAgentFacadeImpl;

/**
 * @author esalcedo
 *
 */
public class ServiceAgentAvailable implements ServiceAgentAvailableFacade, Serializable{

	private static final long serialVersionUID = 1L;

	public ServiceAgentAvailable() {
		
	}
	
	public ServiceAgentFacadeImpl findServiceAgentAvailable(LinkedBlockingQueue<ServiceAgentFacadeImpl> serviceAgentQueue) {
		ServiceAgentFacadeImpl serviceAgentResponse = null;
		ServiceAgentFacadeImpl serviceAgentOperator = this.findServiceAgentOperator(serviceAgentQueue);
		if (null == serviceAgentOperator) {
			Util.log("We're sorry, there is not OPERATORS available");
			ServiceAgentFacadeImpl serviceAgentSupervisor = this.findServiceAgentSupervisor(serviceAgentQueue);
			if (null == serviceAgentSupervisor) {
				Util.log("We're sorry, there is not SUPERVISOR available");
				ServiceAgentFacadeImpl serviceAgentDirector = this.findServiceAgentDirector(serviceAgentQueue);
				if (null == serviceAgentDirector) {
					Util.log("We're sorry, there is not service agents available at the moment.... music, please wait.");
				}else {
					Util.log("Wow, we founded a DIRECTOR available !!! ");
					serviceAgentResponse = serviceAgentDirector;
				}
			}else {
				Util.log("Wow, we founded a SUPERVISOR available !!! ");
				serviceAgentResponse = serviceAgentSupervisor;
			}
		}else {
			serviceAgentResponse = serviceAgentOperator;
		}
		
		return serviceAgentResponse;
	}
	
	/**
	 * Find a service agent available from type OPERATOR.
	 * @param serviceAgentQueue
	 * @return ServiceAgentFacadeImpl
	 */
	private ServiceAgentFacadeImpl findServiceAgentOperator(LinkedBlockingQueue<ServiceAgentFacadeImpl> serviceAgentQueue) {
		ServiceAgentFacadeImpl serviceAgentResponse = null;
		Iterator<ServiceAgentFacadeImpl> serviceAgentIterator = serviceAgentQueue.iterator();
		while (serviceAgentIterator.hasNext()) {
			ServiceAgentFacadeImpl serviceAgent = serviceAgentIterator.next();
        		if (serviceAgent.getType().equals(ServiceAgentType.OPERATOR) && serviceAgent.getStatus().equals(ServiceAgentStatus.FREE)) {
        			serviceAgentResponse = serviceAgent;
        			break;
        		}
		}
		return serviceAgentResponse;
	}
	
	
	/**
	 * Find a service agent available from type SUPERVISOR.
	 * @param serviceAgentQueue
	 * @return ServiceAgentFacadeImpl
	 */
	private ServiceAgentFacadeImpl findServiceAgentSupervisor(LinkedBlockingQueue<ServiceAgentFacadeImpl> serviceAgentQueue) {
		ServiceAgentFacadeImpl serviceAgentResponse = null;
		Iterator<ServiceAgentFacadeImpl> serviceAgentIterator = serviceAgentQueue.iterator();
		while (serviceAgentIterator.hasNext()) {
			ServiceAgentFacadeImpl serviceAgent = serviceAgentIterator.next();
        		if (serviceAgent.getType().equals(ServiceAgentType.SUPERVISOR) && serviceAgent.getStatus().equals(ServiceAgentStatus.FREE)) {
        			serviceAgentResponse = serviceAgent;
        			break;
        		}
		}
		return serviceAgentResponse;
	}
	
	/**
	 * Find a service agent available from type DIRECTOR.
	 * @param serviceAgentQueue
	 * @return ServiceAgentFacadeImpl
	 */
	private ServiceAgentFacadeImpl findServiceAgentDirector(LinkedBlockingQueue<ServiceAgentFacadeImpl> serviceAgentQueue) {
		ServiceAgentFacadeImpl serviceAgentResponse = null;
		Iterator<ServiceAgentFacadeImpl> serviceAgentIterator = serviceAgentQueue.iterator();
		while (serviceAgentIterator.hasNext()) {
			ServiceAgentFacadeImpl serviceAgent = serviceAgentIterator.next();
        		if (serviceAgent.getType().equals(ServiceAgentType.DIRECTOR) && serviceAgent.getStatus().equals(ServiceAgentStatus.FREE)) {
        			serviceAgentResponse = serviceAgent;
        			break;
        		}
		}
		return serviceAgentResponse;
	}
}
