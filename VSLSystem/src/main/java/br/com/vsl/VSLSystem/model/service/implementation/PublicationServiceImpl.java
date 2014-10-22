package br.com.vsl.VSLSystem.model.service.implementation;

import java.util.List;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.service.AuthorService;
import br.com.vsl.VSLSystem.model.service.PublicationService;
import br.com.vsl.VSLSystem.model.repository.AuthorDBLP;
import br.com.vsl.VSLSystem.model.repository.PublicationDBLP;

public class PublicationServiceImpl implements PublicationService{
	@Override
	public List<Publication> searchPublicationsByAuthor(String urlKey) throws DBLPException{
		return PublicationDBLP.getInstance().searchPublicationsByAuthor(urlKey);
	}
}
