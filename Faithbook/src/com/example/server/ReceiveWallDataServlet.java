package com.example.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.data.MemoryManager;
import com.example.data.UserProfileData;
import com.example.data.UserWallData;
import com.example.shared.JSONArray;
import com.example.shared.JSONException;
import com.example.shared.JSONObject;
import com.google.appengine.api.datastore.Blob;
import com.example.utility.*;

public class ReceiveWallDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3267140978020582542L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		ServerUtility srvUtil = new ServerUtility();

		String requestText = srvUtil.getRequestText(req);

		JSONArray receivedArray;
		try {
			receivedArray = new JSONArray(requestText);

			JSONObject o1 = (JSONObject) receivedArray.get(0);
			String postingUser = (String) o1.get("postingUser");

			JSONObject o2 = (JSONObject) receivedArray.get(1);
			int postType = (int) o2.get("postType");

			JSONObject o3 = (JSONObject) receivedArray.get(2);
			String postContent = (String) o3.get("postContent");

			saveWallPost(postingUser,postType,postContent);

		} catch (JSONException e) {

			e.printStackTrace();
		}
		
		resp.getWriter().println("Post Caricato");
		
	}
	//Salvataggio del post
	private void saveWallPost(String postingUser, int postType,
			String postContent) {
        
		System.out.println("Salvando un post di " + postingUser);
		
		UserWallData newPost = new UserWallData();

		newPost.setUsername(postingUser);

		newPost.setPostType(postType);

		newPost.setPostContent(postContent);

		UserWallData lastPost = MemoryManager.getLastPostOf(postingUser);
		
		
		if(MemoryManager.getLastPostOf(postingUser) == null){
			//nessun post esistente
			String postID = postingUser + '0';
			
			System.out.println("Ultimo post #" + postID);
			
			newPost.setPostID(postID);
		}
		else{
			int newID = Integer.parseInt(lastPost.getPostID().replaceFirst(postingUser, "")) + 1;

			newPost.setPostID(postingUser + newID);
		}

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());

		// S is the millisecond
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:MM:ss:S");
		
		String timestamp = simpleDateFormat.format(ts);

		System.out.println("Timestamp:" + simpleDateFormat.format(timestamp));

		newPost.setTimeStamp(timestamp);
		
		
	}



public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException {

	UserProfileData newUser = null;
	JSONArray userData = null;
	int resultCounter = 0;
	//your image servlet code here
	if(req.getParameter("user")!=null){

		//Gestire Risultati multipli
		String username = req.getParameter("user").toString();
		//System.out.println(user);
		newUser = MemoryManager.getUser(username);
		userData = new JSONArray();

		if(newUser!= null){
			System.out.println("reperito dal datastore user: " + newUser.getUsername());
			try {

				JSONObject user = new JSONObject().put("user", newUser.getUsername());

				JSONObject firstName = new JSONObject().put("firstName", newUser.getFirstName());

				JSONObject secName = new JSONObject().put("secName", newUser.getSecondName());

				JSONObject sex = new JSONObject().put("sex", newUser.getSex());

				JSONObject birthDate = new JSONObject().put("birthDate", newUser.getBirthdate());

				JSONObject country = new JSONObject().put("country",newUser.getCountry());

				JSONObject city = new JSONObject().put("city", newUser.getCity());

				JSONObject address = new JSONObject().put("address", newUser.getAddress());

				JSONObject job = new JSONObject().put("job", newUser.getJob());

				userData.put(0, user);
				userData.put(1, firstName);
				userData.put(2, secName);
				userData.put(3, sex);
				userData.put(4, birthDate);
				userData.put(5, country);
				userData.put(6, city);
				userData.put(7, address);
				userData.put(8, job);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			resp.getWriter().println(userData);
		}
		else
			resp.getWriter().println("Errore nel caricamento del profilo!");
	}
}
}