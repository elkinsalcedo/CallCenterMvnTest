package com.esalcedo.callcenter.core.facade.impl;

import java.io.Serializable;
import java.util.List;
import com.esalcedo.callcenter.core.facade.CallFacade;
import com.esalcedo.callcenter.core.util.Util;

/**
 * @author esalcedo
 *
 */
public class CallFacadeImpl implements CallFacade, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer number;
	private Integer duration;
	
	public CallFacadeImpl() {
		
	}
	
	/**
	 * CONSTRUCTOR
	 * @param number
	 * @param duration
	 */
	public CallFacadeImpl( Integer number, Integer duration ) {
        this.number = number;
        this.duration = duration;
    }
	
	public CallFacadeImpl prepareCall(Integer callNumber, Integer minDurationCall, Integer maxDurationCall) {
		return new CallFacadeImpl(callNumber, Util.getRandom(minDurationCall, maxDurationCall + 1));
	}
	
	/**
	 * 
	 * @return duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * 
	 * @return number
	 */
	public Integer getNumber() {
		return number;
	}
	
}
