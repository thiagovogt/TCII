package br.com.vsc.VSCSystem.model.repository;

import java.net.URL;

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
     * Método responsável por realizar uma requisição HTTP para o repositório do DBLP e retornar 
     * o xml da informação buscada relacionada as publicações do autor buscado.
     * Também é responsável por formatar o arquivo XML recebido, evitando erros na hora de realizar
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
