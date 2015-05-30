package br.com.vsc.VSCSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.repository.AuthorDBLP;
import br.com.vsc.VSCSystem.model.service.AuthorService;

public class AuthorServiceImpl implements AuthorService{
	
	/*
	 *
	 * Método responsável por processar um xml e obter as informações de uma lista de nomes dos 
	 * autores que foram encontrados através do texto buscado referente ao nome ou sobrenome do autor.
	 * 
	 */
	@Override
	public List<Author> searchAuthorByName(String searchName) throws DBLPException{

		List<Author> authorsList = new ArrayList<Author>();
		String currName = null;
		String currUrlKey = null;

		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(AuthorDBLP.getInstance().searchAuthorByName(searchName));
			SAXBuilder builder = new SAXBuilder();
		    Document authorDocument = builder.build(byteArray);

		    Element authorsXml = authorDocument.getRootElement();
            
            List<Element> authorsListXml = authorsXml.getChildren("author");
            
            for (Element authorXml : authorsListXml) {
            	currUrlKey = authorXml.getAttributeValue("urlpt");
            	currName = authorXml.getText();
				authorsList.add(new Author(currName, currUrlKey));
			}
            
//		    XMLInputFactory factory = XMLInputFactory.newInstance();
//            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray, "UTF-8");
//            int event = xmlStreamReader.getEventType();
//            
//            while(true){
//                switch(event) {
//                case XMLStreamConstants.START_ELEMENT:
//                    if(xmlStreamReader.getLocalName().equals("author")){
//                    	currUrlKey = xmlStreamReader.getAttributeValue(0);
//                    }
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                        currName = xmlStreamReader.getText();
//                    break;
//                case XMLStreamConstants.END_ELEMENT:
//                    if(xmlStreamReader.getLocalName().equals("author")){
//                        authors.add(new Author(currName, currUrlKey));
//                    }
//                    break;
//                }
//                if (!xmlStreamReader.hasNext())
//                    break;
// 
//              event = xmlStreamReader.next();
//            }
			return authorsList;

		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	}
}
