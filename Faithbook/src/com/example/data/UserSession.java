package com.example.data;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class UserSession implements Serializable{

	
	public UserSession() {
		
	}

	/**
	 * @uml.property  name="username"
	 */
	@Id private String username;
	
	/**
	 * @uml.property  name="sessionId"
	 */
	
	private String sessionID;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	
	
}
