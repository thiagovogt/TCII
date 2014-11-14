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
    
    /*
     * M�todo respons�vel por realizar uma requisi��o http para o reposit�rio do DBLP e retornar o xml da informa��o buscada relacionada a uma lista de publica��es de um autor.
     */
//    public byte[] searchPublicationsKeysByAuthor(String urlKeyAuthor) throws DBLPException {
//	    try{
//	    	
//	    	URL url = new URL("http://dblp.uni-trier.de/rec/pers/" + urlKeyAuthor + "/xk");
//    		InputStream input = url.openStream();
//    		int ptr = 0;
//    		StringBuilder builder = new StringBuilder();
//    		while ((ptr = input.read()) != -1) {
//    		    builder.append((char) ptr);
//    		}
//    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
//    		
//	    	return xml.getBytes("UTF-8");
//    	    
//    	} catch (Exception e) {
//    		throw new DBLPException("Erro ao processar XML1 : " + e.getMessage(), e);
//    	}
//    }
    
    /*
     * M�todo respons�vel por realizar uma requisi��o http para o reposit�rio do DBLP e retornar o xml da informa��o buscada relacionada a uma publica��o.
     */
//    public byte[] searchPublication(String urlKeyPublication) throws DBLPException {
//    	try{
//    		
//    		URL url = new URL("http://dblp.uni-trier.de/rec/bibtex/"+urlKeyPublication+".xml");
//    		InputStream input = url.openStream();
//    		int ptr = 0;
//    		StringBuilder builder = new StringBuilder();
//    		while ((ptr = input.read()) != -1) {
//    			builder.append((char) ptr);
//    		}
////    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
//    		String xml = builder.toString();
//    		System.out.println(xml);
//    		return xml.getBytes("UTF-8");
//    		
//    	} catch (Exception e) {
//    		throw new DBLPException("Erro ao processar XML 2: " + e.getMessage(), e);
//    	}
//    }
//    
    
    /*
     * M�todo respons�vel por realizar uma requisi��o HTTP para o reposit�rio do DBLP e retornar 
     * o xml da informa��o buscada relacionada as publica��es do autor buscado.
     */
    public byte[] searchPublicationsByAuthor(String urlKeyAuthor) throws DBLPException {
	    try{
	    	
	    	URL url = new URL("http://dblp.uni-trier.de/pers/xx/" + urlKeyAuthor);
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
    		System.out.println(xml);
    		xml = xml.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    		
    		xml = xml.replace("<title>", "<title><![CDATA[");
    		xml = xml.replace("</title>", "]]></title>");
    		
    		xml = xml.replace("<booktitle>", "<booktitle><![CDATA[");
    		xml = xml.replace("</booktitle>", "]]></booktitle>");
    		
    		xml = xml.replace("<author>", "<author><![CDATA[");
    		xml = xml.replace("</author>", "]]></author>");
    		
    		xml = xml.replace("<journal>", "<journal><![CDATA[");
    		xml = xml.replace("</journal>", "]]></journal>");
    		
    		xml = xml.replace("<school>", "<school><![CDATA[");
    		xml = xml.replace("</school>", "]]></school>");
    		
	    	return xml.getBytes("UTF-8");
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Publications - Failed to process the XML: " + e.getMessage(), e);
    	}
    }
}
