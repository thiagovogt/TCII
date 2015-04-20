package br.com.vsc.VSCSystem.model.repository;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.vsc.VSCSystem.model.exception.DBLPException;

public class PublicationDBLP {
	
    private static PublicationDBLP instance;
    private List<String> tagListToFormat;		
    
    private PublicationDBLP() {
    	tagListToFormat = new ArrayList<String>();
    	tagListToFormat.add("title");
    	tagListToFormat.add("booktitle");
    	tagListToFormat.add("author");
    	tagListToFormat.add("editor");
    	tagListToFormat.add("number");
    	tagListToFormat.add("journal");
    	tagListToFormat.add("ee");
    	tagListToFormat.add("school");
    	tagListToFormat.add("year");
    	tagListToFormat.add("address");
    	tagListToFormat.add("volume");
    	tagListToFormat.add("month");
    	tagListToFormat.add("url");
    	tagListToFormat.add("cdrom");
    	tagListToFormat.add("cite");
    	tagListToFormat.add("publisher");
    	tagListToFormat.add("note");
    	tagListToFormat.add("crossref");
    	tagListToFormat.add("isbn");
    	tagListToFormat.add("series");
    	tagListToFormat.add("chapter");
    	tagListToFormat.add("pages");
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
    		InputStream input = url.openStream();
    		int ptr = 0;
    		StringBuilder builder = new StringBuilder();
    		while ((ptr = input.read()) != -1) {
    		    builder.append((char) ptr);
    		}
    		String xml = this.formatXml(StringEscapeUtils.unescapeHtml4(builder.toString()));
    		return xml.getBytes("UTF-8");
    	    
    	} catch (Exception e) {
    		throw new DBLPException("Publications - Failed to process the XML: " + e.getMessage(), e);
    	}
    }
    
    private String formatXml(String xmlText){
    	xmlText = xmlText.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	xmlText = xmlText.trim().replaceFirst("^([\\W]+)<","<");
    	for (String tagValue : tagListToFormat) {
    		xmlText = xmlText.replace("<"+tagValue+">", "<"+tagValue+"><![CDATA[");
    	    xmlText = xmlText.replaceAll("(" + "<"+tagValue + ".{0,})(?i)" + "\">", "$1" + "\"><![CDATA[");
    	    
    		xmlText = xmlText.replace("</"+tagValue+">", "]]></"+tagValue+">");
		}
    	return xmlText;
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
}
