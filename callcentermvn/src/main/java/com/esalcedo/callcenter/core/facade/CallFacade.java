/**
 * 
 */
package com.esalcedo.callcenter.core.facade;

import com.esalcedo.callcenter.core.facade.impl.CallFacadeImpl;

/**
 * @author esalcedo
 *
 */
public interface CallFacade {
	
	/**
	 * Prepare a Call with a min and max duration.
	 * @param callNumber
	 * @param minDurationCall
	 * @param maxDurationCall
	 * @return Call
	 */
	
	public CallFacadeImpl prepareCall(Integer callNumber, Integer minDurationCall, Integer maxDurationCall);
}
