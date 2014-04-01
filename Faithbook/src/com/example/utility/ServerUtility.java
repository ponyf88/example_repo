package com.example.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.example.data.UserProfileData;

public class ServerUtility {
	public String formatName(UserProfileData userData) {
		
		String firstName = userData.getFirstName();
		String secondName = userData.getSecondName();
		
		firstName = firstName.replaceFirst(""+userData.getFirstName().charAt(0), ""+userData.getFirstName().toUpperCase().charAt(0));
		secondName = secondName.replaceFirst(""+userData.getSecondName().charAt(0), ""+userData.getSecondName().toUpperCase().charAt(0));
		
		return firstName + " " + secondName;
	}
	
	//Legge i dati in arrivo lato client come una sola stringa da conv. poi in JSON
	public String getRequestText(HttpServletRequest req)
		throws UnsupportedEncodingException, IOException {
			Writer writer = new StringWriter();
	
			char[] buffer = new char[1024];
	
			try {
				Reader reader = new BufferedReader(
						new InputStreamReader(req.getInputStream(),req.getCharacterEncoding()));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				
			}
	
			String theResponse = writer.toString();
			return theResponse;
		}
}

