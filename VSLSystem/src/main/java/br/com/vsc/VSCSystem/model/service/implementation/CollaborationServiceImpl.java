package br.com.vsc.VSCSystem.model.service.implementation;

import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.repository.CollaborationsDBLP;
import br.com.vsc.VSCSystem.model.service.CollaborationService;
import br.com.vsc.VSCSystem.model.utility.XmlParseUtils;

public class CollaborationServiceImpl implements CollaborationService{
	
	/*
	 * Método responsável por processar um xml e buscar as informações referentes as 
	 * colaborações em publicações do autor buscado e retornar uma lista de coautores 
	 * e quantidades de colaborações.
	 * 
	 * */
	public HashMap<Author, Integer> searchAuthorsCollaborations(String urlAuthorKey) throws DBLPException{
		HashMap<Author, Integer> coauthorsCollaborations = new HashMap<Author, Integer>();
		try {
			
		    Element coauthors = XmlParseUtils.getRootElement(CollaborationsDBLP.getInstance().searchAuthorsCollaborations(urlAuthorKey));
		    
		    
		    List<Element> authorsListXml = coauthors.getChildren("author");
		    
		    for (Element authorXml : authorsListXml) {
		    	
		    	String authorsName = authorXml.getText();
		    	String urlCoauthorKey = authorXml.getAttributeValue("urlpt");

		    	int count = Integer.valueOf(authorXml.getAttributeValue("count"));
		    	
		    	coauthorsCollaborations.put(new Author(authorsName,urlCoauthorKey), count);
			}
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	    return coauthorsCollaborations;
	}
}
