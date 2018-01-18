package com.esalcedo.callcenter.core.facade.impl;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.Soundbank;

import com.esalcedo.callcenter.core.enums.ServiceAgentStatus;
import com.esalcedo.callcenter.core.enums.ServiceAgentType;
import com.esalcedo.callcenter.core.facade.ServiceAgentFacade;
import com.esalcedo.callcenter.core.util.Util;

/**
 * @author esalcedo
 *
 */
public class ServiceAgentFacadeImpl implements ServiceAgentFacade, Runnable, Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private ServiceAgentStatus status;
	private ServiceAgentType type;
	private LinkedBlockingQueue<CallFacadeImpl> queueCall;
	private boolean running;
	
	/**
	 * CONSTRUCT
	 * @param id
	 * @param type
	 */
	public ServiceAgentFacadeImpl(Integer id, ServiceAgentType type) {
		this.id = id;
		this.status = ServiceAgentStatus.FREE;
		this.type = type;
		this.queueCall = new LinkedBlockingQueue<CallFacadeImpl>();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			if ((null != queueCall && queueCall.size() > 0) && status == ServiceAgentStatus.FREE) {
				CallFacadeImpl currentCall = this.queueCall.poll();
				if (null != currentCall) {
					Util.log( "Service agent # "+ this.id +" is answering call #[" + currentCall.getNumber() + "] in "+ currentCall.getDuration() + " seconds" );
					this.status = ServiceAgentStatus.IN_A_CALL;
					try {
						TimeUnit.SECONDS.sleep(currentCall.getDuration());
						Util.log("Service agent #"+ this.id +" finished call - (hanging up) !!! " );
						this.status = ServiceAgentStatus.FREE;
					} catch (InterruptedException e) {
						continue;
					}
				}
			}
		}
	}
	
	public void setStatus(ServiceAgentStatus status) {
		this.status = status;
	}

	public void setType(ServiceAgentType type) {
		this.type = type;
	}	
	
	public ServiceAgentStatus getStatus() {
		return status;
	}
	public ServiceAgentType getType() {
		return type;
	}
	
	public void setCallToQueue(CallFacadeImpl call) {
		this.queueCall.add(call);
	}
	
	public synchronized void start() {
		running = true;
        new Thread( this ).start();
    }
	
	public synchronized void stop()
    {
        running = false;
    }
	
}
