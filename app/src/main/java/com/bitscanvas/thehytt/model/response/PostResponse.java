package com.bitscanvas.thehytt.model.response;

import java.io.Serializable;

public class PostResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8967378528371008413L;
	
	private String postId;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

}
