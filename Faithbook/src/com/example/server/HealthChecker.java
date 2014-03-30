package com.example.server;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class HealthChecker
 */
public class HealthChecker extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String cfgFile = "webapps-settings.xml";
	private static String cfgFilePath = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HealthChecker() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		// USARE RIGA COMMENTATA PER IL DEPLOY!
		cfgFilePath = config.getServletContext().getRealPath("/") + "/";
		//cfgFilePath = "C:\\Documents and Settings\\u80275\\workspace\\HealthChecker4LoadBalancer\\";
		System.out.println("Path: " + cfgFilePath);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		return null;
	}

	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * @author U80275, Fabio Pini
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @return Una tabella html con gli status code HTTP relativi alle webapps
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	//Legge il file XML
	private Document readXmlFile() throws IOException {

		Document doc = null;
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(cfgFilePath + cfgFile));
			doc.getDocumentElement().normalize();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
