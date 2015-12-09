package com.bitscanvas.thehytt.model.request;

import com.bitscanvas.thehytt.dtos.AbstractDto;


public class AuthRequest extends AbstractDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4567669666413999763L;
	
	private Integer sourceChannel; //Whether logged in via FB, GOOGLE or Custom
	
	private String displayName;
	
	private String firstName;
	
	private String lastName;
	
	private String dob;
	
	private String gender;
	
	private String email;
	
	private String mobileNumber;
	
	private String gcmRegId; //The regid to be used for communicating via gcm
	
	private String pwd;

	public Integer getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(Integer sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
