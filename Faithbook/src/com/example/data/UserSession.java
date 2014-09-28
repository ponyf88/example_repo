package com.example.data;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class UserSession implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7855034593369120889L;


	public UserSession(String user, String jSessId) {
		
		this.username = user;
		this.sessionID = jSessId;
	}

	/**
	 * @uml.property  name="username"
	 */
	private String username;
	
	/**
	 * @uml.property  name="sessionId"
	 */
	
	@Id private String sessionID;

	
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
