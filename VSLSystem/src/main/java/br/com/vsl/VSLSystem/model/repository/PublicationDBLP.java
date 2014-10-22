package br.com.vsl.VSLSystem.model.repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;

public class PublicationDBLP {
	
    private static PublicationDBLP instance;
    		
    private PublicationDBLP() {
    }

    static public PublicationDBLP getInstance() {
        if (instance == null) {
            instance = new PublicationDBLP();
        }
        return (instance);
    }
    
    public List<Publication> searchPublicationsByAuthor(String urlKey) throws DBLPException {
	    List<Publication> publications = new ArrayList<Publication>();
	    String currName = null;
	    String currUrlKey = null;  
	    String authorName = null;  
	    
	    try{
	    	XMLInputFactory factory = XMLInputFactory.newInstance();
	    	String charset = "UTF-8";
	    	URL url = new URL("http://dblp.uni-trier.de/rec/pers/" + urlKey + "/xk");
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
	    	System.out.println(xml);
    		byte[] byteArray = xml.getBytes(charset);
	    	
	    	XMLStreamReader reader = 
	    			factory. createXMLStreamReader(new ByteArrayInputStream(byteArray),charset);
	    	while(reader.hasNext()){
	    		int event = reader.next();

	    		switch(event){
	    		case XMLStreamConstants.START_ELEMENT: 
    				if ("dblpperson".equals(reader.getLocalName())){
    					authorName = reader.getAttributeValue(0);
    					System.out.println(authorName);
    				}
	    			break;
	    		case XMLStreamConstants.CHARACTERS:
	    			if(!reader.getText().contains("homepages")){
	    				currUrlKey = reader.getText().trim();
	    				System.out.println(currUrlKey);
	    			}
	    			break;
//	    		case XMLStreamConstants.END_ELEMENT:
//	    			if(!currName.equals("") && !currUrlKey.equals(""))
//	    				publications.add(new Publication());
//	    			break;
//	    		case XMLStreamConstants.START_DOCUMENT:
//	    			publications = new ArrayList<>();
//	    			break;
	    		}

	    	}

	    	return publications;
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Erro ao processar XML : " + e.getMessage(), e);
    	}
    }
}
