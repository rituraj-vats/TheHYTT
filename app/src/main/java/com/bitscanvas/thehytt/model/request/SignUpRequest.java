package com.bitscanvas.thehytt.model.request;

import android.net.Uri;

import com.bitscanvas.thehytt.emuns.AuthChannel;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SignUpRequest extends ProfileVerificationRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7703951922469486457L;

	private String displayName;

	private String firstName;

	private String lastName;
	
	private String email;
		
	private String password;

	private AuthChannel authChannel;

	@JsonIgnore
	private Uri uri;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}



	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

    public AuthChannel getAuthChannel() {
        return authChannel;
    }

    public void setAuthChannel(AuthChannel authChannel) {
        this.authChannel = authChannel;
    }

    public void setPassword(String password) {
		this.password = password;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}
}
