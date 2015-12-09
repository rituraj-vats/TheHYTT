package com.bitscanvas.thehytt.model.response;

import com.bitscanvas.thehytt.dtos.AbstractDto;

public class ProfileVerificationResponse extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6029566411762652868L;

	private boolean isVerified;
	
	private String userId;

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
