package com.bitscanvas.thehytt.model.response;

import java.io.Serializable;
import java.util.List;

public class JourneyStartResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336873378757035444L;
	
	private List<String> tripList;
	
	private List<String> hopList;
	
	//for success -1 , for failure - 0
	private Integer status;

	public List<String> getTripList() {
		return tripList;
	}

	public void setTripList(List<String> tripList) {
		this.tripList = tripList;
	}

	public List<String> getHopList() {
		return hopList;
	}

	public void setHopList(List<String> hopList) {
		this.hopList = hopList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
