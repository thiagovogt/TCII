package br.com.vsl.VSLSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.repository.PublicationDBLP;
import br.com.vsl.VSLSystem.model.service.PublicationService;

public class PublicationServiceImpl implements PublicationService{
	
	/*
	 * M�todo respons�vel por ler um xml que cont�m uma lista com as chaves que representam cada publica��o do autor.
	 * 
	 */
	@Override
	public List<Publication> searchPublicationsByAuthor(String urlAuthorKey) throws DBLPException{
		List<Publication> publications = new ArrayList<Publication>();
		
		String currUrlKey = null;

		try {

			ByteArrayInputStream byteArray = new ByteArrayInputStream(PublicationDBLP.getInstance().searchPublicationsKeysByAuthor(urlAuthorKey));

			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(byteArray,"UTF-8");

			while (reader.hasNext()) {
				int event = reader.next();
				switch (event) {
					case XMLStreamConstants.CHARACTERS:
						if (!reader.getText().contains("homepages")) {
							currUrlKey = reader.getText().trim();
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
						if (!currUrlKey.equals(""))
							publications.add(new Publication(currUrlKey));
						break;
					case XMLStreamConstants.START_DOCUMENT:
						publications = new ArrayList<>();
						break;
				}

			}
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	    
		return publications;
	}

	/*
	 *
	 * M�todo respons�vel por buscar as informa��es de uma uma publica��o atrav�s de um xml com base em uma chave.
	 * 
	 */
	@Override
	public String searchPublication(Publication publication) throws DBLPException {
		
		try {
				
			return PublicationDBLP.getInstance().searchPublication(publication.getUrlKey());
			
		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	}
	
}
