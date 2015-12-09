package com.bitscanvas.thehytt.model.response;

public class GcmResults {

	private String message_id;
	
	private String registration_id;
	
	//http://developer.android.com/google/gcm/server-ref.html#table11
	private String error;

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
