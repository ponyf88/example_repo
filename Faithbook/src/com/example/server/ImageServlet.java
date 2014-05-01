package com.example.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.data.MemoryManager;
import com.example.data.UserProfileData;
import com.example.feed.Feed;
import com.example.feed.FeedMessage;
import com.example.feed.RSSFeedParser;
import com.google.appengine.api.datastore.Blob;

public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3267140978020582542L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		byte[] imageProfileData = null;
		//your image servlet code here
		if(req.getParameter("imageType").equals("profile")){
			if(req.getParameter("user") != null){
				String user = req.getParameter("user").toString();
				System.out.println(user);
				UserProfileData userProfileData = MemoryManager.getUser(user);
				imageProfileData = userProfileData.getProfilePhoto().getBytes();
			}
		}

		System.out.println("letti " + imageProfileData.length + " byte");
		resp.setContentType("image/jpeg");

		//prova lettura di feed RSS 
	    RSSFeedParser parser = new RSSFeedParser("http://goofynomics.blogspot.it/feeds/posts/default?alt=rss");
	    Feed feed = parser.readFeed();
	    System.out.println(feed);
	    for (FeedMessage message : feed.getMessages()) {
	      System.out.println(message);
	      
	    }
		
		
		
		resp.setContentLength(imageProfileData.length);

		resp.getOutputStream().write(imageProfileData);
	}
}