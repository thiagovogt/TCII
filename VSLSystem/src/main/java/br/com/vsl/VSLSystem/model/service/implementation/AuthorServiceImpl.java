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

		List<Author> authors = null;
		String currName = null;
		String currUrlKey = null;

		try {
			ByteArrayInputStream byteArray = new ByteArrayInputStream(AuthorDBLP.getInstance().searchAuthorByName(searchName));
			
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(byteArray, "UTF-8");
			while (reader.hasNext()) {
				int event = reader.next();

				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						if ("author".equals(reader.getLocalName())) {
							currUrlKey = reader.getAttributeValue(0);
						}
						if ("authors".equals(reader.getLocalName())) {
							authors = new ArrayList<>();
						}
						break;
					case XMLStreamConstants.CHARACTERS:
						currName = reader.getText().trim();
						break;
					case XMLStreamConstants.END_ELEMENT:
						if (!currName.equals("") && !currUrlKey.equals(""))
							authors.add(new Author(currName, currUrlKey));
						break;
					case XMLStreamConstants.START_DOCUMENT:
						authors = new ArrayList<>();
						break;
				}
			}

			return authors;

		} catch (Exception e) {
			throw new DBLPException(e.getMessage(), e);
		}
	}
}
