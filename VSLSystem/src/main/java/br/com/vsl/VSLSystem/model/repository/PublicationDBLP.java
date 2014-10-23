package br.com.vsl.VSLSystem.model.repository;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;

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
    
    public byte[] searchPublicationsKeysByAuthor(String urlKeyAuthor) throws DBLPException {
	    try{
	    	
	    	URL url = new URL("http://dblp.uni-trier.de/rec/pers/" + urlKeyAuthor + "/xk");
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
    		
	    	return xml.getBytes("UTF-8");
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Erro ao processar XML : " + e.getMessage(), e);
    	}
    }
    
    public String searchPublication(String urlKeyPublication) throws DBLPException {
    	try{
    		
    		URL url = new URL("http://dblp.uni-trier.de/rec/bibtex/"+urlKeyPublication+".xml");
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    			builder.append((char) ptr);
    		}
//    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
    		String xml = builder.toString();
    		return xml;
    		
    	} catch (Exception e) {
    		throw new DBLPException("Erro ao processar XML : " + e.getMessage(), e);
    	}
    }
}
