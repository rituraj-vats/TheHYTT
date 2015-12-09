package com.bitscanvas.thehytt.model.response;

import java.util.List;


/**
 * 
 * @author bk
 * http://developer.android.com/google/gcm/server-ref.html
 */
public class GcmResponse {

	private Long multicast_id;
	
	private Integer success;
	
	private Integer failure;
	
	private Integer canonical_ids;
	
	private List<GcmResults> results;

	public Long getMulticast_id() {
		return multicast_id;
	}

	public void setMulticast_id(Long multicast_id) {
		this.multicast_id = multicast_id;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public Integer getCanonical_ids() {
		return canonical_ids;
	}

	public void setCanonical_ids(Integer canonical_ids) {
		this.canonical_ids = canonical_ids;
	}

	public List<GcmResults> getResults() {
		return results;
	}

	public void setResults(List<GcmResults> results) {
		this.results = results;
	}

}
