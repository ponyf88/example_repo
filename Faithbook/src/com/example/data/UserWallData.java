package com.example.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.code.twig.annotation.Id;

/**
 * @author  Fabio Pini
 */

public class UserWallData implements Serializable{

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5359303131604604613L;

	public UserWallData() {

	}

	/**
	 * @uml.property  name="isLogged"
	 */
	private Boolean isLogged;
	
	/**
	 * @uml.property  name="username"
	 */
	private String username;
	
	/**
	 * @uml.property  name="postID"
	 */
	@Id private String postID;
	
	/**
	 * @uml.property  name="postType"
	 */
	private int postType;
	
	/**
	 * @uml.property  name="postContent"
	 */
	private String postContent;
	
	/**
	 * @uml.property  name="timeStamp"
	 */
	private String timeStamp;
	
}
