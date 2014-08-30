package com.example.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.shared.*;

import com.example.data.MemoryManager;
import com.example.data.UserProfileData;
import com.example.shared.JSONException;
import com.example.shared.JSONObject;

public class HandleFriendsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3267140978020582542L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		System.out.println("Dentro HandleFriends");
		if(req.getParameter("user") != null){
			if(req.getParameter("addedFriend") != null){
				String user = req.getParameter("user").toString();
				System.out.println(user + " aggiunge agli amici " + req.getParameter("addedFriend").toString());
				UserProfileData userProfileData = MemoryManager.getUser(user);
				//Update della friendlist
				if(MemoryManager.getUser(req.getParameter("addedFriend").toString())!=null){
					List<String> oldList = userProfileData.getFriendList();
					if(oldList==null)
						oldList = new ArrayList<String>();
					oldList.add(req.getParameter("addedFriend").toString());
					userProfileData.setFriendList(oldList);
					MemoryManager.createUser(userProfileData);
					System.out.println("Aggiunto amico:" + oldList.get(oldList.size()-1));
					resp.getWriter().println("Aggiunto amico:" + oldList.get(oldList.size()-1));
				}
			}
		}
		else if(req.getParameter("getFriendsOf") != null){
			String user = req.getParameter("getFriendsOf");
			System.out.println("Ricerco gli amici di: " + user);
			UserProfileData userProfileDataToCheck = MemoryManager.getUser(user);
			UserProfileData foundFriend = null;
			JSONArray userData = null;
			JSONObject friendListDataJSON = new JSONObject();
			//Ricerca della friendlist
			if(userProfileDataToCheck != null){
				List<String> friendList = userProfileDataToCheck.getFriendList();
				int cont = 0;

				if(friendList != null){
					for(String friend : friendList){

						foundFriend = MemoryManager.getUser(friend);

						userData = new JSONArray();

						try {

							JSONObject username = new JSONObject().put("user", foundFriend.getUsername());

							JSONObject firstName = new JSONObject().put("firstName", foundFriend.getFirstName());

							JSONObject secName = new JSONObject().put("secName", foundFriend.getSecondName());

							JSONObject sex = new JSONObject().put("sex", foundFriend.getSex());

							JSONObject birthDate = new JSONObject().put("birthDate", foundFriend.getBirthdate());

							JSONObject country = new JSONObject().put("country",foundFriend.getCountry());

							JSONObject city = new JSONObject().put("city", foundFriend.getCity());

							JSONObject address = new JSONObject().put("address", foundFriend.getAddress());

							JSONObject job = new JSONObject().put("job", foundFriend.getJob());

							userData.put(0, username);
							userData.put(1, firstName);
							userData.put(2, secName);
							userData.put(3, sex);
							userData.put(4, birthDate);
							userData.put(5, country);
							userData.put(6, city);
							userData.put(7, address);
							userData.put(8, job);

							friendListDataJSON.put("result" + cont,userData);
							cont++;

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

					}
					resp.getWriter().println(friendListDataJSON);
				}
				else 
					resp.getWriter().println("Errore: non hai ancora amici");//Mettere JSON vuoto
			}

		}

		//System.out.println("letti " + imageProfileData.length + " byte");
		resp.setContentType("text/html");


		//resp.setContentLength(imageProfileData.length);


	}
}