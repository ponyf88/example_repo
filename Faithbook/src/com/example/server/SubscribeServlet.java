package com.example.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import com.example.shared.*;


import com.example.data.MemoryManager;
import com.example.data.UserProfileData;
import com.google.appengine.api.datastore.Blob;

public class SubscribeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3158938630983769801L;

	public SubscribeServlet() {
	}


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String sentUser = req.getParameter("user");
		String sentPw = req.getParameter("pw"); 
		
		final int TWO_WEEKS = 14*24*60*60; //due settimane
		
		//resp.getWriter().println("Sei loggato!");

		//Ricerco dati user
		UserProfileData newUser = MemoryManager.getUser(sentUser);
		if (newUser == null || (!newUser.getPassword().equals(sentPw) ) )
			resp.getWriter().println("Errore di autenticazione");
		else{
			//Setto il cookie
			String sessionId = req.getSession().getId();
			Cookie userCookie = new Cookie("JSESSIONID",sessionId);
			//System.out.println("Setto il cookie di login per:" + newUser.getUsername());
			
			//TODO: salvare in memoria l'oggetto sessione con user e jsessionid associato
			
			//Durata: 1 giorno
			userCookie.setMaxAge(TWO_WEEKS);
			resp.addCookie(userCookie);

			JSONArray userData = new JSONArray();
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
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		System.out.println("Ricevuta richiesta da: " + req.getRequestURI());

		if (ServletFileUpload.isMultipartContent(req)) {
			System.out.println("richiesta multipart");
		}
		//Creo nuovo User
		UserProfileData newUser = new UserProfileData();
		ServletFileUpload  upload = new ServletFileUpload();
		FileItemIterator iter;

		Blob imageBlob = null;
		try {


			String birthDate = "";
			ByteArrayOutputStream photoBuffer = null;
			iter = upload.getItemIterator(req);
			while(iter.hasNext()){

				FileItemStream item = iter.next();
				String fieldName = "";
				String value = "";

				if(item.isFormField()) {
					// Normal field
					fieldName = item.getFieldName();
					value = Streams.asString(item.openStream());

					System.out.println("Ricevuto dato da form: " + fieldName + " - " + value);
				} else {
					// File
					fieldName = item.getFieldName();


					InputStream imgStream = item.openStream();

					imageBlob = new Blob(IOUtils.toByteArray(imgStream));

					System.out.println("Ricevuto dato non da form: " + fieldName + "di dim: " + imageBlob.getBytes().length);
				}
				if(newUser!=null){
					switch(fieldName){
					case "User":
						if(MemoryManager.getUser(value)!=null)
							newUser = null;
						else
							newUser.setUsername(value.toLowerCase());
						break;
					case "Pw":

						newUser.setPassword(value);
						break;
					case "FirstName":

						newUser.setFirstName(value.toLowerCase());
						break;
					case "SecName":

						newUser.setSecondName(value.toLowerCase());
						break;	
					case "SexChoice":

						newUser.setSex(value);
						break;
					case "giorno":

						birthDate = birthDate + value + " ";
						break;
					case "mese":

						birthDate += value + " ";
						break;		
					case "anno":

						birthDate += value;
						newUser.setBirthdate(birthDate);
						break;					
					case "uploadPhoto":

						newUser.setProfilePhoto(imageBlob);
						break;
					case "Email":

						newUser.setEmail(value);
						break;
					case "Country":

						newUser.setCountry(value);
						break;
					case "City":

						newUser.setCity(value);
						break;
					case "Address":

						newUser.setAddress(value);
						break;
					case "Job":

						newUser.setJob(value);
						break;

					}

				}
			}
			if(newUser!=null){
				//MemoryManager.createUser(newUser.getUsername());
				MemoryManager.createUser(newUser);
				
				System.out.println("salvato user " + newUser.getFirstName() + " " + newUser.getSecondName() + " con username: " + newUser.getUsername());
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);
		if(newUser == null){
			//System.out.println("Email mia:" + MemoryManager.getUser("ponyf88").getEmail());
			//System.out.println("foto mia (dim):" + MemoryManager.getUser("ponyf88").getProfilePhoto().getBytes().length);
			resp.getWriter().println("User già presente, inserirne uno diverso!");

		}
		else{
			//Invio cookie logged in e dati dello user
			Cookie userCookie = new Cookie("userCookie",newUser.getUsername());
			//Durata: 1 giorno
			userCookie.setMaxAge(24*60*60);
			resp.addCookie(userCookie);
			resp.getWriter().println("Cookie di logging aggiunto");

			JSONArray userData = new JSONArray();

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
	}

}