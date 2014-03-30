package com.example.client;

import com.cleanform.gwt.bootstrap.client.ui.Button.ButtonType;
import com.cleanform.gwt.bootstrap.client.ui.ListBox;
import com.cleanform.gwt.bootstrap.client.ui.TextArea;
import com.cleanform.gwt.bootstrap.js.client.ui.ButtonGroup;
import com.cleanform.gwt.bootstrap.js.client.ui.Carousel;


import com.cleanform.gwt.user.client.ActionEvent;
import com.cleanform.gwt.user.client.ActionEvent.ActionHandler;
import com.cleanform.gwt.user.client.ui.TabPanel;
import com.cleanform.gwt.user.layout.client.Layouts;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
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
	public ButtonGroup menuPanel = null;

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
			if(item.getTitle().equals("About")){
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
		trtmNewItem_1.setText("About");
		trtmNewItem_1.setTitle("About");
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
	public class AboutPanel extends ScrollPanel {
		ListBox widget = null;

		TextArea postTextArea = null;
		String visitedUser = null;

		public AboutPanel(String _visitedUser) {

			this.visitedUser = _visitedUser;

			VerticalPanel containerPanel = new VerticalPanel();

			containerPanel.setWidth("100%");
			containerPanel.setHeight("100%");
			Carousel car = new Carousel();
			//setContentWidget(car);
			//car.setStyleName("btn-success");
			car.addItem("<h3>Caption 1</h3>", Layouts.as(
					new com.cleanform.gwt.bootstrap.client.ui.Button("Item1", ButtonType.SUCCESS)).width().height(300));
			car.addItem("<h4>Caption 2</h4> something about item2...", Layouts.as(
					new com.cleanform.gwt.bootstrap.client.ui.Button("Item2", ButtonType.PRIMARY)).width().height(300));
			car.addItem("<h4>Caption 3</h4> something about item3...", Layouts.as(
					new com.cleanform.gwt.bootstrap.client.ui.Button("Item3", ButtonType.PRIMARY)).width().height(300));
			car.addItem("<h4 >Caption 4</h4> something about item4...", Layouts.as(
					new com.cleanform.gwt.bootstrap.client.ui.Button("Item4", ButtonType.PRIMARY)).width().height(300));
			car.setWidth("100%");
			//car.setStyleName("gwt-Carousel-Red");
			containerPanel.add(car);
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

							if (optionElement.getValue().equals("News personali"))
								optionElement.getStyle().setColor("#3276B1");  
							if (optionElement.getValue().equals("Eventi/Iniziative in parrocchia")) 
								optionElement.getStyle().setColor("#47a447");
							if (optionElement.getValue().equals("Catechismo")) 
								optionElement.getStyle().setColor("#F0AD4E");
							if (optionElement.getValue().equals("Varie ed eventuali")) 
								optionElement.getStyle().setColor("#D9534F");

						}
					}           
				}

				containerPanel.add(widget);
				//Inserimento dati
				postTextArea = new TextArea();


				containerPanel.add(postTextArea);
				postTextArea.setHeight("100px");

				com.cleanform.gwt.bootstrap.client.ui.Button insButton = 
						new com.cleanform.gwt.bootstrap.client.ui.Button("Inserisci", ButtonType.PRIMARY);
				//TODO: formattare i dati nella textarea in JSON e inviarli con .toString()


				insButton.addClickHandler(new SendWallDataHandler());

				containerPanel.add(insButton);


			}

			add(containerPanel);

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
		//NB STA ROBA MEGLIO DICHIARARLA SOPRA, COME sendButton
		final Label errorLabel = new Label();
		final CwConstants constants = null;

		visitedUser = null;
		//Cookies.removeCookie("userCookie");
		navPanel = new HorizontalPanel();
		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.setStyleName("gwt-FacebookLike");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element


		//splitPanel.ensureDebugId("cwSplitLayoutPanel");
		//splitPanel.getElement().getStyle().setProperty("border", "3px solid #e7e7e7");

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
		txtbxUser.setSize("80px", "20px");

		txtbxPassword = new PasswordTextBox();
		txtbxPassword.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		//txtbxPassword.setText("Password");
		//txtbxPassword.setStyleName("gwt-FacebookTextBox");
		txtbxPassword.setAlignment(TextAlignment.LEFT);
		navPanel.add(txtbxPassword);
		navPanel.setCellHorizontalAlignment(txtbxPassword, HasHorizontalAlignment.ALIGN_RIGHT);
		navPanel.setCellVerticalAlignment(txtbxPassword, HasVerticalAlignment.ALIGN_MIDDLE);
		txtbxPassword.setSize("80px", "20px");
		sendButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Send",ButtonType.DEFAULT);
		sendButton.setText("Accedi");
		sendButton.addClickHandler(new LoginHandler());
		navPanel.add(sendButton);
		navPanel.setCellVerticalAlignment(sendButton, HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.setCellHorizontalAlignment(sendButton, HasHorizontalAlignment.ALIGN_RIGHT);
		//sendButton.setStyleName("uibutton");
		//sendButton.setSize("58px", "20px");

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		//sendButton.addClickHandler(buttonHandler);
		//Cookies.removeCookie("userCookie");
		//Cookies.setCookie("userCookie", null);



		splitPanel.addNorth(navPanel, 100.0);

		//Cookies.getCookie("userCookie")!=null
		if(Cookies.getCookie("userCookie")!=null){
			//Window.alert("Ho un cookie: " + Cookies.getCookie("userCookie"));
			//Questa parte va inserita all'evento di click nel menu (Profilo)

			renderLoggedInWidgets();

			loadEastWestSouthPanels(Cookies.getCookie("userCookie"));

			//rootPanel.add(splitPanel);
		}
		else {
			//Mostro schermata di subscribe (default)

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
				}
			});

			centerVerticalPanel.add(centerScrollable);
			splitPanel.add(centerVerticalPanel);
			RootLayoutPanel.get().add(splitPanel);
		}

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


		com.cleanform.gwt.bootstrap.client.ui.Button homeButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Home",ButtonType.PRIMARY);
		com.cleanform.gwt.bootstrap.client.ui.Button newsButton = new com.cleanform.gwt.bootstrap.client.ui.Button("News",ButtonType.PRIMARY);
		com.cleanform.gwt.bootstrap.client.ui.Button matButton = new com.cleanform.gwt.bootstrap.client.ui.Button("Materiale",ButtonType.PRIMARY);

		menuPanel = new ButtonGroup();
		menuPanel.setStyleName("btn-justified");
		//Meglio usare tabPanel per usare i bottoni...
		//menuPanel = new TabPanel();
		//menuPanel.add(homeButton);
		//menuPanel.add(newsButton);
		//menuPanel.add(matButton);
		menuPanel.addButton("Home", "home");

		menuPanel.addButton("News", "news");
		menuPanel.addButton("Materiale", "materiale");
		menuPanel.addActionHandler(new MenuPanelHandler());
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
			if(selectedItem.equals("home"))
				loadEastWestSouthPanels(Cookies.getCookie("userCookie"));
			if(selectedItem.equals("materiale"))
			{
				//Rimozione pannelli centrali
				if(verticalPanel!=null)
					verticalPanel.removeFromParent();
				if(verticalPanel_1!=null)	
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

			}
		}

	}



}

