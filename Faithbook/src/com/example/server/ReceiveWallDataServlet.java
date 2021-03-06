package com.example.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.TimeZone;

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

	//creo un post dell'utente
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		
		//Recupero l'utente
		String postingUser = req.getAttribute("user").toString();
		
		ServerUtility srvUtil = new ServerUtility();

		String requestText = srvUtil.getRequestText(req);
		
		JSONArray receivedArray;
		try {
			receivedArray = new JSONArray(requestText);

			
			JSONObject o1 = (JSONObject) receivedArray.get(0);
			int postType = (int) o1.get("postType");

			JSONObject o2 = (JSONObject) receivedArray.get(1);
			String postContent = (String) o2.get("postContent");

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
		
		int lastPostIndex = Integer.parseInt(lastPost.getPostID().replaceFirst(postingUser, ""));
		if(MemoryManager.getLastPostOf(postingUser) == null){
			//nessun post esistente
			String postID = postingUser + '0';

			System.out.println("Nuovo post num" + postID);

			newPost.setPostID(postID);
		}
		else{
			int newID = lastPostIndex + 1;

			newPost.setPostID(postingUser + newID);
		}

		Timestamp ts = new Timestamp(System.currentTimeMillis());
	
		Date date = new Date(ts.getTime());

		// S is the millisecond
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
		
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
		
		String timestamp = simpleDateFormat.format(date);

		System.out.println("Timestamp:" + timestamp);

		newPost.setTimeStamp(timestamp);

		//da salvare con MemoryManager
		
		MemoryManager.createPost(newPost);
	}


	//Estraggo ultimi 10 post
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		UserProfileData newUser = null;
		JSONArray userLastPosts = null;
		final int MAX_POST = 10;
		String username = req.getAttribute("user").toString();
		
		if(username != null){

			System.out.println("Estraggo ultimi 10 post di: " + username);
			newUser = MemoryManager.getUser(username);
			userLastPosts = new JSONArray();

			if(newUser!= null){

				UserWallData lastPost = MemoryManager.getLastPostOf(username);
				
				//Da traslare se si raggiunge il limite
				if(lastPost != null){
					try {
						userLastPosts.put(0,createJSONPost(lastPost,newUser));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int postLastCounter = Integer.parseInt(lastPost.getPostID().replaceFirst(username, ""));
					//Andiamo a prendere gli ultimi post dello user -- 10
					int i = postLastCounter - 1;
					int counterJSON = 1;
					while (i > 0 && ( (postLastCounter - i) < MAX_POST) ){
						
						
						UserWallData prevPost = MemoryManager.getPost(username + i);

						System.out.println("Estratto post con ID: " + prevPost.getPostID());

						try {
							
							userLastPosts.put(counterJSON,createJSONPost(prevPost,newUser));
							counterJSON++;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						i--;
					}


					resp.getWriter().println(userLastPosts.toString());
				}
				else
					resp.getWriter().println("Errore nel trovare l'ultimo post!");
			}
			else
				resp.getWriter().println("Errore nello user!");
		}
	}

	//Crea un post formato JSON
	private JSONArray createJSONPost(UserWallData prevPost, UserProfileData userData) {
		JSONArray post = null;
		
		try {
			ServerUtility su = new ServerUtility(); 
			String userFormattedName = su.formatName(userData);
			
			JSONObject user = new JSONObject().put("postingUser", userFormattedName);

			JSONObject postType = new JSONObject().put("postType", prevPost.getPostType());

			JSONObject postContent = new JSONObject().put("postContent", prevPost.getPostContent());

			JSONObject timestamp = new JSONObject().put("timestamp", prevPost.getTimeStamp());
			
			JSONObject postID = new JSONObject().put("postID", prevPost.getPostID());

			post = new JSONArray(); 

			post.put(0, user);
			post.put(1, postType);
			post.put(2, postContent);
			post.put(3, timestamp);
			post.put(4, postID);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore nella creazione del Post con ID: " + prevPost.getPostID());
		}
		return post;


	}



}