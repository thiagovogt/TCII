package br.com.vsc.VSCSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.entity.Publication;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.repository.PublicationDBLP;
import br.com.vsc.VSCSystem.model.service.PublicationService;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
public class PublicationServiceImpl implements PublicationService{
	
	
	
	/*
	 * Método responsável por processar um xml e buscar as informações referentes as 
	 * publicações do autor buscado e retornar uma lista de Publicações.
	 * 
	 * */
	public List<Publication> searchPublicationsByAuthor(String urlAuthorKey) throws DBLPException{
		List<Publication> publications = new ArrayList<Publication>();
		Publication currPublication = null;
		String authorName = "";

		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublicationsByAuthor(urlAuthorKey));
			SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(byteArray);

		    Element dblpperson = document.getRootElement();
		    authorName = dblpperson.getAttributeValue("name");
		    
		    if(dblpperson.getAttribute("f") != null){
		    	return this.searchPublicationsByAuthor(dblpperson.getAttributeValue("f"));
		    }
		    
		    List<Element> publicationsListXml = dblpperson.getChildren("r");
		    
		    for (Element r : publicationsListXml) {
		    	
		    	Element publicationXml = r.getChildren().get(0);
		    	
		    	currPublication = new Publication(publicationXml.getAttributeValue("key"));
		    	
		    	currPublication.setType(this.getPublicationType(publicationXml.getName()));
		    	currPublication.setTitle(this.formatTextValue(publicationXml.getChildText("title")));
		    	currPublication.setYear(Integer.parseInt(publicationXml.getChildText("year")));
		    	
		    	if(publicationXml.getChild("booktitle") != null){
		    		currPublication.setLocal(this.formatTextValue(publicationXml.getChildText("booktitle")));
		    	}else if(publicationXml.getChild("journal") != null){
		    		currPublication.setLocal(this.formatTextValue(publicationXml.getChildText("journal")));
		    	}else if(publicationXml.getChild("school") != null){
		    		currPublication.setLocal(this.formatTextValue(publicationXml.getChildText("school")));
		    	}
		    	
		    	String coAuthorType = "";
		    	if(!publicationXml.getChildren("author").isEmpty()){
		    		coAuthorType = "author";
		    	}else if(!publicationXml.getChildren("editor").isEmpty()) {
		    		coAuthorType = "editor";
		    		currPublication.setType(this.getPublicationType("editor"));
				}
		    	for (Element element : publicationXml.getChildren(coAuthorType)) {
		    		if(!element.getValue().equals(authorName) && 
    						!element.getValue().equals("\n")){
		    			currPublication.getCoAuthors().add(new Author(this.formatTextValue(element.getText()), ""));
		    		}
		    	}
		    	
		    	publications.add(currPublication);
			}
		
			
//			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublicationsByAuthor(urlAuthorKey));
//			XMLInputFactory factory = XMLInputFactory.newInstance();
//			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray,"UTF-8");
//			
//			int event = xmlStreamReader.getEventType();
//            while(true){
//                switch(event) {
//                case XMLStreamConstants.START_ELEMENT:
//                	if(xmlStreamReader.getLocalName().equals("dblpperson")){
//                		authorName = xmlStreamReader.getAttributeValue(0);
//                		
//                		if(xmlStreamReader.getAttributeCount() > 2){
//                			if(xmlStreamReader.getAttributeLocalName(2).equals("f")){
//                				aliasKey = xmlStreamReader.getAttributeValue(2);
//                			}
//                		}
//                	}else if(xmlStreamReader.getLocalName().equals("r")){
//                		isPublication = true;
//                	}else if(isPublication){ 
//	                	if(!this.getPublicationType(xmlStreamReader.getLocalName()).equals("")){
//	                    	if(xmlStreamReader.getAttributeValue(0).equals("informal publication") || 
//	                    			xmlStreamReader.getAttributeValue(0).equals("encyclopedia entry")){
//	                    		currPublication = new Publication(xmlStreamReader.getAttributeValue(1));
//	                    		currPublication.setType(this.getPublicationType(xmlStreamReader.getAttributeValue(0)) + " - " + this.getPublicationType(xmlStreamReader.getLocalName()));
//	                    	}else{
//	                    		currPublication = new Publication(xmlStreamReader.getAttributeValue(0));
//		                		currPublication.setType(this.getPublicationType(xmlStreamReader.getLocalName()));
//	                    	}
//	                    }else if(xmlStreamReader.getLocalName().equals("title")){
//	                    	isTitle = true;
//	                    }else if(xmlStreamReader.getLocalName().equals("author") ||
//	                    			xmlStreamReader.getLocalName().equals("editor")){
//	                    	isCoAuthor = true;
//	                    }else if(xmlStreamReader.getLocalName().equals("year")){
//	                    	isYear = true;
//	                    }else if(xmlStreamReader.getLocalName().equals("booktitle") || 
//	                    			xmlStreamReader.getLocalName().equals("journal") ||
//	                    				xmlStreamReader.getLocalName().equals("school")){
//	                    	isLocal = true;
//	                    }
//	                    break;
//                	}
//                case XMLStreamConstants.CHARACTERS:
//                	if(isPublication){
//                		if(isTitle){
////                			System.out.println(this.formatTextValue(xmlStreamReader.getText()));
//                			currPublication.setTitle(this.formatTextValue(xmlStreamReader.getText()));
//                			isTitle = false;
//                		} else if(isYear){
//                			currPublication.setYear(Integer.parseInt(xmlStreamReader.getText()));
//                			isYear = false;
//                		} else if(isLocal){
//                			currPublication.setLocal(this.formatTextValue(xmlStreamReader.getText()));
//                			isLocal = false;
//                		}else if(isCoAuthor){
//                			if(!xmlStreamReader.getText().equals(authorName) && 
//                						!xmlStreamReader.getText().equals("\n")){
//	                			currPublication.getCoAuthors().add(new Author(this.formatTextValue(xmlStreamReader.getText()), ""));
//                			}
//                			isCoAuthor = false;
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
//            if(publications.size() == 0 && !aliasKey.equals("")){
//            	return this.searchPublicationsByAuthor(aliasKey);
//            }else{
//            	return publications;
//            }
		    return publications;
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	    
	}
	
	/*
	 * 
	 * Define o tipo da publicação baseado na tag xml
	 * 
	 * */
	private String getPublicationType(String typeCode){
		if(typeCode.equals("book") || typeCode.equals("phdthesis") || typeCode.equals("masterthesis")){
			return "Books and Theses";
		}else if(typeCode.equals("article")){
			return "Journal Article";
		}else if(typeCode.equals("inproceedings") || typeCode.equals("proceedings")){
			return "Conference and Workshop Papers";
		}else if(typeCode.equals("incollection")){
			return "Parts in Books or Collections";
		}else if(typeCode.equals("editor")){
			return "Editorship";
		}else if(typeCode.equals("reference") || typeCode.equals("encyclopedia entry")){
			return "Reference Works";
		}else if(typeCode.equals("informal") || typeCode.equals("informal publication")){
			return "Informal Publications";
		}else{
			return "";
		}
	}
	
	/*
	 * 
	 * Formata o valor do texto substituindo todas as ocorrências de '\' por '\\'
	 * 
	 * */
	private String formatTextValue(String text){
		return text.replaceAll("\"", "\\\\\"");
	}
	
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
//	@Override
//	public Publication searchPublication(Publication publication) throws DBLPException {
//		
//		try {
//			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublication(publication.getUrlKey()));
//			XMLInputFactory factory = XMLInputFactory.newInstance();
//			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray,"UTF-8");
//			int event = xmlStreamReader.getEventType();	
//			boolean isYear = false;
//			boolean isTitle = false;
//			while(true){
//                switch(event) {
//                case XMLStreamConstants.START_ELEMENT:
//                    if(xmlStreamReader.getLocalName().equals("title")){
//                    	isTitle=true;
//                    }else if(xmlStreamReader.getLocalName().equals("year")){
//                    	isYear=true;
//                    }
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                    if(isTitle){
//                    	publication.setTitle(xmlStreamReader.getText());
//                    }else if(isYear){
//                    	publication.setYear(Integer.parseInt(xmlStreamReader.getText()));
//                    }
//                    break;
//                case XMLStreamConstants.END_ELEMENT:
//                	if(xmlStreamReader.getLocalName().equals("title")){
//                    	isTitle=false;
//                    }else if(xmlStreamReader.getLocalName().equals("year")){
//                    	isYear=false;
//                    }
//                    break;
//                }
//                if (!xmlStreamReader.hasNext())
//                    break;
// 
//              event = xmlStreamReader.next();
//            }
//			
//			return publication;
//		} catch (Exception e) {
//			throw new DBLPException(e.getMessage(), e);
//		}
//	}
}
