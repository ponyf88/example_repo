package com.example.client;

import com.cleanform.gwt.bootstrap.client.ui.Button.ButtonType;
import com.cleanform.gwt.bootstrap.client.ui.ListBox;
import com.cleanform.gwt.bootstrap.client.ui.TextArea;
import com.cleanform.gwt.bootstrap.js.client.ui.Carousel;


import com.cleanform.gwt.bootstrap.js.client.ui.Navigator;
import com.cleanform.gwt.bootstrap.js.client.ui.Navigator.NavType;
import com.cleanform.gwt.user.client.ActionEvent;
import com.cleanform.gwt.user.client.ActionEvent.ActionHandler;
import com.cleanform.gwt.user.layout.client.Layouts;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Faithbook implements EntryPoint {

	//pannelli principali
	final SplitLayoutPanel splitPanel = new SplitLayoutPanel(5);
	public HorizontalPanel navPanel = null;
	public VerticalPanel verticalPanel_1 = null;
	public VerticalPanel verticalPanel = null;
	public VerticalPanel centerVerticalPanel = null;
	public Label lblCreditsLink = null;
	private Label lblCreditsLink_1;
	public Image image = null;
	public TextBox txtbxUser = null;
	public PasswordTextBox txtbxPassword = null;
	public Button logoutButton = null;
	public Label welcomeLabel = null;
	public JSONArray userDataJSONArray;

	public TextBox suggestBox = null;
	public FlexTable flexTable = null;
	public ScrollPanel scrollPanel = null;
	public com.cleanform.gwt.bootstrap.client.ui.Button sendButton = null;
	public Button button_1 = null;
	public String visitedUser = null;
	public Navigator menuPanel = null;

	public SubscribePanel centerScrollable = null;


	public class TreeItemHandler implements SelectionHandler<TreeItem>{

		String visitedUser = null;

		public TreeItemHandler(String _visitedUser){
			this.visitedUser = _visitedUser;
		}
		@Override
		public void onSelection(SelectionEvent event) {
			TreeItem item = (TreeItem) event.getSelectedItem();

			// expand the selected item
			if(item.getTitle().equals("Profilo")){
				UserBasicInfoPanel centerBasicData = null;
				//Passare i dati di base da elaborare al pannello	

				centerBasicData = new UserBasicInfoPanel(visitedUser);

				//Rimuovo il widget centrale (l'ultimo inserito)
				centerVerticalPanel.clear();
				centerVerticalPanel.add(centerBasicData);
				//Controllare se avviene il refresh automatico 
				//RootLayoutPanel.get().add(splitPanel);
			}
			if(item.getTitle().equals("Amici")){
				//Passare i dati di base da elaborare al pannello	
				//image.setUrl("/images?imageType=profile&user=" + Cookies.getCookie("userCookie"));
				FriendListPanel centerBasicData = null;

				centerBasicData = new FriendListPanel(visitedUser);

				//Rimuovo il widget centrale (l'ultimo inserito)
				centerVerticalPanel.clear();
				centerVerticalPanel.add(centerBasicData);
				//Controllare se avviene il refresh automatico 
				//RootLayoutPanel.get().add(splitPanel);
			}
			if(item.getTitle().equals("Bacheca")){
				//Passare i dati di base da elaborare al pannello	
				//image.setUrl("/images?imageType=profile&user=" + Cookies.getCookie("userCookie"));
				centerVerticalPanel.clear();
				AboutPanel centerBasicData = null;

				centerBasicData = new AboutPanel(visitedUser);

				//Rimuovo il widget centrale (l'ultimo inserito)

				centerVerticalPanel.add(centerBasicData);
				//Controllare se avviene il refresh automatico 
				//RootLayoutPanel.get().add(splitPanel);
			}

		}
	}


	private void loadEastWestSouthPanels(String visitedUser){

		//Rimuovo i pannelli east, west south presenti
		if(verticalPanel!=null)
			verticalPanel.removeFromParent();
		if(verticalPanel_1!=null)	
			verticalPanel_1.removeFromParent();
		if(lblCreditsLink_1!=null)	
			lblCreditsLink_1.removeFromParent();

		//Pannello centrale
		if(centerVerticalPanel != null)
			centerVerticalPanel.removeFromParent();

		if(splitPanel != null)
			splitPanel.removeFromParent();
		//carico logout button
		sendButton.removeFromParent();
		txtbxUser.removeFromParent();
		txtbxPassword.removeFromParent();


		//ridefinizione dei pannelli centrali
		verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		verticalPanel_1.setSize("100%", "20%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);

		suggestBox = new TextBox();
		horizontalPanel.add(suggestBox);
		horizontalPanel.setCellHeight(suggestBox, "20px");
		horizontalPanel.setCellWidth(suggestBox, "100%");
		suggestBox.setStyleName("gwt-FacebookTextBox");
		suggestBox.setTitle("Ricerca amici");
		suggestBox.setSize("100%", "20px");



		com.cleanform.gwt.bootstrap.client.ui.Button button = new com.cleanform.gwt.bootstrap.client.ui.Button("Trova Persone", ButtonType.PRIMARY);
		button.setStyleName("btn btn-primary btn-xs");
		horizontalPanel.add(button);
		//horizontalPanel.setCellHeight(button, "20px");
		horizontalPanel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_RIGHT);
		//button.setText("Trova Persone");
		//button.setStylePrimaryName("uibutton.confirm");
		//button.setStyleName("TreeItemButton-Custom");
		verticalPanel_1.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
		//button.setSize("98px", "20px");
		button.addClickHandler(new SearchFriendHandler());

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.setStyleName("gwt-MenuPanelRight");
		verticalPanel_2.setBorderWidth(0);
		verticalPanel_2.setTitle("Amici");
		verticalPanel_1.add(verticalPanel_2);
		verticalPanel_1.setCellVerticalAlignment(verticalPanel_2, HasVerticalAlignment.ALIGN_BOTTOM);
		verticalPanel_2.setSize("100%", "119px");

		scrollPanel = new ScrollPanel();
		//scrollPanel.setStyleName("gwt-FacebookLike-Inverted");
		verticalPanel_2.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		verticalPanel_2.setCellHeight(scrollPanel, "100%");
		verticalPanel_2.setCellWidth(scrollPanel, "100%");

		flexTable = new FlexTable();


		scrollPanel.setWidget(flexTable);
		flexTable.setSize("100%", "100%");

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_3.setStyleName("gwt-FacebookLike-Inverted");
		verticalPanel_3.setBorderWidth(0);
		verticalPanel_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_3.setTitle("Chat");
		verticalPanel_1.add(verticalPanel_3);
		verticalPanel_1.setCellHeight(verticalPanel_3, "100%");
		verticalPanel_1.setCellHorizontalAlignment(verticalPanel_3, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_3.setSize("100%", "120px");

		verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setStyleName("gwt-MenuPanelLeft");
		verticalPanel.setBorderWidth(0);
		verticalPanel.setSize("100%", "80%");

		image = new Image();
		image.setStyleName("gwt-TabPanelBottom");
		image.setTitle("Immagine");
		//Passare nome utente
		image.setUrl("/images?imageType=profile&user=" + visitedUser);
		verticalPanel.add(image);
		verticalPanel.setCellVerticalAlignment(image, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
		image.setSize("140px", "150px");

		Tree tree = new Tree();


		TreeItem trtmNewItem = new TreeItem();
		trtmNewItem.setStyleName("TreeItemButton-Custom");
		trtmNewItem.setText("Profilo");
		trtmNewItem.setTitle("Profilo");

		tree.addItem(trtmNewItem);
		trtmNewItem.setSize("", "10%");


		TreeItem trtmNewItem_1 = new TreeItem();
		trtmNewItem_1.setStyleName("TreeItemButton-Custom");
		trtmNewItem_1.setText("Bacheca");
		trtmNewItem_1.setTitle("Bacheca");
		tree.addItem(trtmNewItem_1);
		trtmNewItem_1.setSize("", "10%");
		trtmNewItem_1.setState(true);

		TreeItem trtmNewItem_2 = new TreeItem();
		trtmNewItem_2.setStyleName("TreeItemButton-Custom");
		trtmNewItem_2.setText("Amici");
		trtmNewItem_2.setTitle("Amici");
		tree.addItem(trtmNewItem_2);
		trtmNewItem_2.setSize("", "10%");

		//Creare selectionHandler customizzato
		tree.addSelectionHandler(new TreeItemHandler(visitedUser));

		button_1 = new Button("Ricerca Amici");
		button_1.setText("+ Aggiungi agli Amici");
		button_1.setStylePrimaryName("uibutton.confirm");
		button_1.setStyleName("TreeItemButton-Custom");
		verticalPanel.add(button_1);
		button_1.setSize("140px", "20px");

		if(visitedUser.equals(Cookies.getCookie("userCookie")))
			button_1.setVisible(false);
		else{
			button_1.addClickHandler(new AddFriendHandler(visitedUser));
			button_1.setVisible(true);
		}

		verticalPanel.add(tree);
		verticalPanel.setCellVerticalAlignment(tree, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(tree, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHeight(tree, "100%");
		verticalPanel.setCellWidth(tree, "100%");
		tree.setSize("100%", "100%");
		// Add scrollable text to the center.
		String centerText = "";
		for (int i = 0; i < 3; i++) {
			centerText += " " + centerText;
		}

		splitPanel.addWest(verticalPanel, 350.0);
		splitPanel.addEast(verticalPanel_1, 350.0);
		lblCreditsLink_1.setWidth("100%");
		splitPanel.addSouth(lblCreditsLink_1, 70.0);

		//Carico pannello 'about' di default

		UserBasicInfoPanel centerBasicData = new UserBasicInfoPanel(visitedUser);

		centerVerticalPanel = new VerticalPanel();
		centerVerticalPanel.setWidth("100%");
		centerVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerVerticalPanel.add(centerBasicData);
		//verticalPanel.setCellHorizontalAlignment(centerBasicData, HasHorizontalAlignment.ALIGN_CENTER);

		splitPanel.add(centerVerticalPanel);
		centerVerticalPanel.setWidth("100%");
		RootLayoutPanel.get().add(splitPanel);
	}

	public void getVisitedUserData(String url, RequestCallback httpResponseHandler) {


		JSONArray visitedProfileDataJSONArray = null;
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

	//pannello About centrale
	public class AboutPanel extends VerticalPanel {
		ListBox widget = null;

		TextArea postTextArea = null;
		String visitedUser = null;
		VerticalPanel carContainer = new VerticalPanel();
		ScrollPanel commentContainerPanel = new ScrollPanel();
		VerticalPanel insertPostPanel = new VerticalPanel();

		private void recoverWallDataRequest(String httpMethod, String url) {
			RequestBuilder builder = null;
			if(httpMethod.equals("GET"))
				builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

			int respStatus = 0;

			try {
				Request request = builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						// Couldn't connect to server (could be timeout, SOP violation, etc.)
						//sendButton.setEnabled(false);
						Window.alert("Errore Client!");

					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							if(response.getText().startsWith("Errore"))
								Window.alert("Errore:post non ritrovato!");
							else{
								Window.alert(response.getText());
								Carousel car = new Carousel();

								JSONArray foundPosts = (JSONArray) JSONParser.parseStrict(response.getText());
								String postID = "";
								for(int i = 0; i < foundPosts.size(); i++){

									//Window.alert("Lung del JSONArray: " + foundPosts.size());
									JSONArray post = (JSONArray) foundPosts.get(i);

									String postingUser = ((JSONObject)post.get(0)).get("postingUser").isString().stringValue();
									Double postType = ((JSONObject)post.get(1)).get("postType").isNumber().doubleValue();
									String postContent = ((JSONObject)post.get(2)).get("postContent").isString().stringValue();
									String postTimestamp = ((JSONObject)post.get(3)).get("timestamp").isString().stringValue();

									ButtonType postTypeBtn = null;
									switch(postType.intValue()){
									case 0:
										postTypeBtn= ButtonType.PRIMARY;
										break;
									case 1:
										postTypeBtn= ButtonType.SUCCESS;
										break;
									case 2:
										postTypeBtn= ButtonType.WARNING;
										break;
									case 3:
										postTypeBtn= ButtonType.DANGER;
										break;
									}
									//car.addItem("<h4>Caption 1</h4>", Layouts.as(
									//new com.cleanform.gwt.bootstrap.client.ui.Button("Item1", ButtonType.SUCCESS)).width().height(200));
									String addNewPost = "";
									if (i==0){
										addNewPost = "<h5>Nuovo Post!</h5>" + "<br/>" + "<br/>" + "<br/>";
										postID = ((JSONObject)post.get(4)).get("postID").isString().stringValue(); //Commenti solo all'ultimo post
									}
									car.addItem(addNewPost + "<h5>" + postContent + "</h5>" + "<br/>" + postingUser + ", " + postTimestamp, Layouts.as(
											new com.cleanform.gwt.bootstrap.client.ui.Button("", postTypeBtn)).width().height(200));
									car.pause();
								}

								carContainer.add(car);
								car.pause();
					
								String disqusCode =
										"<div id='disqus_thread'>" + 						
										"</div>" +
										"<noscript>Please enable JavaScript to view the <a href='http://disqus.com/?ref_noscript'>comments powered by Disqus.</a></noscript>" +
										"<a href='http://disqus.com' class='dsq-brlink'>comments powered by <span class='logo-disqus'>Disqus</span></a></body></html>";
								//TODO: Inserire box commenti: uno per user o uno x post?
								HTMLPanel commentsPanel = new HTMLPanel(disqusCode);
								commentsPanel.setHeight("350px");
								Disqus.showComments(commentsPanel, postID);
								carContainer.add(commentsPanel);

							}

						} else {
							// Handle the error.  Can get the status text from response.getStatusText()
							Window.alert("Errore dal server:" + response.getStatusCode() +  response.getText());
						}
					}
				});
			} catch (RequestException e) {
				// Couldn't connect to server
				Window.alert("No connect!");
			}

		}

		public AboutPanel(String _visitedUser) {

			this.setSize("100%", "100%");

			add(carContainer);
			
			//add(insertPostPanel);

			carContainer.setSize("100%", "10%");
			commentContainerPanel.setSize("100%", "40%");
			//insertPostPanel.getElement().setAttribute("align", "bottom");
			//verticalPanel_1.setCellVerticalAlignment(verticalPanel_2, HasVerticalAlignment.ALIGN_BOTTOM);
			//verticalPanel_2.setSize("100%", "119px");

			this.visitedUser = _visitedUser;

			//car.setStyleName("btn-success");
			//Recupero dati dei post
			recoverWallDataRequest("GET","/ReceiveWallDataServlet?user=" + Cookies.getCookie("userCookie"));

			//Inserimento dati per l'owner del profilo
			if(Cookies.getCookie("userCookie").equals(visitedUser)){

				//Scelta categoria
				widget = new ListBox();
				widget.addStyleName("demo-ListBox");

				widget.addItem("News personali");

				widget.addItem("Eventi/Iniziative in parrocchia");
				widget.addItem("Catechismo");
				widget.addItem("Varie ed eventuali");

				//Coloriamo le categorie
				NodeList<Node> children = widget.getElement().getChildNodes();       
				for (int i = 0; i< children.getLength();i++) {
					Node child = children.getItem(i);
					if (child.getNodeType()==Node.ELEMENT_NODE) {
						if (child instanceof OptionElement) {
							OptionElement optionElement = (OptionElement) child;
							optionElement.getStyle().setFontWeight(FontWeight.BOLD);

							if (optionElement.getValue().equals("News personali")){
								optionElement.getStyle().setBackgroundColor("#3276B1");  
								optionElement.getStyle().setColor("white");  
							}
							if (optionElement.getValue().equals("Eventi/Iniziative in parrocchia")) {
								optionElement.getStyle().setBackgroundColor("#47a447");
								optionElement.getStyle().setColor("white");  	
							}
							if (optionElement.getValue().equals("Catechismo")){ 
								optionElement.getStyle().setBackgroundColor("#F0AD4E");
								optionElement.getStyle().setColor("white");  
							}
							if (optionElement.getValue().equals("Varie ed eventuali")) {
								optionElement.getStyle().setBackgroundColor("#D9534F");
								optionElement.getStyle().setColor("white");  
							}

						}
					}           
				}

				insertPostPanel.add(widget);
				widget.setWidth("100%");

				//Inserimento dati
				postTextArea = new TextArea();
				String toShow = "Inserisci qui il tuo commento...";
				postTextArea.setText(toShow);
				postTextArea.addClickHandler(new ClickHandler(){
					String toShow = "Inserisci qui il tuo commento...";
					@Override
					public void onClick(ClickEvent event) {
						if(((TextArea) event.getSource()).getText().equals(toShow)){
							((TextArea) event.getSource()).setText("");
							postTextArea.setHeight("100px");
						}
					}

				});

				insertPostPanel.add(postTextArea);
				postTextArea.setHeight("40px");
				postTextArea.setWidth("100%");
				com.cleanform.gwt.bootstrap.client.ui.Button insButton = 
						new com.cleanform.gwt.bootstrap.client.ui.Button("Inserisci", ButtonType.PRIMARY);
				//TODO: formattare i dati nella textarea in JSON e inviarli con .toString()
				

				insButton.addClickHandler(new SendWallDataHandler());

				insertPostPanel.add(insButton);

				insertPostPanel.setCellHorizontalAlignment(insButton, HasHorizontalAlignment.ALIGN_CENTER);

				commentContainerPanel.add(insertPostPanel);
			
				insertPostPanel.setSize("100%", "100px");
				//setCellVerticalAlignment(insertPostPanel,HasVerticalAlignment.ALIGN_BOTTOM);
			}
			

			add(commentContainerPanel);

		}


		//Invia i dati della bacheca alla servlet
		public class SendWallDataHandler implements ClickHandler, KeyUpHandler {

			private void doAPost(String url, String postData) {
				RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

				try { 
					@SuppressWarnings("unused")
					Request response = builder.sendRequest(postData,
							new RequestCallback() {

						public void onError(Request request, Throwable exception) {
							Window.alert(exception.getMessage());
						}

						public void onResponseReceived(Request request,
								Response response) {
							if (200 == response.getStatusCode()) {

								Window.alert("post pubblicato con successo!");
								centerVerticalPanel.clear();
								AboutPanel centerBasicData = null;

								centerBasicData = new AboutPanel(visitedUser);

								//Rimuovo il widget centrale (l'ultimo inserito)

								centerVerticalPanel.add(centerBasicData);

							} else {
								Window.alert("errore nell'invio del post");
							}
						}
					});

				} catch (RequestException e) {
					Window.alert("Errore nell'invio della richiesta: " + e.getMessage());
				}

			}
			String sentJSONdata = null;

			public SendWallDataHandler() {

			}

			public void onClick(ClickEvent event) {
				Button button = (Button) event.getSource();


				JSONArray postData = new JSONArray();

				JSONObject postingUser = new JSONObject();
				postingUser.put("postingUser", new JSONString(Cookies.getCookie("userCookie")));

				JSONObject postType = new JSONObject();
				postType.put("postType", new JSONNumber(widget.getSelectedIndex()));

				JSONObject postContent = new JSONObject();
				postContent.put("postContent", new JSONString(postTextArea.getValue()));

				postData.set(0, postingUser);
				postData.set(1, postType);
				postData.set(2, postContent);

				//Richiedo i dati del profilo da visitare
				doAPost("/ReceiveWallDataServlet", postData.toString());	
			}

			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub

			}

		}
	}


	//Pannello amici centrale
	public class FriendListPanel extends ScrollPanel {

		FlexTable flexTable = null;

		public FriendListPanel(String visitedUser) {

			flexTable = new FlexTable();
			flexTable.setStyleName("flexTableCentered");

			getFriendsRESTRequest("GET","/HandleFriendsServlet?getFriendsOf=" + visitedUser);

		}

		private void getFriendsRESTRequest(String httpMethod, String url) {
			RequestBuilder builder = null;
			if(httpMethod.equals("GET"))
				builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

			int respStatus = 0;

			try {
				Request request = builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						// Couldn't connect to server (could be timeout, SOP violation, etc.)
						//sendButton.setEnabled(false);
						Window.alert("Errore Client!");
					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							if(response.getText().startsWith("Errore"))
								Window.alert("Non hai ancora amici. Cosa aspetti ad aggiungerne?!");
							else{
								JSONObject foundUsers = (JSONObject) JSONParser.parseStrict(response.getText());
								//prendo il primo elemento trovato
								for(int k = 0; k < foundUsers.size(); k++){
									com.google.gwt.json.client.JSONArray friendDataJSONArray =  (com.google.gwt.json.client.JSONArray) foundUsers.get("result" + k);
									//Window.alert("Ricevuti dati dello user: " + ((JSONObject) userDataJSONArray.get(0)).get("user").isString().stringValue());

									Image contactImage = new Image();
									contactImage.setSize("45px", "45px");
									contactImage.setUrl("/images?imageType=profile&user=" + ((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue());

									contactImage.setTitle(((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue());
									//contactImage.addClickHandler(new VisitProfileHandler());

									flexTable = new FlexTable();

									int row = flexTable.getRowCount();

									flexTable.getCellFormatter().addStyleName(row,0,"gwt-FriendListTableFlex");
									flexTable.setWidget(row, 0, contactImage);

									//Seleziono il contatto
									Label contactLabel = new Label();
									contactLabel.setTitle(((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue());
									contactLabel.setStyleName("gwt-FacebookLike-Inverted");
									contactLabel.setSize("100%", "45px");
									contactLabel.addClickHandler(new VisitProfileHandler(((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue()));
									String contactCompleteName = ((JSONObject) friendDataJSONArray.get(1)).get("firstName").isString().stringValue() + " " + ((JSONObject) friendDataJSONArray.get(2)).get("secName").isString().stringValue();

									contactLabel.setText(contactCompleteName);

									flexTable.getCellFormatter().addStyleName(row,1,"gwt-FriendListTableFlex");
									flexTable.setWidget(row, 1, contactLabel);
									flexTable.getCellFormatter().setVerticalAlignment(row, 1,HasVerticalAlignment.ALIGN_MIDDLE );

									add(flexTable);
								}
							}
						} else {
							// Handle the error.  Can get the status text from response.getStatusText()
							Window.alert("Errore dal server:" + response.getStatusCode() +  response.getText());
						}
					}
				});
			} catch (RequestException e) {
				// Couldn't connect to server
				Window.alert("No connect!");
			}

		}
	}

	public class LogoutHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			//Così il server non sa che è sloggato; da migliorare....
			Cookies.removeCookie("userCookie");
			Window.Location.reload();
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			//TODO
		}
	}



	public class AddFriendHandler implements ClickHandler, KeyUpHandler {

		String visitedUser = null;

		public AddFriendHandler(String _visitedUser) {
			this.visitedUser = _visitedUser;
		}

		public void onClick(ClickEvent event) {
			Button button = (Button) event.getSource();
			//Window.alert("Titolo:" + button.getTitle());
			//Visito un profilo, se non è il mio
			//if(!Cookies.getCookie("userCookie").equals(button.getTitle()))	
			//Richiedo i dati del profilo da visitare
			addFriendRESTRequest("GET","/HandleFriendsServlet?user=" + Cookies.getCookie("userCookie") + "&addedFriend=" + visitedUser);	
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			//TODO
		}

		private void addFriendRESTRequest(String httpMethod, String url) {
			RequestBuilder builder = null;
			if(httpMethod.equals("GET"))
				builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

			int respStatus = 0;

			try {
				Request request = builder.sendRequest(null, new RequestCallback() {
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
								Window.alert(response.getText());
							}

						} else {
							// Handle the error.  Can get the status text from response.getStatusText()
							Window.alert("Errore dal server:" + response.getStatusCode() +  response.getText());
						}
					}
				});
			} catch (RequestException e) {
				// Couldn't connect to server
				Window.alert("No connect!");
			}

		}


	}
	public class VisitProfileHandler implements ClickHandler, KeyUpHandler {

		String visitedUser = null;

		public VisitProfileHandler(String stringValue) {


			this.visitedUser = stringValue;
		}

		public void onClick(ClickEvent event) {
			Window.alert("Apro profilo di: " + visitedUser);
			//Rendering del nuovo profilo
			loadEastWestSouthPanels(visitedUser);

		}
		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			//TODO
		}
	}	

	class SearchFriendHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			if(suggestBox.getText().length() > 0){
				flexTable.removeAllRows();
				searchFriendRESTRequest("GET","/SearchFriendServlet?searchData=" + suggestBox.getText());
			}
		}

		private void searchFriendRESTRequest(String httpMethod, String url) {
			RequestBuilder builder = null;
			if(httpMethod.equals("GET"))
				builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

			int respStatus = 0;

			try {
				Request request = builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						// Couldn't connect to server (could be timeout, SOP violation, etc.)
						//sendButton.setEnabled(false);
						Window.alert("Errore Client!");
					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							if(response.getText().startsWith("Errore"))
								Window.alert("Nessun utente trovato: riprova con nome, cognome o username!");
							else{
								JSONObject foundUsers = (JSONObject) JSONParser.parseStrict(response.getText());
								//prendo il primo elemento trovato
								for(int k = 0; k < foundUsers.size(); k++){
									com.google.gwt.json.client.JSONArray friendDataJSONArray =  (com.google.gwt.json.client.JSONArray) foundUsers.get("result" + k);
									//Window.alert("Ricevuti dati dello user: " + ((JSONObject) userDataJSONArray.get(0)).get("user").isString().stringValue());

									Image contactImage = new Image();
									contactImage.setSize("45px", "45px");
									contactImage.setUrl("/images?imageType=profile&user=" + ((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue());

									contactImage.setTitle(((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue());
									//contactImage.addClickHandler(new VisitProfileHandler());

									flexTable = new FlexTable();
									int row = flexTable.getRowCount();

									flexTable.setWidget(row, 0, contactImage);
									flexTable.getCellFormatter().addStyleName(row,0,"gwt-FriendListTableFlex");

									//Seleziono il contatto
									com.cleanform.gwt.bootstrap.client.ui.Button vistProfileButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Disconnetti");
									vistProfileButton.setStyleName("btn btn-link btn-lg");
									vistProfileButton.addClickHandler(new VisitProfileHandler(((JSONObject) friendDataJSONArray.get(0)).get("user").isString().stringValue()));
									String contactCompleteName = ((JSONObject) friendDataJSONArray.get(1)).get("firstName").isString().stringValue() + " " + ((JSONObject) friendDataJSONArray.get(2)).get("secName").isString().stringValue();
									vistProfileButton.setText(contactCompleteName);

									flexTable.setWidget(row, 1, vistProfileButton);
									//flexTable.getCellFormatter().addStyleName(row,1,"gwt-FriendListTableFlex");
									flexTable.getCellFormatter().setVerticalAlignment(row, 1,HasVerticalAlignment.ALIGN_MIDDLE );
									scrollPanel.setWidget(flexTable);
								}
							}
						} else {
							// Handle the error.  Can get the status text from response.getStatusText()
							Window.alert("Errore dal server:" + response.getStatusCode() +  response.getText());
						}
					}
				});
			} catch (RequestException e) {
				// Couldn't connect to server
				Window.alert("No connect!");
			}

		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			//TODO
		}

	}
	class LoginHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {

			loginToServer();
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			//TODO
		}

		private void loginToServer() {
			//NB: l'URL della webapp è quello (anche più di uno) specificato nel servlet mapping di war/WEB-INF/web.xml
			int respCode = makeRESTRequest("GET","/SubscribeServlet?user=" + txtbxUser.getText() + "&pw=" + txtbxPassword.getText());
		}


		private int makeRESTRequest(String httpMethod, String url) {
			//String url = "http://www.myserver.com/getData?type=3";
			RequestBuilder builder = null;
			if(httpMethod.equals("GET"))
				builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

			int respStatus = 0;

			try {
				Request request = builder.sendRequest(null, new RequestCallback() {
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
								Window.alert("Loggato: " + response.getText());
								userDataJSONArray = (JSONArray) JSONParser.parseStrict(response.getText());

								centerVerticalPanel.removeFromParent();
								renderLoggedInWidgets();
								loadEastWestSouthPanels(Cookies.getCookie("userCookie"));

							}

						} else {
							// Handle the error.  Can get the status text from response.getStatusText()
							Window.alert("Errore dal server:" + response.getStatusCode() +  response.getText());
						}
					}
				});
			} catch (RequestException e) {
				// Couldn't connect to server
				Window.alert("No connect!");
			}
			return 0;
		}

	}	

	public void onModuleLoad() {
		//registo JSNI

		
		//Via alla history
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();

				// Parse the history token
				try {
					switch(historyToken){

					case "homepage":
						loadInitTopPanels();
						renderLoggedInWidgets();

						loadEastWestSouthPanels(Cookies.getCookie("userCookie"));

						break;
					case "subscribe":
						loadInitTopPanels();
						centerVerticalPanel = new VerticalPanel();
						centerVerticalPanel.setWidth("100%");
						centerVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

						centerScrollable = new SubscribePanel();

						centerScrollable.form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

							//Il cookie dovrebbe essere arrivato, controllare
							public void onSubmitComplete(SubmitCompleteEvent event) {
								centerVerticalPanel.removeFromParent();

								renderLoggedInWidgets();
								loadEastWestSouthPanels(Cookies.getCookie("userCookie"));
								centerVerticalPanel.removeFromParent();//rimuovo pannello predef
							}
						});

						centerVerticalPanel.add(centerScrollable);
						splitPanel.add(centerVerticalPanel);
						RootLayoutPanel.get().add(splitPanel);
						break;
					case "materiale":

						//Rimozione pannelli centrali
						if(verticalPanel!= null)
							verticalPanel.removeFromParent();
						if(verticalPanel_1!= null)	
							verticalPanel_1.removeFromParent();
						if(centerVerticalPanel != null)
							centerVerticalPanel.removeFromParent();
						//Creo frame dropbox

						Frame frame = new Frame("./dropbox-prova.html");

						centerVerticalPanel.clear();
						centerVerticalPanel.add(frame);
						splitPanel.add(centerVerticalPanel);
						centerVerticalPanel.setSize("100%", "100%");
						frame.setSize("100%", "100%");

						break;

					}  

				} catch (IndexOutOfBoundsException e) {
					//carico homepage
					History.newItem("homepage");
				}
			}
		});


		//Cookies.getCookie("userCookie")!=null
		if(Cookies.getCookie("userCookie")!=null){
			//Window.alert("Ho un cookie: " + Cookies.getCookie("userCookie"));
			//Questa parte va inserita all'evento di click nel menu (Profilo)
			History.newItem("homepage");

		}
		else {
			//Mostro schermata di subscribe (default)
			History.newItem("subscribe");
		}

	}

	public void loadInitTopPanels() {

		//Pulizia iniziale
		RootLayoutPanel.get().clear();
		splitPanel.clear();
		//Cookies.removeCookie("userCookie");
		navPanel = new HorizontalPanel();
		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.setStyleName("gwt-FacebookLike");

		splitPanel.setSize("100%", "100%");



		// Add text all around.
		Label lblProba = new Label("faithbook");

		lblProba.setText("faithbook", Direction.LTR );
		lblProba.setStyleName("gwt-FacebookTitle");
		lblProba.setSize("150px", "60px");
		navPanel.add(lblProba);
		navPanel.setCellWidth(lblProba, "100%");
		navPanel.setCellVerticalAlignment(lblProba, HasVerticalAlignment.ALIGN_BOTTOM);
		navPanel.setSize("100%", "100%");
		lblCreditsLink_1 = new Label("Designed & Powered by Fabio Pini ");
		lblCreditsLink_1.setStyleName("gwt-FacebookLike-smallfont");


		txtbxUser = new TextBox();
		//txtbxUser.setText("User");
		txtbxUser.setTitle("User");
		//txtbxUser.setStylePrimaryName("gwt-FacebookTextBox");
		txtbxUser.setTextAlignment(TextBoxBase.ALIGN_LEFT);

		navPanel.add(txtbxUser);
		navPanel.setCellVerticalAlignment(txtbxUser, HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.setCellHorizontalAlignment(txtbxUser, HasHorizontalAlignment.ALIGN_RIGHT);
		txtbxUser.setSize("80px", "30px");

		txtbxPassword = new PasswordTextBox();
		txtbxPassword.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		//txtbxPassword.setText("Password");
		//txtbxPassword.setStyleName("gwt-FacebookTextBox");
		txtbxPassword.setAlignment(TextAlignment.LEFT);
		navPanel.add(txtbxPassword);
		navPanel.setCellHorizontalAlignment(txtbxPassword, HasHorizontalAlignment.ALIGN_RIGHT);
		navPanel.setCellVerticalAlignment(txtbxPassword, HasVerticalAlignment.ALIGN_MIDDLE);
		txtbxPassword.setSize("80px", "30px");
		sendButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Send",ButtonType.DEFAULT);
		sendButton.setStyleName("btn btn-default btn-sm");
		sendButton.setText("Accedi");
		sendButton.addClickHandler(new LoginHandler());
		navPanel.add(sendButton);
		navPanel.setCellVerticalAlignment(sendButton, HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.setCellHorizontalAlignment(sendButton, HasHorizontalAlignment.ALIGN_RIGHT);

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		splitPanel.addNorth(navPanel, 100.0);
		//Cookies.removeCookie("userCookie");
	}

	public void renderLoggedInWidgets() {

		welcomeLabel = new Label("Ciao " + Cookies.getCookie("userCookie") + "!");
		navPanel.setCellHorizontalAlignment(welcomeLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		navPanel.setCellVerticalAlignment(welcomeLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		//welcomeLabel.setSize("100px", "20px");
		navPanel.add(welcomeLabel);

		Image contactImage = new Image();
		contactImage.setSize("45px", "45px");
		contactImage.setStyleName("border: 2px solid white");
		contactImage.setUrl("/images?imageType=profile&user=" + Cookies.getCookie("userCookie"));
		navPanel.add(contactImage);

		//Ricarico bottone logout
		logoutButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Disconnetti",ButtonType.DEFAULT);

		logoutButton.addClickHandler(new LogoutHandler());

		logoutButton.setStyleName("btn btn-default btn-sm");
		navPanel.add(logoutButton);
		navPanel.setCellVerticalAlignment(logoutButton, HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.setCellHorizontalAlignment(logoutButton, HasHorizontalAlignment.ALIGN_RIGHT);

		StringBuilder sb = new StringBuilder();
		sb.append("<p><button class='btn btn-default'>Default</button> <button class='btn btn-primary'>Primary</button> "
				+ "<button class='btn btn-success'>Success</button> <button class='btn btn-info'>Info</button> "
				+ "<button class='btn btn-warning'>Warning</button> <button class='btn btn-danger'>Danger</button> "
				+ "<button class='btn btn-link'>Link</button></p>");
		//com.cleanform.gwt.bootstrap.client.ui.Button homeButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Home",ButtonType.PRIMARY);
		//com.cleanform.gwt.bootstrap.client.ui.Button newsButton = new com.cleanform.gwt.bootstrap.client.ui.Button("News",ButtonType.PRIMARY);
		//com.cleanform.gwt.bootstrap.client.ui.Button matButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Materiale",ButtonType.PRIMARY);

		menuPanel = new Navigator(NavType.PILLS);
		menuPanel.addItem("<b><font color='#3276B1'>Home</font></b>", "#homepage");
		menuPanel.addItem("<b><font color='#3276B1'>Blog</font></b>", "#blog");
		menuPanel.addItem("<b><font color='#3276B1'>Materiale</font></b>", "#materiale");

		//menuPanel.addActionHandler(new MenuPanelHandler());
		//layout.add(Layouts.as(menuPanel).addStyle(HelperStyles.THUMBNAIL));

		splitPanel.addNorth(menuPanel, 37.0);

	}
	public class MenuPanelHandler implements ActionHandler{


		public MenuPanelHandler(){

		}
		@Override
		public void onAction(ActionEvent event) {
			// TODO Auto-generated method stub
			String selectedItem = event.getAction();
			if(selectedItem	.equals("home")){
				History.newItem("homepage");

			}
			if(selectedItem.equals("materiale"))
			{
				History.newItem("materiale");

			}
		}

	}



}

