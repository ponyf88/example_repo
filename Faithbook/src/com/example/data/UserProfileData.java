package com.example.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.code.twig.annotation.Id;

/**
 * @author  Fabio Pini
 */

public class UserProfileData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5359303131604604613L;

	public UserProfileData() {
		this.isLogged = true;

	}

	/**
	 * @uml.property  name="isLogged"
	 */
	private Boolean isLogged;


	/**
	 * @uml.property  name="username"
	 */
	@Id private String username;

	/**
	 * @uml.property  name="password"
	 */
	private String password;

	/**
	 * @uml.property  name="firstName"
	 */
	private String firstName;

	/**
	 * @uml.property  name="secondName"
	 */
	private String secondName;

	/**
	 * @uml.property  name="sex"
	 */
	private String sex;


	/**
	 * @uml.property  name="birthdate"
	 */
	private String birthdate;

	/**
	 * @uml.property  name="profilePhoto"
	 */
	private Blob profilePhoto;

	/**
	 * @uml.property  name="email"
	 */
	private String email;

	/**
	 * @uml.property  name="country"
	 */
	private String country;

	/**
	 * @uml.property  name="city"
	 */
	 private String city;

	public List<String> getFriendList() {
		
		return friendList;
	}

	public void setFriendList(List<String> friendList) {
		this.friendList = friendList;
	}

	/**
	 * @uml.property  name="address"
	 */
	private String address;

	/**
	 * @uml.property  name="job"
	 */
	private String job;
	
	/**
	 * @uml.property  name="job"
	 */
	private List<String> friendList;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getSecondName() {
		return secondName;
	}


	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Blob getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(Blob imageBlob) {
		this.profilePhoto = imageBlob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	/**
	 * @return
	 * @uml.property  name="username"
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username
	 * @uml.property  name="username"
	 */
	public void setUsername(String username) {
		this.username = username;
		datastoreUpdateMyself();
	}

	public Boolean isLogged() {
		return isLogged;
	}
	public void setLogged(Boolean b) {
		this.isLogged = b;
		datastoreUpdateMyself();
	}

	protected void datastoreUpdateMyself() {
		//TODO
	}


}
