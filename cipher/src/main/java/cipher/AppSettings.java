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

public class AppSettings {
	private boolean web_sync;
	private String email_addy;
	private String app_password;
	
	public AppSettings() {
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
	            this.email_addy = att.getNamedItem("address").getTextContent();
	            this.app_password = att.getNamedItem("AppPassword").getTextContent();
	            if(att.getNamedItem("sync").getTextContent().contains("true")) {
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
	public static void makeSettings(boolean SYNC,String EMAIL,String APP_PASS) {
		try {
			
	         DocumentBuilderFactory dbFactory =
	                 DocumentBuilderFactory.newInstance();
	                 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	                 Document doc = dBuilder.newDocument();
             
             //root
             Element rootElement = doc.createElement("AppSettings");
             doc.appendChild(rootElement);
             //sub root
             Element settings = doc.createElement("settings");
             
             
             Attr sync = doc.createAttribute("Sync");
             Attr email = doc.createAttribute("address");
             Attr appPass = doc.createAttribute("AppPassword");
             
             if(SYNC) 
            	 sync.setValue("true"); 
             else 
            	 sync.setValue("false");
  
             email.setValue(EMAIL);
        	 appPass.setValue(APP_PASS);
             	 
        	 settings.setAttributeNode(sync);
        	 settings.setAttributeNode(email);
        	 settings.setAttributeNode(appPass);
             
             rootElement.appendChild(settings);
             // write the content into xml file
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();
             DOMSource source = new DOMSource(doc);
             StreamResult result = new StreamResult(new File("AppSettings.xml"));
             transformer.transform(source, result);
          // Output to console for testing
             StreamResult consoleResult = new StreamResult(System.out);
             transformer.transform(source, consoleResult);
        } catch (Exception e) {
           e.printStackTrace();
        }
	}

}
