package com.bitscanvas.thehytt.model.request;

import com.bitscanvas.thehytt.dtos.*;

public class JoinRequest extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5965190902168083965L;

	/*
	 * In case of join - hop user id
	 * in case of pick up - post user id
	 */
	private String requestorId;
	
	/*
	 * in case of join - 0
	 * in case of pickup - 1
	 */
	private Integer requestType;
	
	private String hopId;
	
	private String postId;
	
	/*
	 * In case of join - post user id
	 * in case of pick up - hop user id
	 */
	private String recipientid;
	
	/*
	 * In case of join - price quoted by poster
	 * In case of pickup - price quoted by hopper
	 */
	private Integer price;

	public String getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(String requestorId) {
		this.requestorId = requestorId;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getRecipientid() {
		return recipientid;
	}

	public void setRecipientid(String recipientid) {
		this.recipientid = recipientid;
	}

	public String getHopId() {
		return hopId;
	}

	public void setHopId(String hopId) {
		this.hopId = hopId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
