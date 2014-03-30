package com.example.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DateBox;
import com.ibm.icu.text.DateFormat;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class SubscribePanel extends ScrollPanel {

	
	public FormPanel form;
	/**
	 * @wbp.parser.constructor
	 */
	

	public SubscribePanel() {
		// TODO Auto-generated constructor stub

		form = new FormPanel();
		form.setAction("/SubscribeServlet");

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		// Create a panel to hold all of the form widgets.
		VerticalPanel panel = new VerticalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		form.setWidget(panel);
		panel.setSize("100%", "100%");

		Grid grid = new Grid(12, 2);
		grid.setBorderWidth(0);

		panel.setCellVerticalAlignment(grid, HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setCellHorizontalAlignment(grid, HasHorizontalAlignment.ALIGN_CENTER);
		grid.setSize("544px", "327px");

		Label label = new Label("User");
		label.setStyleName("gwt-FacebookLike");
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(0, 0, label);
		grid.getCellFormatter().setWidth(0, 0, "");
		grid.getCellFormatter().setStyleName(0, 0, "gwt-FacebookLike-smallfont");
		label.setSize("78px", "22px");

		TextBox textBox_6 = new TextBox();
		textBox_6.setName("User");
		textBox_6.setTitle("UserTextbox");
		textBox_6.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox_6.setText("");
		textBox_6.setStylePrimaryName("gwt-FacebookTextBox");
		textBox_6.setStyleName("gwt-FacebookTextBox");
		textBox_6.setAlignment(TextAlignment.JUSTIFY);
		grid.setWidget(0, 1, textBox_6);
		textBox_6.setWidth("212px");
		grid.getCellFormatter().setWidth(0, 1, "");

		Label lblPassword_1 = new Label("Password");
		lblPassword_1.setStyleName("gwt-FacebookLike");
		lblPassword_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(1, 0, lblPassword_1);
		grid.getCellFormatter().setStyleName(1, 0, "gwt-FacebookLike");
		lblPassword_1.setSize("78px", "19px");
		
		final PasswordTextBox passwordTextBox = new PasswordTextBox();
		passwordTextBox.setStyleName("gwt-FacebookTextBox");
		passwordTextBox.setName("Pw");
		grid.setWidget(1, 1, passwordTextBox);
		passwordTextBox.setWidth("212px");
		Label lblProba = new Label("Nome");
		grid.setWidget(2, 0, lblProba);
		grid.getCellFormatter().setStyleName(2, 0, "gwt-FacebookLike");
		lblProba.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblProba.setText("Nome", Direction.LTR );
		lblProba.setStyleName("gwt-FacebookLike");
		lblProba.setSize("78px", "19px");
		// Create a TextBox, giving it a name so that it will be submitted.
		final TextBox txtbxGigi = new TextBox();
		txtbxGigi.setName("FirstName");
		grid.setWidget(2, 1, txtbxGigi);
		txtbxGigi.setWidth("212px");
		txtbxGigi.setText("");
		txtbxGigi.setTitle("FirstNameTextbox");
		txtbxGigi.setStylePrimaryName("gwt-FacebookTextBox");
		txtbxGigi.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		txtbxGigi.setStyleName("gwt-FacebookTextBox");
		txtbxGigi.setAlignment(TextAlignment.JUSTIFY);

		Label lblPassword = new Label("Cognome");
		grid.setWidget(3, 0, lblPassword);
		grid.getCellFormatter().setStyleName(3, 0, "gwt-FacebookLike");
		lblPassword.setStyleName("gwt-FacebookLike");
		lblPassword.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblPassword.setSize("78px", "19px");

		TextBox textBox = new TextBox();
		textBox.setName("SecName");
		textBox.setTitle("SecNameTextbox");
		textBox.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox.setText("");
		textBox.setStylePrimaryName("gwt-FacebookTextBox");
		textBox.setStyleName("gwt-FacebookTextBox");
		textBox.setAlignment(TextAlignment.JUSTIFY);
		grid.setWidget(3, 1, textBox);
		textBox.setWidth("212px");

		Label lblSesso = new Label("Sesso");
		lblSesso.setStyleName("gwt-FacebookLike");
		lblSesso.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(4, 0, lblSesso);
		grid.getCellFormatter().setStyleName(4, 0, "gwt-FacebookLike");
		lblSesso.setSize("78px", "19px");

		ListBox listBox = new ListBox();
		listBox.setName("SexChoice");
		listBox.addItem("Maschio", "M");
		listBox.addItem("Femmina", "F");
		grid.setWidget(4, 1, listBox);

		Label lblEt = new Label("Data di nascita");
		lblEt.setStyleName("gwt-FacebookLike");
		lblEt.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(5, 0, lblEt);
		grid.getCellFormatter().setStyleName(5, 0, "gwt-FacebookLike");
		lblEt.setSize("100%", "100%");
		
		Date date = new Date();
		date.setYear(2000);
		date.setMonth(1);
		
		final DateTimeFormat format = DateTimeFormat.getFormat("dd.MM.yyyy");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		grid.setWidget(5, 1, horizontalPanel);
		
		ListBox comboBox_1 = new ListBox();
		comboBox_1.setName("giorno");
		comboBox_1.addItem("1");
		comboBox_1.addItem("2");
		comboBox_1.addItem("3");
		comboBox_1.addItem("4");
		comboBox_1.addItem("5");
		comboBox_1.addItem("6");
		comboBox_1.addItem("7");
		comboBox_1.addItem("8");
		comboBox_1.addItem("9");
		comboBox_1.addItem("10");
		comboBox_1.addItem("11");
		comboBox_1.addItem("12");
		comboBox_1.addItem("13");
		comboBox_1.addItem("14");
		comboBox_1.addItem("15");
		comboBox_1.addItem("16");
		comboBox_1.addItem("17");
		comboBox_1.addItem("18");
		comboBox_1.addItem("19");
		comboBox_1.addItem("20");
		comboBox_1.addItem("21");
		comboBox_1.addItem("22");
		comboBox_1.addItem("23");
		comboBox_1.addItem("24");
		comboBox_1.addItem("25");
		comboBox_1.addItem("26");
		comboBox_1.addItem("27");
		comboBox_1.addItem("28");
		comboBox_1.addItem("29");
		comboBox_1.addItem("30");
		comboBox_1.addItem("31");
		horizontalPanel.add(comboBox_1);
		comboBox_1.setWidth("53px");
		
		ListBox comboBox = new ListBox();
		comboBox.setName("mese");
		comboBox.addItem("Gennaio");
		comboBox.addItem("Febbraio");
		comboBox.addItem("marzo");
		comboBox.addItem("Aprile");
		comboBox.addItem("Maggio");
		comboBox.addItem("Giugno");
		comboBox.addItem("Luglio");
		comboBox.addItem("Agosto");
		comboBox.addItem("Settembre");
		comboBox.addItem("Ottobre");
		comboBox.addItem("Novembre");
		comboBox.addItem("Dicembre");
		horizontalPanel.add(comboBox);
		
		ListBox comboBox_2 = new ListBox();
		comboBox_2.addItem("1900");
		comboBox_2.addItem("1901");
		comboBox_2.addItem("1902");
		comboBox_2.addItem("1903");
		comboBox_2.addItem("1904");
		comboBox_2.addItem("1905");
		comboBox_2.addItem("1906");
		comboBox_2.addItem("1907");
		comboBox_2.addItem("1908");
		comboBox_2.addItem("1909");
		comboBox_2.addItem("1910");
		comboBox_2.addItem("1911");
		comboBox_2.addItem("1912");
		comboBox_2.addItem("1913");
		comboBox_2.addItem("1914");
		comboBox_2.addItem("1915");
		comboBox_2.addItem("1916");
		comboBox_2.addItem("1917");
		comboBox_2.addItem("1918");
		comboBox_2.addItem("1919");
		comboBox_2.addItem("1920");
		comboBox_2.addItem("1921");
		comboBox_2.addItem("1922");
		comboBox_2.addItem("1923");
		comboBox_2.addItem("1924");
		comboBox_2.addItem("1925");
		comboBox_2.addItem("1926");
		comboBox_2.addItem("1927");
		comboBox_2.addItem("1928");
		comboBox_2.addItem("1929");
		comboBox_2.addItem("1930");
		comboBox_2.addItem("1931");
		comboBox_2.addItem("1932");
		comboBox_2.addItem("1933");
		comboBox_2.addItem("1934");
		comboBox_2.addItem("1935");
		comboBox_2.addItem("1936");
		comboBox_2.addItem("1937");
		comboBox_2.addItem("1938");
		comboBox_2.addItem("1939");
		comboBox_2.addItem("1940");
		comboBox_2.addItem("1941");
		comboBox_2.addItem("1942");
		comboBox_2.addItem("1943");
		comboBox_2.addItem("1944");
		comboBox_2.addItem("1945");
		comboBox_2.addItem("1946");
		comboBox_2.addItem("1947");
		comboBox_2.addItem("1948");
		comboBox_2.addItem("1949");
		comboBox_2.addItem("1950");
		comboBox_2.addItem("1951");
		comboBox_2.addItem("1952");
		comboBox_2.addItem("1953");
		comboBox_2.addItem("1954");
		comboBox_2.addItem("1955");
		comboBox_2.addItem("1956");
		comboBox_2.addItem("1957");
		comboBox_2.addItem("1958");
		comboBox_2.addItem("1959");
		comboBox_2.addItem("1960");
		comboBox_2.addItem("1961");
		comboBox_2.addItem("1962");
		comboBox_2.addItem("1963");
		comboBox_2.addItem("1964");
		comboBox_2.addItem("1965");
		comboBox_2.addItem("1966");
		comboBox_2.addItem("1967");
		comboBox_2.addItem("1968");
		comboBox_2.addItem("1969");
		comboBox_2.addItem("1970");
		comboBox_2.addItem("1971");
		comboBox_2.addItem("1972");
		comboBox_2.addItem("1973");
		comboBox_2.addItem("1974");
		comboBox_2.addItem("1975");
		comboBox_2.addItem("1976");
		comboBox_2.addItem("1977");
		comboBox_2.addItem("1978");
		comboBox_2.addItem("1979");
		comboBox_2.addItem("1980");
		comboBox_2.addItem("1981");
		comboBox_2.addItem("1982");
		comboBox_2.addItem("1983");
		comboBox_2.addItem("1984");
		comboBox_2.addItem("1985");
		comboBox_2.addItem("1986");
		comboBox_2.addItem("1987");
		comboBox_2.addItem("1988");
		comboBox_2.addItem("1989");
		comboBox_2.addItem("1990");
		comboBox_2.addItem("1991");
		comboBox_2.addItem("1992");
		comboBox_2.addItem("1993");
		comboBox_2.addItem("1994");
		comboBox_2.addItem("1995");
		comboBox_2.addItem("1996");
		comboBox_2.addItem("1997");
		comboBox_2.addItem("1998");
		comboBox_2.addItem("1999");
		comboBox_2.addItem("2000");
		comboBox_2.addItem("2001");
		comboBox_2.addItem("2002");
		comboBox_2.addItem("2003");
		comboBox_2.addItem("2004");
		comboBox_2.addItem("2005");
		comboBox_2.addItem("2006");
		comboBox_2.addItem("2007");
		comboBox_2.addItem("2008");
		comboBox_2.addItem("2009");
		comboBox_2.addItem("2010");
		comboBox_2.addItem("2011");
		comboBox_2.addItem("2012");
		comboBox_2.addItem("2013");
		comboBox_2.setName("anno");
		horizontalPanel.add(comboBox_2);
		
		
		Label lblFotoProfilo = new Label("Foto");
		lblFotoProfilo.setStyleName("gwt-FacebookLike");
		lblFotoProfilo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(6, 0, lblFotoProfilo);
		grid.getCellFormatter().setStyleName(6, 0, "gwt-FacebookLike");
		lblFotoProfilo.setSize("100%", "100%");

		// Create a FileUpload widget.
		FileUpload upload = new FileUpload();
		upload.setStyleName("gwt-FacebookTextBox");
		grid.setWidget(6, 1, upload);
		grid.getCellFormatter().setWidth(6, 1, "");
		upload.setWidth("212px");
		upload.setName("uploadPhoto");

		Label lblEmail = new Label("Email");
		lblEmail.setStyleName("gwt-FacebookLike");
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(7, 0, lblEmail);
		grid.getCellFormatter().setStyleName(7, 0, "gwt-FacebookLike");
		lblEmail.setSize("78px", "19px");

		TextBox textBox_1 = new TextBox();
		textBox_1.setName("Email");
		grid.setWidget(7, 1, textBox_1);
		textBox_1.setWidth("212px");
		textBox_1.setTitle("EmailTextbox");
		textBox_1.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox_1.setText("");
		textBox_1.setStylePrimaryName("gwt-FacebookTextBox");
		textBox_1.setStyleName("gwt-FacebookTextBox");
		textBox_1.setAlignment(TextAlignment.JUSTIFY);
		grid.getCellFormatter().setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_LEFT);

		Label lblPaese = new Label("Paese");
		lblPaese.setStyleName("gwt-FacebookLike");
		lblPaese.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(8, 0, lblPaese);
		grid.getCellFormatter().setStyleName(8, 0, "gwt-FacebookLike");
		lblPaese.setSize("78px", "19px");

		TextBox textBox_7 = new TextBox();
		textBox_7.setName("Country");
		textBox_7.setTitle("CountryTextbox");
		textBox_7.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox_7.setText("");
		textBox_7.setStylePrimaryName("gwt-FacebookTextBox");
		textBox_7.setStyleName("gwt-FacebookTextBox");
		textBox_7.setAlignment(TextAlignment.LEFT);
		grid.setWidget(8, 1, textBox_7);
		textBox_7.setWidth("212px");

		Label lblCitt = new Label("Citt\u00E0");
		lblCitt.setStyleName("gwt-FacebookLike");
		lblCitt.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(9, 0, lblCitt);
		grid.getCellFormatter().setStyleName(9, 0, "gwt-FacebookLike");
		lblCitt.setSize("56px", "19px");

		TextBox textBox_3 = new TextBox();
		textBox_3.setName("City");
		textBox_3.setTitle("CityTextbox");
		textBox_3.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox_3.setText("");
		textBox_3.setStylePrimaryName("gwt-FacebookTextBox");
		textBox_3.setStyleName("gwt-FacebookTextBox");
		textBox_3.setAlignment(TextAlignment.LEFT);
		grid.setWidget(9, 1, textBox_3);
		textBox_3.setWidth("212px");

		Label lblIndirizzo = new Label("Indirizzo");
		lblIndirizzo.setStyleName("gwt-FacebookLike");
		lblIndirizzo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(10, 0, lblIndirizzo);
		grid.getCellFormatter().setStyleName(10, 0, "gwt-FacebookLike");
		lblIndirizzo.setSize("78px", "19px");

		TextBox textBox_2 = new TextBox();
		textBox_2.setName("Address");
		textBox_2.setTitle("AddressTextbox");
		textBox_2.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox_2.setText("");
		textBox_2.setStylePrimaryName("gwt-FacebookTextBox");
		textBox_2.setStyleName("gwt-FacebookTextBox");
		textBox_2.setAlignment(TextAlignment.JUSTIFY);
		grid.setWidget(10, 1, textBox_2);
		textBox_2.setWidth("212px");
		grid.getCellFormatter().setHorizontalAlignment(10, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(9, 1, HasHorizontalAlignment.ALIGN_LEFT);
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);

		// Add a 'submit' button.
		Button button = new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});
		button.setText("Subscribe");
		button.setStyleName("uibutton");
		panel.add(grid);
		
		Label lblLavorooccupazione = new Label("Lavoro/occupazione");
		lblLavorooccupazione.setStyleName("gwt-FacebookLike");
		lblLavorooccupazione.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(11, 0, lblLavorooccupazione);
		grid.getCellFormatter().setStyleName(11, 0, "gwt-FacebookLike");
		lblLavorooccupazione.setSize("78px", "19px");
		
		TextBox textBox_4 = new TextBox();
		textBox_4.setName("Job");
		textBox_4.setTitle("JobTextbox");
		textBox_4.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		textBox_4.setText("");
		textBox_4.setStylePrimaryName("gwt-FacebookTextBox");
		textBox_4.setStyleName("gwt-FacebookTextBox");
		textBox_4.setAlignment(TextAlignment.JUSTIFY);
		grid.setWidget(11, 1, textBox_4);
		textBox_4.setWidth("212px");


		// Add an event handler to the form.
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				// This event is fired just before the form is submitted. We can take
				// this opportunity to perform validation.
				if (txtbxGigi.getText().length() == 0 || passwordTextBox.getText().length() < 8) {
					Window.alert("Inserire user e password di almeno 8 caratteri");
					event.cancel();
				}
			}
		});
		
		panel.add(button);
		panel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
		
		add(form);
	}

	public SubscribePanel(Widget child) {
		super(child);
		// TODO Auto-generated constructor stub
	}

	public SubscribePanel(Element root, Element scrollable, Element container) {
		super(root, scrollable, container);
		// TODO Auto-generated constructor stub
	}

}
