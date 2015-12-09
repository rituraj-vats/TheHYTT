package com.bitscanvas.thehytt.model.response;

import java.io.Serializable;

public class HopResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4030452873284690231L;
	
	private String hopId;

	public String getHopId() {
		return hopId;
	}

	public void setHopId(String hopId) {
		this.hopId = hopId;
	}

}
