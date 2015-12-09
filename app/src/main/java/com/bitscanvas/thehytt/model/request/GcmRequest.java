package com.bitscanvas.thehytt.model.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//https://developers.google.com/cloud-messaging/http
public class GcmRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1725283819115825483L;
	
	//https://developers.google.com/cloud-messaging/server-ref#table1
	//private List<String> registration_ids; // The gcm regid of the user that needs to be notified
	private String to; //registration_ids has been deprecated. Use "to" instead 
	/* 
	 * use a single collapse_key for all messages that need to be replaced with preceeding message.
	 * In case you want all message to be uniquely delivered, ignore this param.
	 * Will not be used in case of join/pick up request.
	 * Can be used in case of Confirmation regarding who all have accepted/denied the request
	 * 	or started the journey
	 */
	private String collapse_key;
	private Integer time_to_live;
	private Boolean delay_while_idle;
    private Map<String,String> data;
    
    /*
    public void addRegistrationId(String regId){
    	if(registration_ids == null){
    		registration_ids = new LinkedList<String>();
    	}
    	registration_ids.add(regId);
    }
    */
    
    
	
    public void addData(String key, String value){
    	if(data == null){
    		data = new HashMap<String, String>();
    	}
    	data.put(key, value);
    }

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCollapse_key() {
		return collapse_key;
	}

	public void setCollapse_key(String collapse_key) {
		this.collapse_key = collapse_key;
	}

	public Integer getTime_to_live() {
		return time_to_live;
	}

	public void setTime_to_live(Integer time_to_live) {
		this.time_to_live = time_to_live;
	}

	public Boolean getDelay_while_idle() {
		return delay_while_idle;
	}

	public void setDelay_while_idle(Boolean delay_while_idle) {
		this.delay_while_idle = delay_while_idle;
	}

	/*
	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}
	 */
	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
