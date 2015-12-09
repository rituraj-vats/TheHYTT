package com.bitscanvas.thehytt.model.request;

import com.bitscanvas.thehytt.dtos.AbstractDto;

public class RideSearchRequest extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1467366821928771911L;

	private String cscId;
	
	private double srcLat;
	
	private double srcLng;
	
	private String srcFrmtAddr;
	
	private String srcPlaceId;
	
	private double destLat;
	
	private double destLng;
	
	private String destFrmtAddr;
	
	private String destPlaceId;
	
	private Long travelTime;
	
	private Integer pps;

	public String getCscId() {
		return cscId;
	}

	public void setCscId(String cscId) {
		this.cscId = cscId;
	}


	public String getSrcFrmtAddr() {
		return srcFrmtAddr;
	}

	public void setSrcFrmtAddr(String srcFrmtAddr) {
		this.srcFrmtAddr = srcFrmtAddr;
	}

	public String getSrcPlaceId() {
		return srcPlaceId;
	}

	public void setSrcPlaceId(String srcPlaceId) {
		this.srcPlaceId = srcPlaceId;
	}



	public String getDestFrmtAddr() {
		return destFrmtAddr;
	}

	public void setDestFrmtAddr(String destFrmtAddr) {
		this.destFrmtAddr = destFrmtAddr;
	}

	public String getDestPlaceId() {
		return destPlaceId;
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

	public void setDestPlaceId(String destPlaceId) {
		this.destPlaceId = destPlaceId;
	}

	public Long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Long travelTime) {
		this.travelTime = travelTime;
	}

	public Integer getPps() {
		return pps;
	}

	public void setPps(Integer pps) {
		this.pps = pps;
	}

}
