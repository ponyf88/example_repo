package com.example.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

public class ServerUtility {
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

