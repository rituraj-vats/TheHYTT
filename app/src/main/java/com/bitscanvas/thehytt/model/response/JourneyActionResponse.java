package com.bitscanvas.thehytt.model.response;

import java.io.Serializable;

public class JourneyActionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6821220510610608583L;

	private String tripId;

	private String hopId;

	private String postId;
	
	//use trip status enum
	private Integer status;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getHopId() {
		return hopId;
	}

	public void setHopId(String hopId) {
		this.hopId = hopId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
