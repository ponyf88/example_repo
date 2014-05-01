package com.example.client;

import com.cleanform.gwt.bootstrap.client.ui.Label;
import com.cleanform.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BlogrollPanel extends VerticalPanel{
	
	private Label labelFeeds;
	private Label labelLinks;
	private StackPanel blogrollMenu = null;
	final String feedTitle = "Feed";
	final String linkTitle = "Blog collegati";
	
	public BlogrollPanel(){
		setStyleName("gwt-MenuPanelLeft");
		
		blogrollMenu = new StackPanel();
		
		labelFeeds.setStyleName("gwt-DisclosurePanel-header");
		labelLinks.setStyleName("gwt-DisclosurePanel-header");
		blogrollMenu.add(labelFeeds, feedTitle, false);
		//Aggiungi elenco di blog da cui prendere feed
		
		blogrollMenu.add(labelLinks, feedTitle, false);
		//aggiungi semplici link ai blog, ad es http://ilsicomoro.jimdo.com/
		
		Anchor linkSicomoro = new Anchor("http://ilsicomoro.jimdo.com/");
		
		blogrollMenu.add(linkSicomoro);
	}

}
