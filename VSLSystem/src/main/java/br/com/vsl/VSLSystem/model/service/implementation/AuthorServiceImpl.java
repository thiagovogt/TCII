package br.com.vsl.VSLSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.repository.AuthorDBLP;
import br.com.vsl.VSLSystem.model.service.AuthorService;

public class AuthorServiceImpl implements AuthorService{
	
	/*
	 *
	 * Classe responsável por ler um xml e obter as informações de uma lista de nomes dos autores que foram encontrados através da string buscada.
	 * 
	 */
	@Override
	public List<Author> searchAuthorByName(String searchName) throws DBLPException{

		List<Author> authors = new ArrayList<Author>();
		String currName = null;
		String currUrlKey = null;

		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(AuthorDBLP.getInstance().searchAuthorByName(searchName));
			XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(byteArray, "UTF-8");
            int event = xmlStreamReader.getEventType();
            while(true){
                switch(event) {
                case XMLStreamConstants.START_ELEMENT:
                    if(xmlStreamReader.getLocalName().equals("author")){
                    	currUrlKey = xmlStreamReader.getAttributeValue(0);
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                        currName = xmlStreamReader.getText();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if(xmlStreamReader.getLocalName().equals("author")){
                        authors.add(new Author(currName, currUrlKey));
                    }
                    break;
                }
                if (!xmlStreamReader.hasNext())
                    break;
 
              event = xmlStreamReader.next();
            }
			return authors;

		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	}
}
