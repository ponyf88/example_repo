package com.example.client;

import java.util.Date;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class UserBasicInfoPanel extends ScrollPanel {
	
	Grid grid = null;
	
	public UserBasicInfoPanel(String visitedUser) {
		setSize("450px", "433px");
		
		grid = new Grid(2, 2);
		setWidget(grid);
		grid.setSize("100%", "422px");
		
		String url = "/VisitProfileServlet?user=" + visitedUser;
		
		CallbackRenderer httpResponseHandler = new CallbackRenderer();
		
		this.getVisitedUserData(url,httpResponseHandler);
		
	}
	public void getVisitedUserData(String url, RequestCallback httpResponseHandler) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

		int respStatus = 0;
		try {

			builder.setTimeoutMillis(2000);
			Request request = builder.sendRequest(null, httpResponseHandler);
		

		} catch (RequestException e) {
			// Couldn't connect to server
			Window.alert("No connect!");
		}
	}	
	
	//Implemento il renderer
	public class CallbackRenderer implements RequestCallback{

		JSONArray visitedProfileDataJSONArray = null;

		public void onError(Request request, Throwable exception) {
			// Couldn't connect to server (could be timeout, SOP violation, etc.)
			//sendButton.setEnabled(false);
			Window.alert("Errore Client!");
		}

		public void onResponseReceived(Request request, Response response) {
			if (200 == response.getStatusCode()) {
				if(response.getText().startsWith("Errore"))
					Window.alert("Errore: username o password errate!");
				else{
					visitedProfileDataJSONArray = (JSONArray) JSONParser.parseStrict(response.getText());
					

					String userFirstName  = ( (JSONObject) visitedProfileDataJSONArray.get(1) ).get("firstName").isString().stringValue();
					String userSecName  = ( (JSONObject) visitedProfileDataJSONArray.get(2) ).get("secName").isString().stringValue();
					String userSex  = ( (JSONObject) visitedProfileDataJSONArray.get(3) ).get("sex").isString().stringValue();
					String userBirth  = ( (JSONObject) visitedProfileDataJSONArray.get(4) ).get("birthDate").isString().stringValue();
					String userCountry = ( (JSONObject) visitedProfileDataJSONArray.get(5) ).get("country").isString().stringValue();
					String userCity = ( (JSONObject) visitedProfileDataJSONArray.get(6) ).get("city").isString().stringValue();
					String userAddress = ( (JSONObject) visitedProfileDataJSONArray.get(7) ).get("address").isString().stringValue();
					String userJob = ( (JSONObject) visitedProfileDataJSONArray.get(8) ).get("job").isString().stringValue();
					
					DisclosurePanel disclosurePanel = new DisclosurePanel("Chi Sono", true);
					disclosurePanel.setStyleName("gwt-DisclosurePanel");
					HTML panelTitle = new HTML("Chi sono");
					panelTitle.setStyleName("gwt-DisclosurePanel-header");
					
					disclosurePanel.setHeader(panelTitle);
				
					//Window.alert(userSex);
					String whoIsUser = userFirstName + " " + userSecName;
					HTML html = new HTML(whoIsUser);
					
					if(userSex.equals("M")){
						
						whoIsUser += "<br>" + "<br>" + "<br>" + "<image src='img/male.png' height='40px' width='40px'/>";
						html = new HTML(whoIsUser);
					
					}else if(userSex.equals("F")){
						
						whoIsUser += "<br>" + "<br>" + "<br>" + "<image src='img/female.png' height='40px' width='40px'/>";
						html = new HTML(whoIsUser);
					}


					html.setStyleName("gwt-DisclosurePanel-inside-CustomFaithbook");
					

					grid.setWidget(0, 0, disclosurePanel);
					grid.getCellFormatter().setHeight(0, 0, "50%");
					grid.getCellFormatter().setWidth(0, 0, "50%");
					disclosurePanel.setSize("100%", "100%");
					
					disclosurePanel.add(html);
					
					DisclosurePanel disclosurePanel_1 = new DisclosurePanel("Compleanno", true);
					disclosurePanel_1.setStyleName("gwt-DisclosurePanel");
					panelTitle = new HTML("Compleanno");
					panelTitle.setStyleName("gwt-DisclosurePanel-header");
					disclosurePanel_1.setHeader(panelTitle);
					
					grid.setWidget(0, 1, disclosurePanel_1);
					grid.getCellFormatter().setHeight(0, 1, "50%");
					grid.getCellFormatter().setWidth(0, 1, "50%");
					disclosurePanel_1.setSize("100%", "100%");
					
					String dateBirthday = userBirth.substring(0,userBirth.length()-4).concat(DateTimeFormat.getFormat( "yyyy" ).format( new Date() ));
					dateBirthday += "<br>" + "<br>" + "<br>" + "<image src='img/BirthdayCake.jpg' height='40px' width='40px'/>";
					HTML html1 = new HTML(dateBirthday);
					html1.setStyleName("gwt-DisclosurePanel-inside-CustomFaithbook");
					disclosurePanel_1.add(html1);

					DisclosurePanel disclosurePanel_2 = new DisclosurePanel("Dove Abito", true);
					disclosurePanel_2.setStyleName("gwt-DisclosurePanel");
					panelTitle = new HTML("Dove Abito");
					panelTitle.setStyleName("gwt-DisclosurePanel-header");
					disclosurePanel_2.setHeader(panelTitle);
					
					
					grid.setWidget(1, 0, disclosurePanel_2);
					grid.getCellFormatter().setWidth(1, 0, "50%");
					grid.getCellFormatter().setHeight(1, 0, "50%");
					disclosurePanel_2.setSize("100%", "100%");
					
					String whereIsUser = "<b>Paese:</b> " + userCountry + "<br>" + "<br>" + "<b>Citt&agrave:</b> " + userCity + "<br>" + "<br>" + "<b>Indirizzo:</b> " + userAddress;
					HTML html2 = new HTML(whereIsUser);
					html2.setStyleName("gwt-DisclosurePanel-inside-CustomFaithbook");
					disclosurePanel_2.add(html2);

					
					DisclosurePanel disclosurePanel_3 = new DisclosurePanel("Cosa Faccio", true);
					disclosurePanel_3.setStyleName("gwt-DisclosurePanel");
					panelTitle = new HTML("Cosa Faccio");
					panelTitle.setStyleName("gwt-DisclosurePanel-header");
					disclosurePanel_3.setHeader(panelTitle);
					
					grid.setWidget(1, 1, disclosurePanel_3);
					grid.getCellFormatter().setHeight(1, 1, "50%");
					grid.getCellFormatter().setWidth(1, 1, "50%");
					disclosurePanel_3.setSize("100%", "100%");
					
					String whatUserDoes = "<b>Occupazione:</b> " + userJob;
					HTML html3 = new HTML(whatUserDoes);
					html3.setStyleName("gwt-DisclosurePanel-inside-CustomFaithbook");
					disclosurePanel_3.add(html3);
					
					
				}
			} else {
				// Handle the error.  Can get the status text from response.getStatusText()
				Window.alert("Errore dal server:" + response.getStatusCode() +  response.getText());
			}
		}
		public JSONArray getVisitedProfileData(){
			return this.visitedProfileDataJSONArray;
		}
	}
}
