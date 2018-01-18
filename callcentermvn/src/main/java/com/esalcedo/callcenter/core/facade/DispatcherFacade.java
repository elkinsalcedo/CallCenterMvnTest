/**
 * 
 */
package com.esalcedo.callcenter.core.facade;

/**
 * @author esalcedo
 *
 */
public interface DispatcherFacade {
	
	/**
	 * Initialize service agents with thread.
	 * @param operatorAgentNumbers
	 * @param supervisorAgentNumbers
	 * @param directorAgentNumber
	 */
	public void initializeServiceAgent(Integer operatorAgentNumbers, Integer supervisorAgentNumbers, Integer directorAgentNumber);

	/**
	 * Initialize calls with thread.
	 * @param maxCalls
	 * @param minDurationCall
	 * @param maxDurationCall
	 */
	public void initializeCalls(Integer maxCalls, Integer minDurationCall, Integer maxDurationCall) throws InterruptedException;
	
	public void start();
}
