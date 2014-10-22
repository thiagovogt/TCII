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
import br.com.vsl.VSLSystem.model.exception.DBLPException;

public class AuthorDBLP {
	
    private static AuthorDBLP instance;
    private static String DBLP_SEARCH_AUTHOR_URL = "http://dblp.uni-trier.de/search/author?xauthor=";
    		
    private AuthorDBLP() {
    }

    static public AuthorDBLP getInstance() {
        if (instance == null) {
            instance = new AuthorDBLP();
        }
        return (instance);
    }
    
    public List<Author> searchAuthorByName(String searchName) throws DBLPException {
	    List<Author> authors = null;
	    String currName = null;
	    String currUrlKey = null;  
	    
	    try{
	    	XMLInputFactory factory = XMLInputFactory.newInstance();
	    	String charset = "UTF-8";
	    	URL url = new URL(DBLP_SEARCH_AUTHOR_URL + URLEncoder.encode(searchName, charset));
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
	    	
    		byte[] byteArray = xml.getBytes(charset);
	    	
	    	XMLStreamReader reader = 
	    			factory. createXMLStreamReader(new ByteArrayInputStream(byteArray),charset);
	    	while(reader.hasNext()){
	    		int event = reader.next();

	    		switch(event){
	    		case XMLStreamConstants.START_ELEMENT: 
	    			if ("author".equals(reader.getLocalName())){
	    				currUrlKey = reader.getAttributeValue(0);
	    			}
	    			if("authors".equals(reader.getLocalName())){
	    				authors = new ArrayList<>();
	    			}
	    			break;
	    		case XMLStreamConstants.CHARACTERS:
	    			currName = reader.getText().trim();
	    			break;
	    		case XMLStreamConstants.END_ELEMENT:
	    			if(!currName.equals("") && !currUrlKey.equals(""))
	    				authors.add(new Author(currName, currUrlKey));
	    			break;
	    		case XMLStreamConstants.START_DOCUMENT:
	    			authors = new ArrayList<>();
	    			break;
	    		}

	    	}

	    	return authors;
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Erro ao processar XML : " + e.getMessage(), e);
    	}
    }
}
