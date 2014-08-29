/**
 * 
 */
package com.example.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.management.Query;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;

/**
 * @author Fabio Pini
 *
 */
public class MemoryManager {

	static Cache userCache;
	static Cache tableCache;
	static Cache aiCache;

	private static synchronized Cache getMemcache( 
			String cacheName) { 
		try { 
			CacheManager cacheMgr = CacheManager.getInstance(); 
			Cache cache = cacheMgr.getCache(cacheName); 
			if (cache == null) { 
				CacheFactory cacheFactory = cacheMgr.getCacheFactory(); 
				cache = cacheFactory.createCache(Collections.EMPTY_MAP);
				cacheMgr.registerCache(cacheName, cache); 
			} 
			return cache; 
		} catch (CacheException e) {
			System.err.println("[MemoryManager][getMemcache] CacheException");
			return null;
		}
	} 

	//Cerco user con combinazioni di nome e cognome o username
	public static synchronized Iterator<UserProfileData> searchUser(String searchData) {

		searchData = searchData.toLowerCase();
		userCache = getMemcache("userCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		UserProfileData aUser = (UserProfileData) userCache.get(searchData); 

		System.out.println("Debug");
		if (aUser == null) {


			aUser = datastore.load(UserProfileData.class, searchData);	
			if(aUser==null){
				System.out.println("Non è stato inserito lo username");
				Iterator<UserProfileData> foundWithName = null;
				Iterator<UserProfileData> foundWithSecName = null;
				//Non è stato inserito lo user; provo col nome/cognome
				if(searchData.split(" ").length == 1){
					System.out.println("Inserito solo il nome o il cognome!");

					foundWithName =  datastore.find().type(UserProfileData.class).
							addFilter("firstName",FilterOperator.EQUAL , searchData).now();

					if(!foundWithName.hasNext()){
						foundWithSecName =  datastore.find().type(UserProfileData.class).
								addFilter("secondName",FilterOperator.EQUAL , searchData).now();
						if(foundWithSecName.hasNext()){
							System.out.println("trovato User con cognome:" + foundWithName);
							return foundWithSecName;
						}
					}
					else{
						System.out.println("trovato User con nome:" + foundWithName);
						return foundWithName;
					}
				}
				else if(searchData.split(" ").length == 2){
					System.out.println("ins Nome+Cognome o viceversa!");
					//se ho ins Nome+Cognome
					foundWithName = datastore.find().type(UserProfileData.class).
							addFilter("firstName",FilterOperator.EQUAL , searchData.split(" ")[0])
							.addFilter("secondName",FilterOperator.EQUAL , searchData.split(" ")[1]).now();
					if(!foundWithName.hasNext()){
						//Se ho ins Cognome + Nome
						foundWithSecName = datastore.find().type(UserProfileData.class).
								addFilter("firstName",FilterOperator.EQUAL , searchData.split(" ")[1])
								.addFilter("secondName",FilterOperator.EQUAL , searchData.split(" ")[0]).now();
						if(foundWithSecName.hasNext())
							return foundWithSecName;
					}
					else
						return foundWithName;

				}
			}
			else{
				List<UserProfileData> list = new ArrayList();
				list.add(aUser);
				Iterator<UserProfileData> iterator = list.iterator();
				//System.out.println("Anvedi: " + iterator.next().getFirstName());
				return iterator;
			}
		}

		return null;
	} 




	//Creo user session --> Da usare post login
	public static synchronized UserSession createUserSession(UserSession newSession) {

		userCache = getMemcache("userSessionCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		UserSession aSession = (UserSession) userCache.get(newSession.getUsername()); 

		//Creo nuova sessione se non esistente
		if (aSession == null) {
			aSession = datastore.load(UserSession.class, newSession.getUsername());		
		}
		else{

			try {
				datastore.associate(aSession);
				System.err.println("user datastore associate" + aSession);
			} catch(IllegalArgumentException e) {

				System.err.println("user associate failed");
			} finally {
				datastore.update(aSession);
				System.err.println("user datastore update" + aSession);
			}
		}

		//Agg cache
		userCache.put(aSession.getUsername(), aSession);

		return aSession;
	} 

	//Estraggo sessionId --> da usare in dispatcher
	public static synchronized UserProfileData getUserSession(String username) {

		userCache = getMemcache("userCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		UserProfileData aUser = (UserProfileData) userCache.get(username); 

		if (aUser == null) {
			aUser = datastore.load(UserProfileData.class, username);		
		}

		return aUser;
	} 

	
	//Aggiornamento/creazione di uno user
	public static synchronized void createUser(UserProfileData aUser){
		userCache = getMemcache("userCache");
		userCache.put(aUser.getUsername(), aUser);
		
		ObjectDatastore datastore = new AnnotationObjectDatastore();

		//Cerco nella cache uno user con lo stesso username
		UserProfileData aUserFound = (UserProfileData) userCache.get(aUser.getUsername()); 

		//carico nuovo user dal datastore direttamente
		if (aUserFound == null) {
			aUserFound = datastore.load(UserProfileData.class, aUser.getUsername());		
		}
		
		if (aUserFound == null) {
			try {
				datastore.associate(aUserFound);
				System.err.println("user datastore associate"+aUserFound);
			} catch(IllegalArgumentException e) {
	
				System.err.println("user associate failed");
			} finally {
				datastore.update(aUserFound);
				System.err.println("user datastore update"+aUserFound);
			}
		}
		else{
			datastore.store(aUser); //Salvo lo user fornito in input
		}
	}
	
	//Estraggo user
	public static synchronized UserProfileData getUser(String username) {

		userCache = getMemcache("userCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		UserProfileData aUser = (UserProfileData) userCache.get(username); 
		
		//Ricerca con chiave
		if (aUser == null) {
			aUser = datastore.load(UserProfileData.class, username);		
		}
		
		return aUser;
	} 
	

	//Creo un post
	public static synchronized UserWallData createPost(String postID) {

		userCache = getMemcache("userCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		UserWallData aPost = (UserWallData) userCache.get(postID); 

		//Creo nuovo post
		if (aPost == null) {
			System.out.println("Creato post vuoto");
			aPost = datastore.load(UserWallData.class, postID);		
		}

		return aPost;
	} 


	//Aggiornamento di un post 
	public static synchronized void updatePost(UserWallData aWall){
		userCache = getMemcache("userCache");
		userCache.put(aWall.getPostID(), aWall);

		ObjectDatastore datastore = new AnnotationObjectDatastore();
		try {
			datastore.associate(aWall);
			System.err.println("post datastore associate"+aWall);
		} catch(IllegalArgumentException e) {

			System.err.println("post associate failed");
		} finally {
			datastore.update(aWall);
			System.err.println("post datastore update"+aWall);
		}
	}

	//Estraggo post
	public static synchronized UserWallData getPost(String postID) {

		userCache = getMemcache("userCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		UserWallData aPost = (UserWallData) userCache.get(postID); 

		if (aPost == null) {
			aPost = datastore.load(UserWallData.class, postID);		
		}

		return aPost;
	} 


	//Prendo l'ultimo post di user dato in input
	public static synchronized UserWallData getLastPostOf(String username) {

		userCache = getMemcache("userCache");

		ObjectDatastore datastore = new AnnotationObjectDatastore();

		//Itero i post per trovare il più recente
		Iterator<UserWallData> foundPostsOf =  datastore.find().type(UserWallData.class).
				addFilter("username",FilterOperator.EQUAL , username).now();

		ArrayList<Integer> userPostIds = new ArrayList<Integer>();

		while(foundPostsOf.hasNext()){
			int postIdNum = Integer.parseInt(foundPostsOf.next().getPostID().replaceFirst(username, ""));

			userPostIds.add(postIdNum);
		}

		if(userPostIds.size() > 0){
			int lastPostCounter = Collections.max(userPostIds);

			System.out.println("Estraggo ultimo post: " +  username + lastPostCounter);


			return getPost(username + lastPostCounter);
		}
		else
			return null; //nessun Post dello user con username
	} 

}
