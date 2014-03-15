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


public class AnimationFrameSequenceFile {
	
	/**
	 * Create empty new file and create frames for all .png's in directory.
	 * @param path Path to create file.
	 */
	public static void generateNewAnimtionFrameSequenceXmlFile(String path) {
		if (path == null) return;

		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			if (!path.endsWith(".xml")) {
				path = path + ".xml";
			}
			file = new File(path);
			ArrayList<AnimationFrame> createdFrames = new ArrayList<AnimationFrame>();
			for (String dirFileName : file.getParentFile().list()) {
				if (dirFileName.endsWith(".png")) {
					System.out.println("Auto-included png file in directory: " + dirFileName);
					createdFrames.add(new AnimationFrame(dirFileName, 0, 0, 1, "", ""));
				}
			}
			AnimationFrameSequenceFile.writeAnimationFrameSequenceToXml(path, createdFrames);
		}
	}
	
	
	public static ArrayList<AnimationFrame> createAnimationFrameSequenceFromXml(String path) {

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
    	    	int offsetX = 0;
    	    	int offsetY = 0;
    	    	int tics = 1;
    	    	String tag = "";
    	    	String next = "";
	    		
    	    	for (int j = 0; j < attributes.getLength(); j++) {
    	    		String attribute = attributes.item(j).getNodeName();
    	    		String value = attributes.item(j).getNodeValue();
    	    		
    	    		if (attribute.equals("image")) {
    	    			image = value;
    	    		} else if (attribute.equals("offsetX")) {
    	    			offsetX = Integer.parseInt(value);
    	    		} else if (attribute.equals("offsetY")) {
    	    			offsetY = Integer.parseInt(value);
    	    		} else if (attribute.equals("tics")) {
    	    			tics = Integer.parseInt(value);
    	    		} else if (attribute.equals("tag")) {
    	    			tag = value;
    	    		} else if (attribute.equals("next")) {
    	    			next = value;
    	    		} 
    	    	}
    	    	frames.add(new AnimationFrame(image, offsetX, offsetY, tics, tag, next));
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
	
	public static boolean writeAnimationFrameSequenceToXml(String path, final ArrayList<AnimationFrame> animationFrames) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("frames");
			doc.appendChild(rootElement);

			for (AnimationFrame animationFrame : animationFrames) {
				Element frameElement = doc.createElement("AnimationFrame");
				rootElement.appendChild(frameElement);
				
				Attr imageAttr = doc.createAttribute("image");
				imageAttr.setValue(animationFrame.getImage());
				frameElement.setAttributeNode(imageAttr);

				Attr offsetXAttr = doc.createAttribute("offsetX");
				offsetXAttr.setValue(new Integer(animationFrame.getOffsetX()).toString());
				frameElement.setAttributeNode(offsetXAttr);
				
				Attr offsetYAttr = doc.createAttribute("offsetY");
				offsetYAttr.setValue(new Integer(animationFrame.getOffsetY()).toString());
				frameElement.setAttributeNode(offsetYAttr);
				
				Attr ticsAttr = doc.createAttribute("tics");
				ticsAttr.setValue(new Integer(animationFrame.getTics()).toString());
				frameElement.setAttributeNode(ticsAttr);
				
				if (!animationFrame.getTag().isEmpty()) {
					Attr tagAttr = doc.createAttribute("tag");
					tagAttr.setValue(animationFrame.getTag());
					frameElement.setAttributeNode(tagAttr);
				}
				
				if (!animationFrame.getNext().isEmpty()) {
					Attr nextAttr = doc.createAttribute("next");
					nextAttr.setValue(animationFrame.getNext());
					frameElement.setAttributeNode(nextAttr);
				}
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
