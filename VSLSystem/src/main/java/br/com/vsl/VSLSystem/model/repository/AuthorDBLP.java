package br.com.vsl.VSLSystem.model.repository;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringEscapeUtils;

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
    
    public byte[] searchAuthorByName(String searchName) throws DBLPException {
    	
	    try{
	    	URL url = new URL(DBLP_SEARCH_AUTHOR_URL + URLEncoder.encode(searchName, "UTF-8"));
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
}
