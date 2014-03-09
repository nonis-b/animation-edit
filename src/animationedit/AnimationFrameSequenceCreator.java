package animationedit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
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
	
	public static boolean writeAnimtionFrameSequenceToXml(final String path, final ArrayList<AnimationFrame> animationFrames) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("frames");
			doc.appendChild(rootElement);

			for (AnimationFrame animationFrame : animationFrames) {
				Element frameElement = doc.createElement("AnimationFrame");
				rootElement.appendChild(frameElement);
				Attr attr = doc.createAttribute("image");
				attr.setValue(animationFrame.getImage());
				frameElement.setAttributeNode(attr);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		}
		return true;
	}
}
