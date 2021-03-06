package cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * fortknox settings, only used for web sync options right now
 * 
 * @author logan.collier
 *
 */
public class AppSettings {
	
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private static Document doc = null;
	private boolean web_sync; //web sync on/off
	private String email_addy; //email address
	private String app_password; // the google app password generated by google
	private String email_inbox; //the inbox to search for; can set up for the passwords to be sent to a specified inbox on email
	private ArrayList<String> icon_files;
	
	public AppSettings() {
		this.dbFactory = DocumentBuilderFactory.newInstance();
		this.dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icon_files = new ArrayList<String>();
		getSettings();
	}
	public boolean isWeb_sync() {
		return web_sync;
	}
	public String getEmail_addy() {
		return email_addy;
	}
	public String getApp_password() {
		return app_password;
	}
	public String getEmail_inbox() {
		return this.email_inbox;
	}
	/****** SETTERS *******/
	public void set_webSync(boolean sync) {
		this.web_sync = sync;
	}
	public void set_email_address(String newAddress) {
		this.email_addy = newAddress;
	}
	public void set_app_password(String newPassword) {
		this.app_password = newPassword;
	}
	public void set_inbox(String newInbox) {
		this.email_inbox = newInbox;
	}
	public void updateSettings() throws SAXException, IOException {
		File file = new File("AppSettings.xml");
		if(file.exists()) {
		
	
				this.doc = dBuilder.parse(file);
			
				Node settings = doc.getElementsByTagName("settings").item(0);
				NamedNodeMap att = settings.getAttributes();
				// update staff attribute
				Node nodeAttr = att.getNamedItem("address");
				nodeAttr.setTextContent(this.email_addy);
				nodeAttr = att.getNamedItem("AppPassword");
				nodeAttr.setTextContent(this.app_password);
				nodeAttr = att.getNamedItem("inbox");
				nodeAttr.setTextContent(this.email_inbox);
				nodeAttr = att.getNamedItem("Sync");
				if(this.web_sync)
					nodeAttr.setTextContent("true");
				else
					nodeAttr.setTextContent("false");
				
				System.out.println("Settings changed successfully");
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
	             Transformer transformer = null;
				try {
					transformer = transformerFactory.newTransformer();
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             DOMSource source = new DOMSource(doc);
	             StreamResult result = new StreamResult(new File("AppSettings.xml"));
	             try {
					transformer.transform(source, result);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          // Output to console for testing
	             StreamResult consoleResult = new StreamResult(System.out);
	             try {
					transformer.transform(source, consoleResult);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             
			
		}
	}
	public void getSettings() {
		File file = new File("AppSettings.xml");
		if(file.exists()) {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Document doc = null;
			try {
				doc = dBuilder.parse(file);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doc.getDocumentElement().normalize();
			System.out.print("Root element: ");
			System.out.println(doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("settings");

			for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            System.out.println("\nCurrent Element :");
	            System.out.println(nNode.getNodeName());
	            NamedNodeMap att = nNode.getAttributes();
	            //web sync attributes
	            this.email_addy = att.getNamedItem("address").getTextContent();
	            this.app_password = att.getNamedItem("AppPassword").getTextContent();
	            this.email_inbox = att.getNamedItem("inbox").getTextContent();
	            System.out.println(att.getNamedItem("Sync").getNodeValue());
	            if(att.getNamedItem("Sync").getTextContent().contains("true")) {
	            	this.web_sync = true;
	            }else {
	            	this.web_sync = false;
	            }
	            //for(int i = 0; i< att.getLength();i++) {
	            //	Node t = att.item(i);
	            //	System.out.println(t.getNodeName()+" "+t.getNodeValue());
	           // }
			}
		}
	}
	public void make_settings(boolean SYNC,String EMAIL,String APP_PASS,String INBOX) {
		Document doc = dBuilder.newDocument();
        //root
        Element rootElement = doc.createElement("AppSettings");
        doc.appendChild(rootElement);
        //sub root web sync
        Element sync_settings = doc.createElement("Web Sync");
        make_webSync_settings( SYNC, EMAIL, APP_PASS, INBOX, doc, sync_settings);
        //add sync settings
        rootElement.appendChild(sync_settings);
        Element customize_settings = doc.createElement("Customize");
	}
	public void make_customize_settings(Document doc, Element customize_settings) {
		
	}
	/**
	 * create settings for web sync
	 * @param SYNC
	 * @param EMAIL
	 * @param APP_PASS
	 * @param INBOX
	 * @param doc
	 * @param settings
	 */
	public void make_webSync_settings(boolean SYNC,String EMAIL,String APP_PASS,String INBOX, Document doc, Element settings) {
        Attr sync = doc.createAttribute("Sync");
        Attr email = doc.createAttribute("address");
        Attr appPass = doc.createAttribute("AppPassword");
        Attr inbox = doc.createAttribute("inbox");
        if(SYNC) 
       	 sync.setValue("true"); 
        else 
       	 sync.setValue("false");

        email.setValue(EMAIL);
   	 	appPass.setValue(APP_PASS);
        inbox.setValue(INBOX);	
        
   	 	settings.setAttributeNode(sync);
   	 	settings.setAttributeNode(email);
   	 	settings.setAttributeNode(appPass);
        settings.setAttributeNode(inbox);
 
	}

	public void makeSettings(boolean SYNC,String EMAIL,String APP_PASS,String INBOX) throws TransformerException {

             //root
             Element rootElement = doc.createElement("AppSettings");
             doc.appendChild(rootElement);
             //sub root
             Element settings = doc.createElement("settings");
             
             
             Attr sync = doc.createAttribute("Sync");
             Attr email = doc.createAttribute("address");
             Attr appPass = doc.createAttribute("AppPassword");
             Attr inbox = doc.createAttribute("inbox");
             if(SYNC) 
            	 sync.setValue("true"); 
             else 
            	 sync.setValue("false");
  
             email.setValue(EMAIL);
        	 appPass.setValue(APP_PASS);
             inbox.setValue(INBOX);	
             
        	 settings.setAttributeNode(sync);
        	 settings.setAttributeNode(email);
        	 settings.setAttributeNode(appPass);
             settings.setAttributeNode(inbox);
             
             rootElement.appendChild(settings);
             
             Element iconSettings = doc.createElement("custimization");
             
             
             // write the content into xml file
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();
             DOMSource source = new DOMSource(doc);
             StreamResult result = new StreamResult(new File("AppSettings.xml"));
             transformer.transform(source, result);
          // Output to console for testing
             StreamResult consoleResult = new StreamResult(System.out);
             transformer.transform(source, consoleResult);

	}
	public Attr create_icon_att(String site, String filepath) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
        Attr ico = doc.createAttribute(site);
        ico.setValue(filepath);
        return ico;
	}
	

}
