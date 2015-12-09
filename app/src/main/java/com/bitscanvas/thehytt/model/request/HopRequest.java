package com.bitscanvas.thehytt.model.request;

import java.io.Serializable;

public class HopRequest implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5681448497071082943L;

	private String userId;
	
	private String cscId;
	
	private double srcLat;
	
	private double srcLng;
	
	private String srcAddress;
	
	private String srcPlaceId;
	
	private double destLat;
	
	private double destLng;
	
	private String destAddress;
	
	private String destPlaceId;
	
	private Integer pps;//TM
		
	private Long travelTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCscId() {
		return cscId;
	}

	public void setCscId(String cscId) {
		this.cscId = cscId;
	}

	public double getSrcLat() {
		return srcLat;
	}

	public void setSrcLat(double srcLat) {
		this.srcLat = srcLat;
	}

	public double getSrcLng() {
		return srcLng;
	}

	public void setSrcLng(double srcLng) {
		this.srcLng = srcLng;
	}

	public String getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(String srcAddress) {
		this.srcAddress = srcAddress;
	}

	public double getDestLat() {
		return destLat;
	}

	public void setDestLat(double destLat) {
		this.destLat = destLat;
	}

	public double getDestLng() {
		return destLng;
	}

	public void setDestLng(double destLng) {
		this.destLng = destLng;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public Integer getPps() {
		return pps;
	}

	public void setPps(Integer pps) {
		this.pps = pps;
	}

	public Long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Long travelTime) {
		this.travelTime = travelTime;
	}

	public String getSrcPlaceId() {
		return srcPlaceId;
	}

	public void setSrcPlaceId(String srcPlaceId) {
		this.srcPlaceId = srcPlaceId;
	}

	public String getDestPlaceId() {
		return destPlaceId;
	}

	public void setDestPlaceId(String destPlaceId) {
		this.destPlaceId = destPlaceId;
	}
	
}
