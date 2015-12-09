package com.bitscanvas.thehytt.model.request;

import java.io.Serializable;

public class ConfirmRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2842818972505319631L;

	private String tripId;
	
	// For confirm - 1 , for deny -0
	private Integer opType;

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}
}
