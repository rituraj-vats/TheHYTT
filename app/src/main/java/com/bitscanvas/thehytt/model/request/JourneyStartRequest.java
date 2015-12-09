package com.bitscanvas.thehytt.model.request;

import java.io.Serializable;
import java.util.List;

public class JourneyStartRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String postId;
	
	private List<String> tripIdList;
	
	private List<String> hopIdList;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public List<String> getTripIdList() {
		return tripIdList;
	}

	public void setTripIdList(List<String> tripIdList) {
		this.tripIdList = tripIdList;
	}

	public List<String> getHopIdList() {
		return hopIdList;
	}

	public void setHopIdList(List<String> hopIdList) {
		this.hopIdList = hopIdList;
	}

}
