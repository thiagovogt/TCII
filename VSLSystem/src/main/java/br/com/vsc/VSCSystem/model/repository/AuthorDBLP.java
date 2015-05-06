package br.com.vsc.VSCSystem.model.repository;

import java.net.URL;
import java.net.URLEncoder;

import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.utility.XmlParseUtils;

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

    		return XmlParseUtils.getXmlBytesFromUrl(url, false);
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Author - Failed to process the XML: " + e.getMessage(), e);
    	}
    }
}
