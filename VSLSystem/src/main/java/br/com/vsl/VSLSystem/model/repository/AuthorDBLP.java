package br.com.vsl.VSLSystem.model.repository;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

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
    	List<Author> authors = new ArrayList<Author>();
    	
    	try{
    		
    		URL url = new URL(DBLP_SEARCH_AUTHOR_URL + searchName);
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = builder.toString();
    		
    		JSONObject jsonObject = (JSONObject) XML.toJSONObject(xml).get("authors");
    		JSONArray authorsArray = jsonObject.getJSONArray("author");
    	    
    	    for (int i = 0 ; i < authorsArray.length(); i++) {
    	        JSONObject obj = authorsArray.getJSONObject(i);
    	        Author author = new Author(obj.get("content").toString());
    	        authors.add(author);
    	    }
    
    	    return authors;
    	} catch (Exception e) {
    		throw new DBLPException("Erro ao processar XML : " + e.getMessage(), e);
    	}
    }
}
