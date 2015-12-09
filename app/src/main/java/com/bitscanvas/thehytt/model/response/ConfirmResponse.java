package com.bitscanvas.thehytt.model.response;

import com.bitscanvas.thehytt.dtos.AbstractDto;

public class ConfirmResponse extends AbstractDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4051518286343468505L;
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
