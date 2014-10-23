package br.com.vsl.VSLSystem.model.service;

import java.util.List;

import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;

public interface PublicationService {
	List<Publication> searchPublicationsByAuthor(String urlAuthorKey) throws DBLPException;
	String searchPublication(Publication publication) throws DBLPException;
}
