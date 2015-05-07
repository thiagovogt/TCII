package br.com.vsc.VSCSystem.model.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class XmlParseUtils {
	public static byte[] getXmlBytesFromUrl(URL url, boolean containTagList) throws IOException{
		InputStream input = url.openStream();
		int ptr = 0;
		StringBuilder builder = new StringBuilder();
		while ((ptr = input.read()) != -1) {
		    builder.append((char) ptr);
		}
		String xml = StringEscapeUtils.unescapeHtml4(builder.toString());
		
		xml = xml.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml = xml.trim().replaceFirst("^([\\W]+)<","<");
		
		if(containTagList){
			List<String> tagListToFormat = new ArrayList<String>();
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
	    	
	    	for (String tagValue : tagListToFormat) {
	    		xml = xml.replace("<"+tagValue+">", "<"+tagValue+"><![CDATA[");
	    	    xml = xml.replaceAll("(" + "<"+tagValue + ".{0,})(?i)" + "\">", "$1" + "\"><![CDATA[");
	    	    
	    		xml = xml.replace("</"+tagValue+">", "]]></"+tagValue+">");
			}
		}
		
//    	System.out.println(xml);
		
		return xml.getBytes("UTF-8");
	}
	
	public static Element getRootElement(byte[] xmlBytes) throws IOException, JDOMException{
		ByteArrayInputStream byteArray = new ByteArrayInputStream(xmlBytes);
		SAXBuilder builder = new SAXBuilder();
	    Document document = builder.build(byteArray);
	    
	    return document.getRootElement();
	}

}
