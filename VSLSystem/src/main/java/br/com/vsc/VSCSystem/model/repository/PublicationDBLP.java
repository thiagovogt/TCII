package br.com.vsc.VSCSystem.model.repository;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.utility.XmlParseUtils;

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
    
    /*
     * M�todo respons�vel por realizar uma requisi��o HTTP para o reposit�rio do DBLP e retornar 
     * o xml da informa��o buscada relacionada as publica��es do autor buscado.
     * Tamb�m � respons�vel por formatar o arquivo XML recebido, evitando erros na hora de realizar
     * o parse.
     */
    public byte[] searchPublicationsByAuthor(String urlKeyAuthor) throws DBLPException {
	    try{
	    	
	    	URL url = new URL("http://dblp.uni-trier.de/pers/xx/" + urlKeyAuthor);
    		
    		return XmlParseUtils.getXmlBytesFromUrl(url, true);
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Publications - Failed to process the XML: " + e.getMessage(), e);
    	}
    }
}
