package com.bitscanvas.thehytt.model.request;

import java.io.Serializable;

public class JourneyActionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tripId;

	private String hopId;

	private String postId;
	
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


}
