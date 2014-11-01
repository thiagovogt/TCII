package br.com.vsl.VSLSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
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
	 * M�todo respons�vel por ler um xml que cont�m uma lista com as chaves que representam cada publica��o do autor.
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
	 * M�todo respons�vel por buscar as informa��es de uma uma publica��o atrav�s de um xml com base em uma chave.
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
                        isTitle=false;
                    }else if(isYear){
                    	publication.setYear(Integer.parseInt(xmlStreamReader.getText()));
                        isYear=false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
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
			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray);
			int event = xmlStreamReader.getEventType();
            while(true){
                switch(event) {
                case XMLStreamConstants.START_ELEMENT:
                	if(xmlStreamReader.getLocalName().equals("r")){
                		isPublication = true;
                	}else if(isPublication && !this.getPublicationType(xmlStreamReader.getLocalName()).equals("")){
                    	currPublication = new Publication(xmlStreamReader.getAttributeValue(0));
                		currPublication.setType(this.getPublicationType(xmlStreamReader.getLocalName()));
                    }else if(xmlStreamReader.getLocalName().equals("title")){
                    	isTitle = true;
                    }else if(xmlStreamReader.getLocalName().equals("year")){
                    	isYear = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                	if(isPublication){
                		if(isTitle){
                			currPublication.setTitle(xmlStreamReader.getText());
                			isTitle = false;
                		}else if(isYear){
                			currPublication.setYear(Integer.parseInt(xmlStreamReader.getText()));
                			isYear = false;
                		}
                	}
                    break;
                case XMLStreamConstants.END_ELEMENT:
                	if(xmlStreamReader.getLocalName().equals("r") && isPublication){
                		publications.add(currPublication);
                		currPublication = null;
                		isPublication = false;
                	}
                    break;
                }
                if (!xmlStreamReader.hasNext())
                    break;
 
              event = xmlStreamReader.next();
            }
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
}
