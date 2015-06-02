package br.com.vsc.VSCSystem.model.repository;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.vsc.VSCSystem.model.exception.DBLPException;

public class CollaborationDBLP {
	
    private static CollaborationDBLP instance;

    static public CollaborationDBLP getInstance() {
        if (instance == null) {
            instance = new CollaborationDBLP();
        }
        return (instance);
    }
    
    /*
     * Método responsável por realizar uma requisição HTTP para o repositório do DBLP e retornar 
     * o xml da informação buscada relacionada as colaborações do autor buscado.
     * Também é responsável por formatar o arquivo XML recebido, evitando erros na hora de realizar
     * o parse.
     */
    public byte[] searchAuthorsCollaborations(String urlKeyAuthor) throws DBLPException {
	    try{
	    	
	    	URL url = new URL("http://dblp.uni-trier.de/rec/pers/" + urlKeyAuthor + "/xc");
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = this.formatXml(StringEscapeUtils.unescapeHtml4(builder.toString()));
    		return xml.getBytes("UTF-8");
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Collaborations - Failed to process the XML: " + e.getMessage(), e);
    	}
    }
    
    private String formatXml(String xmlText){
    	xmlText = xmlText.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	xmlText = xmlText.trim().replaceFirst("^([\\W]+)<","<");
    	return xmlText;
    }  
}
