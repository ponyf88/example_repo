package com.example.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.data.MemoryManager;
import com.example.data.UserProfileData;
import com.example.feed.Feed;
import com.example.feed.FeedMessage;
import com.example.feed.RSSFeedParser;
import com.google.appengine.api.datastore.Blob;

//classe di validazione della sessione
public class DispatcherServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		//Validazione sessione TODO con jsessionID
		/*
		 * req.getSession().setMaxInactiveInterval(TWO_WEEKS);
		String sessionId = req.getSession().getId();
		Cookie persistentSessionCookie = new Cookie("JSESSIONID", sessionId);
		persistentSessionCookie.setPath("/");
		persistentSessionCookie.setMaxAge(TWO_WEEKS);
		resp.addCookie(persistentSessionCookie);
		 */
		String action = req.getParameter("action");
		RequestDispatcher rd = null;

		try {
			switch(action){
			case "friends":
				rd = req.getRequestDispatcher("HandleFriendsServlet");
				rd.include(req, resp);
				break;
			case "image":
				rd = req.getRequestDispatcher("ImageServlet");
				rd.include(req, resp);
				break;
			case "wall":
				rd = req.getRequestDispatcher("ReceiveWallDataServlet");
				rd.include(req, resp);
				break;
			case "search":
				rd = req.getRequestDispatcher("SearchFriendServlet");
				rd.include(req, resp);
				break;
			case "subscribe":
				rd = req.getRequestDispatcher("SubscribeServlet");
				rd.include(req, resp);
				break;
			case "visitProfile":
				rd = req.getRequestDispatcher("VisitProfileServlet");
				rd.include(req, resp);
				break;

			}

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {

		doGet(req, resp);
	}
}