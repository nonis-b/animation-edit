package animationedit;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import framemodel.AnimationFrame;

public class AnimationFrameSequenceCreator {
	
	public static ArrayList<AnimationFrame> createAnimtionFrameSequenceFromXml(String path) {
		if (!path.endsWith(".xml")) {
			return null;
		}
		
		// frames
        ArrayList<AnimationFrame> frames = new ArrayList<AnimationFrame>();
		
		Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(path);
            Element doc = dom.getDocumentElement();
            
            NodeList frameElementList;
            frameElementList = doc.getElementsByTagName("AnimationFrame");
    	    for (int i = 0; i < frameElementList.getLength(); i++) {
    	    	
    	    	Node frameElement = frameElementList.item(i);
    	    	NamedNodeMap attributes = frameElement.getAttributes();
    	    	String image = null;
	    		
    	    	for (int j = 0; j < attributes.getLength(); j++) {
    	    		String attribute = attributes.item(j).getNodeName();
    	    		String value = attributes.item(j).getNodeValue();
    	    		
    	    		if (attribute.equals("image")) {
    	    			image = value;
    	    		}
    	    	}
    	    	frames.add(new AnimationFrame(image));
    	    }
    	    
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return frames;
	}
}
