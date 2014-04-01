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

			System.out.println("Nuovo post num" + postID);

			newPost.setPostID(postID);
		}
		else{
			int newID = Integer.parseInt(lastPost.getPostID().replaceFirst(postingUser, "")) + 1;

			newPost.setPostID(postingUser + newID);
		}

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());

		// S is the millisecond
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:MM:ss");

		String timestamp = simpleDateFormat.format(date);

		System.out.println("Timestamp:" + timestamp);

		newPost.setTimeStamp(timestamp);

		//da salvare con MemoryManager
		MemoryManager.createPost(newPost.getPostID());
		MemoryManager.updatePost(newPost);
	}



	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		UserProfileData newUser = null;
		JSONArray userLastPosts = null;
		int MAX_POST = 10;
		//your image servlet code here
		if(req.getParameter("user")!=null){

			//Gestire Risultati multipli
			String username = req.getParameter("user").toString();
			System.out.println("Estraggo ultimi 10 post di: " + username);
			newUser = MemoryManager.getUser(username);
			userLastPosts = new JSONArray();

			if(newUser!= null){

				UserWallData lastPost = MemoryManager.getLastPostOf(username);
				if(lastPost != null){
					try {
						userLastPosts.put(0,createJSONPost(lastPost));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int postLastCounter = Integer.parseInt(lastPost.getPostID().replaceFirst(username, ""));
					//Andiamo a prendere gli ultimi post dello user -- 10
					int i = postLastCounter - 1;
					while (i > 0 && ( (postLastCounter - i) < MAX_POST) ){

						UserWallData prevPost = MemoryManager.getPost(username + i);

						System.out.println("Estratto post con ID: " + prevPost.getPostID());

						createJSONPost(prevPost);
						try {
							userLastPosts.put(i % MAX_POST,createJSONPost(lastPost));
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

	private JSONArray createJSONPost(UserWallData prevPost) {
		JSONArray post = null;

		try {
			JSONObject user = new JSONObject().put("postingUser", prevPost.getUsername());

			JSONObject postType = new JSONObject().put("postType", prevPost.getPostType());

			JSONObject postContent = new JSONObject().put("postContent", prevPost.getPostContent());

			JSONObject timestamp = new JSONObject().put("timestamp", prevPost.getTimeStamp());

			post = new JSONArray(); 

			post.put(0, user);
			post.put(1, postType);
			post.put(2, postContent);
			post.put(3, timestamp);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore nella creazione del Post con ID: " + prevPost.getPostID());
		}
		return post;


	}


}