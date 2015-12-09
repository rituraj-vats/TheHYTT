package com.bitscanvas.thehytt.model.request;

import com.bitscanvas.thehytt.dtos.AbstractDto;

public class ProfileVerificationRequest extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1207536993127665524L;

	private String userId;
	
	private String imageUri;
	
	private Integer vehicleType; // Change it to vehicleName
	
	private String vehicleNumber;//TODO - changed
	
	private Integer ownedVehicle;

	private Integer gender;
	
	private String gcmRegId;

	private String mobNum;
	
	public String getUserId() {
		return userId;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getOwnedVehicle() {
		return ownedVehicle;
	}

	public void setOwnedVehicle(Integer ownedVehicle) {
		this.ownedVehicle = ownedVehicle;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMobNum() {
		return mobNum;
	}

	public void setMobNum(String mobNum) {
		this.mobNum = mobNum;
	}
}
