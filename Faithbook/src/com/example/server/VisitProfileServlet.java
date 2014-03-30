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

public class VisitProfileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3267140978020582542L;

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