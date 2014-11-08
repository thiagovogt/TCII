package br.com.vsl.VSLSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.repository.PublicationDBLP;
import br.com.vsl.VSLSystem.model.service.PublicationService;

public class PublicationServiceImpl implements PublicationService{
	
	/*
	 * Método responsável por ler um xml que contém uma lista com as chaves que representam cada publicação do autor.
	 * 
	 */
//	@Override
//	public List<Publication> searchPublicationsByAuthor(String urlAuthorKey) throws DBLPException{
//		List<Publication> publications = new ArrayList<Publication>();
//		
//		String currUrlKey = null;
//		boolean isPublication = false;
//		try {
//			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublicationsKeysByAuthor(urlAuthorKey));
//			XMLInputFactory factory = XMLInputFactory.newInstance();
//			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray,"UTF-8");
//			int event = xmlStreamReader.getEventType();
//            while(true){
//                switch(event) {
//                case XMLStreamConstants.START_ELEMENT:
//                    if(xmlStreamReader.getLocalName().equals("dblpkey") && 
//                    		xmlStreamReader.getAttributeCount() == 0){
//                    	isPublication = true;
//                    }
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                	if(isPublication){
//                		currUrlKey = xmlStreamReader.getText();
//                	}
//                    break;
//                case XMLStreamConstants.END_ELEMENT:
//                	if(xmlStreamReader.getLocalName().equals("dblpkey") && isPublication){
//                		publications.add(new Publication(currUrlKey));
//                		isPublication = false;
//                	}
//                    break;
//                }
//                if (!xmlStreamReader.hasNext())
//                    break;
// 
//              event = xmlStreamReader.next();
//            }
//			return publications;
//		} catch (Exception e) {
//			throw new DBLPException(e.getMessage(), e);
//		}
//	    
//	}

	/*
	 *
	 * Método responsável por buscar as informações de uma uma publicação através de um xml com base em uma chave.
	 * 
	 */
	@Override
	public Publication searchPublication(Publication publication) throws DBLPException {
		
		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublication(publication.getUrlKey()));
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray,"UTF-8");
			int event = xmlStreamReader.getEventType();	
			boolean isYear = false;
			boolean isTitle = false;
			while(true){
                switch(event) {
                case XMLStreamConstants.START_ELEMENT:
                    if(xmlStreamReader.getLocalName().equals("title")){
                    	isTitle=true;
                    }else if(xmlStreamReader.getLocalName().equals("year")){
                    	isYear=true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if(isTitle){
                    	publication.setTitle(xmlStreamReader.getText());
                    }else if(isYear){
                    	publication.setYear(Integer.parseInt(xmlStreamReader.getText()));
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                	if(xmlStreamReader.getLocalName().equals("title")){
                    	isTitle=false;
                    }else if(xmlStreamReader.getLocalName().equals("year")){
                    	isYear=false;
                    }
                    break;
                }
                if (!xmlStreamReader.hasNext())
                    break;
 
              event = xmlStreamReader.next();
            }
			
			return publication;
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	}
	
	public List<Publication> searchPublicationsByAuthor(String urlAuthorKey) throws DBLPException{
		List<Publication> publications = new ArrayList<Publication>();
		Publication currPublication = null;
		boolean isPublication = false;
		boolean isTitle = false;
		boolean isYear = false;
		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublicationsByAuthor(urlAuthorKey));
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray,"US-ASCII");
			     //
			    // Parse the XML
			    //
			    while(xmlStreamReader.hasNext()){
			      printEvent(xmlStreamReader);
			      xmlStreamReader.next();
			    }
			     //
			    // Close the reader
			    //
			    xmlStreamReader.close();
			
//			int event = xmlStreamReader.getEventType();
//            while(true){
//                switch(event) {
//                case XMLStreamConstants.START_ELEMENT:
//                	if(xmlStreamReader.getLocalName().equals("r")){
//                		isPublication = true;
//                	}else if(isPublication && !this.getPublicationType(xmlStreamReader.getLocalName()).equals("")){
//                    	currPublication = new Publication(xmlStreamReader.getAttributeValue(0));
//                		currPublication.setType(this.getPublicationType(xmlStreamReader.getLocalName()));
//                    }else if(xmlStreamReader.getLocalName().equals("title")){
//                    	isTitle = true;
//                    }else if(xmlStreamReader.getLocalName().equals("year")){
//                    	isYear = true;
//                    }
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                	if(isPublication){
//                		if(isTitle){
//                			currPublication.setTitle(xmlStreamReader.getText());
//                			isTitle = false;
//                		}else if(isYear){
//                			currPublication.setYear(Integer.parseInt(xmlStreamReader.getText()));
//                			isYear = false;
//                		}
//                	}
//                    break;
//                case XMLStreamConstants.END_ELEMENT:
//                	if(xmlStreamReader.getLocalName().equals("r") && isPublication){
//                		publications.add(currPublication);
//                		currPublication = null;
//                		isPublication = false;
//                	}
//                    break;
//                }
//                if (!xmlStreamReader.hasNext())
//                    break;
// 
//              event = xmlStreamReader.next();
//            }
			return publications;
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	    
	}
	
