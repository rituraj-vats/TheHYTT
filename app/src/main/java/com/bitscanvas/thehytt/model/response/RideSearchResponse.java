package com.bitscanvas.thehytt.model.response;

import com.bitscanvas.thehytt.dtos.AbstractDto;
import com.bitscanvas.thehytt.dtos.HopDto;
import com.bitscanvas.thehytt.dtos.PostDto;

import java.util.List;

public class RideSearchResponse extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8330159252088338782L;

	private List<HopDto> hopList;
	
	private List<PostDto> postList;

	public List<HopDto> getHopList() {
		return hopList;
	}

	public void setHopList(List<HopDto> hopList) {
		this.hopList = hopList;
	}

	public List<PostDto> getPostList() {
		return postList;
	}

	public void setPostList(List<PostDto> postList) {
		this.postList = postList;
	}
}
