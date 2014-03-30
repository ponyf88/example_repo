package com.example.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.data.MemoryManager;
import com.example.data.UserProfileData;
import com.example.shared.JSONArray;
import com.example.shared.JSONException;
import com.example.shared.JSONObject;
import com.google.appengine.api.datastore.Blob;

public class SearchFriendServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3267140978020582542L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		Iterator<UserProfileData> foundUsersData = null;
		JSONArray userData = null;
		JSONObject usersData = null;
		int resultCounter = 0;
		//your image servlet code here
		if(req.getParameter("searchData")!=null){
			
			usersData = new JSONObject();
			System.out.println("cerco user con dati: " + req.getParameter("searchData"));
			//Gestire Risultati multipli
			foundUsersData = MemoryManager.searchUser(req.getParameter("searchData"));
			
			
			
			while(foundUsersData!= null && foundUsersData.hasNext()){
				UserProfileData foundUser = foundUsersData.next();
				System.out.println("trovato user con username: " + foundUser.getUsername());	
				userData = new JSONArray();
				
				
				try {

					JSONObject user = new JSONObject().put("user", foundUser.getUsername());

					JSONObject firstName = new JSONObject().put("firstName", foundUser.getFirstName());

					JSONObject secName = new JSONObject().put("secName", foundUser.getSecondName());

					JSONObject sex = new JSONObject().put("sex", foundUser.getSex());

					JSONObject birthDate = new JSONObject().put("birthDate", foundUser.getBirthdate());

					JSONObject country = new JSONObject().put("country",foundUser.getCountry());

					JSONObject city = new JSONObject().put("city", foundUser.getCity());

					JSONObject address = new JSONObject().put("address", foundUser.getAddress());

					JSONObject job = new JSONObject().put("job", foundUser.getJob());

					userData.put(0, user);
					userData.put(1, firstName);
					userData.put(2, secName);
					userData.put(3, sex);
					userData.put(4, birthDate);
					userData.put(5, country);
					userData.put(6, city);
					userData.put(7, address);
					userData.put(8, job);
					
					usersData.put("result" + resultCounter,userData);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resultCounter++;
			
			}
		}
		if(usersData.length() > 0)
			resp.getWriter().println(usersData);
		else{
			System.out.println("Errore, nessun utente trovato! Riprovare inserendo user, nome o cognome!");
			resp.getWriter().println("Errore, nessun utente trovato! Riprovare inserendo user, nome o cognome!");
		}
	}
}