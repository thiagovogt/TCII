package br.com.vsc.VSCSystem.model.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.entity.Publication;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.repository.PublicationDBLP;
import br.com.vsc.VSCSystem.model.service.PublicationService;
import br.com.vsc.VSCSystem.model.utility.XmlParseUtils;
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
		    Element dblpperson = XmlParseUtils.getRootElement(PublicationDBLP.getInstance().searchPublicationsByAuthor(urlAuthorKey));
		   
		    authorName = dblpperson.getAttributeValue("name");
		    
		    if(dblpperson.getAttribute("f") != null){
		    	return this.searchPublicationsByAuthor(dblpperson.getAttributeValue("f"));
		    }
		    
		    List<Element> publicationsListXml = dblpperson.getChildren("r");
		    
		    for (Element r : publicationsListXml) {
		    	
		    	Element publicationXml = r.getChildren().get(0);
		    	
		    	currPublication = new Publication(publicationXml.getAttributeValue("key"));
		    	
		    	currPublication.setType(publicationXml.getName());
		    	currPublication.setTitle(publicationXml.getChildText("title"));
		    	currPublication.setYear(Integer.parseInt(publicationXml.getChildText("year")));
		    	
		    	if(publicationXml.getChild("booktitle") != null){
		    		currPublication.setVenue(publicationXml.getChildText("booktitle"));
		    	}else if(publicationXml.getChild("journal") != null){
		    		currPublication.setVenue(publicationXml.getChildText("journal"));
		    	}else if(publicationXml.getChild("school") != null){
		    		currPublication.setVenue(publicationXml.getChildText("school"));
		    	}else if(publicationXml.getChild("publisher") != null){
		    		currPublication.setVenue(publicationXml.getChildText("publisher"));
		    	}
		    	
		    	String coAuthorType = "";
		    	if(!publicationXml.getChildren("author").isEmpty()){
		    		coAuthorType = "author";
		    	}else if(!publicationXml.getChildren("editor").isEmpty()) {
		    		coAuthorType = "editor";
		    		currPublication.setType("editor");
				}
		    	
		    	if(publicationXml.getChild("ee") != null){
		    		currPublication.setEePath(publicationXml.getChildText("ee"));
		    	}
		    	
		    	if(publicationXml.getChild("url") != null){
		    		currPublication.setUrlPath(publicationXml.getChildText("url"));
		    	}
		    	
		    	for (Element element : publicationXml.getChildren(coAuthorType)) {
		    		if(!element.getValue().equals(authorName) && !element.getValue().equals("\n")){
		    			currPublication.getCoAuthors().add(new Author(element.getText(), ""));
		    		}
		    	}
		    	
		    	publications.add(currPublication);
			}
		    return publications;
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	    
	}
	

}
