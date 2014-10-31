package net.tapire_solutions.animationedit.animationeditgui;

import java.awt.Color;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Represents the current project configuration from a file.
 */
public class ApplicationConfig {

	// Values read from file.
	public Color frameViewTransperentAlphaColor;
	public Color frameViewBackgroundColor;
	public Color previewBackgroundColor;
	public String projectPath = ".";
	public String exportPath = ".";

	public ApplicationConfig(String path) {
		System.out.println("Loading config file " + path);
		
		if (path.endsWith(".xml")) {
			readFromXml(path);
		} else {
			System.out.println("Config file not xml. Ignoring.");
		}
	}
	
	private String getTextValueOfElement(String defaultValue, Element doc, String tag) {
		String value = defaultValue;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    } else {
	    	System.out.println("Couldn't find tag <" + tag + "> , using default value \"" + defaultValue + "\"");
	    }
	    return value;
	}

	private void readFromXml(String path) {
		Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(path);
            Element doc = dom.getDocumentElement();
            projectPath = getTextValueOfElement(projectPath, doc, "projectPath");
            exportPath = getTextValueOfElement(exportPath, doc, "exportPath");
            frameViewTransperentAlphaColor = new Color(Integer.parseInt(getTextValueOfElement("bbbbbb", doc, "frameViewTransperentAlphaColor"), 16));
            frameViewBackgroundColor = new Color(Integer.parseInt(getTextValueOfElement("777777", doc, "frameViewBackgroundColor"), 16));
            previewBackgroundColor = new Color(Integer.parseInt(getTextValueOfElement("555555", doc, "previewBackgroundColor"), 16));
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
	}
}