	private String getPublicationType(String typeCode){
		if(typeCode.equals("book")){
			return "Books and Theses";
		}else if(typeCode.equals("article")){
			return "Journal Article";
		}else if(typeCode.equals("inproceedings")){
			return "Conference and Workshop Papers";
		}else if(typeCode.equals("incollection")){
			return "Parts in Books or Collections";
		}else if(typeCode.equals("editor")){
			return "Editorship";
		}else if(typeCode.equals("reference")){
			return "Reference Works";
		}else if(typeCode.equals("informal")){
			return "Informal Publications";
		}else{
			return "";
		}
	}


	
	
	
	  private static String filename = null;
	   private static void printUsage() {
	    System.out.println("usage: java examples.basic.Parse <xmlfile>");
	  }
	   public static void main(String[] args) throws Exception {
	    try {
	      filename = args[0];
	    } catch (ArrayIndexOutOfBoundsException aioobe){
	      printUsage();
	      System.exit(0);
	    }
	     //
	    // Get an input factory
	    //
	    XMLInputFactory xmlif = XMLInputFactory.newInstance();
	    System.out.println("FACTORY: " + xmlif);
	     //
	    // Instantiate a reader
	    //
	    XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader(filename));
	    System.out.println("READER:  " + xmlr + "\n");
	     //
	    // Parse the XML
	    //
	    while(xmlr.hasNext()){
	      printEvent(xmlr);
	      xmlr.next();
	    }
	     //
	    // Close the reader
	    //
	    xmlr.close();
	   }
	   private static void printEvent(XMLStreamReader xmlr) {
	     System.out.print("EVENT:["+xmlr.getLocation().getLineNumber()+"]["+
	                     xmlr.getLocation().getColumnNumber()+"] ");
	     System.out.print(" [");
	     switch (xmlr.getEventType()) {
	     case XMLStreamConstants.START_ELEMENT:
	      System.out.print("<");
	      printName(xmlr);
	      printNamespaces(xmlr);
	      printAttributes(xmlr);
	      System.out.print(">");
	      break;
	     case XMLStreamConstants.END_ELEMENT:
	      System.out.print("</");
	      printName(xmlr);
	      System.out.print(">");
	      break;
	     case XMLStreamConstants.SPACE:
	     case XMLStreamConstants.CHARACTERS:
	      int start = xmlr.getTextStart();
	      int length = xmlr.getTextLength();
	      System.out.print(new String(xmlr.getTextCharacters(),
	                                  start,
	                                  length));
	      break;
	     case XMLStreamConstants.PROCESSING_INSTRUCTION:
	      System.out.print("<?");
	      if (xmlr.hasText())
	        System.out.print(xmlr.getText());
	      System.out.print("?>");
	      break;
	     case XMLStreamConstants.CDATA:
	      System.out.print("<![CDATA[");
	      start = xmlr.getTextStart();
	      length = xmlr.getTextLength();
	      System.out.print(new String(xmlr.getTextCharacters(),
	                                  start,
	                                  length));
	      System.out.print("]]>");
	      break;
	     case XMLStreamConstants.COMMENT:
	      System.out.print("<!--");
	      if (xmlr.hasText())
	        System.out.print(xmlr.getText());
	      System.out.print("-->");
	      break;
	     case XMLStreamConstants.ENTITY_REFERENCE:
	      System.out.print(xmlr.getLocalName()+"=");
	      if (xmlr.hasText())
	        System.out.print("["+xmlr.getText()+"]");
	      break;
	     case XMLStreamConstants.START_DOCUMENT:
	      System.out.print("<?xml");
	      System.out.print(" version='"+xmlr.getVersion()+"'");
	      System.out.print(" encoding='"+xmlr.getCharacterEncodingScheme()+"'");
	      if (xmlr.isStandalone())
	        System.out.print(" standalone='yes'");
	      else
	        System.out.print(" standalone='no'");
	      System.out.print("?>");
	      break;
	     }
	    System.out.println("]");
	  }
	   private static void printName(XMLStreamReader xmlr){
	    if(xmlr.hasName()){
	      String prefix = xmlr.getPrefix();
	      String uri = xmlr.getNamespaceURI();
	      String localName = xmlr.getLocalName();
	      printName(prefix,uri,localName);
	    }
	  }
	   private static void printName(String prefix,
	                                String uri,
	                                String localName) {
	    if (uri != null && !("".equals(uri)) ) System.out.print("['"+uri+"']:");
	    if (prefix != null) System.out.print(prefix+":");
	    if (localName != null) System.out.print(localName);
	  }
	   private static void printAttributes(XMLStreamReader xmlr){
	    for (int i=0; i < xmlr.getAttributeCount(); i++) {
	      printAttribute(xmlr,i);
	    }
	  }
	   private static void printAttribute(XMLStreamReader xmlr, int index) {
	    String prefix = xmlr.getAttributePrefix(index);
	    String namespace = xmlr.getAttributeNamespace(index);
	    String localName = xmlr.getAttributeLocalName(index);
	    String value = xmlr.getAttributeValue(index);
	    System.out.print(" ");
	    printName(prefix,namespace,localName);
	    System.out.print("='"+value+"'");
	  }
	   private static void printNamespaces(XMLStreamReader xmlr){
	    for (int i=0; i < xmlr.getNamespaceCount(); i++) {
	      printNamespace(xmlr,i);
	    }
	  }
	   private static void printNamespace(XMLStreamReader xmlr, int index) {
	    String prefix = xmlr.getNamespacePrefix(index);
	    String uri = xmlr.getNamespaceURI(index);
	    System.out.print(" ");
	    if (prefix == null)
	      System.out.print("xmlns='"+uri+"'");
	    else
	      System.out.print("xmlns:"+prefix+"='"+uri+"'");
	  }
}
