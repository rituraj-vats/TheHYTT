package com.bitscanvas.thehytt.dtos;

public class PostDto extends SearchDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729639346421822337L;

	private String postId;
	
	private String vehicleNo;
	
	private Integer vehicleType;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
}
