package br.com.vsl.VSLSystem.model.repository;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.vsl.VSLSystem.model.exception.DBLPException;

public class AuthorDBLP {
	
    private static AuthorDBLP instance;
    
    private AuthorDBLP() {
    }

    static public AuthorDBLP getInstance() {
        if (instance == null) {
            instance = new AuthorDBLP();
        }
        return (instance);
    }
    
    /*
     * M�todo respons�vel por realizar uma requisi��o HTTP para o reposit�rio do DBLP e 
     * retornar o xml da informa��o buscada relacionada aos autores referentes ao texto buscado, 
     * que remete ao nome ou sobrenome de um autor.
     */
    public byte[] searchAuthorByName(String searchName) throws DBLPException {
    	
	    try{
	    	URL url = new URL("http://dblp.uni-trier.de/search/author?xauthor=" + URLEncoder.encode(searchName, "UTF-8"));
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
	    	
    		return xml.getBytes("UTF-8");
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Author - Failed to process the XML: " + e.getMessage(), e);
    	}
    }
}
