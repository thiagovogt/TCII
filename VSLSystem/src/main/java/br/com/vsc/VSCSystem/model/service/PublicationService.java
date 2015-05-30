package br.com.vsc.VSCSystem.model.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.vsc.VSCSystem.model.entity.Publication;
import br.com.vsc.VSCSystem.model.exception.DBLPException;

public interface PublicationService {
	List<Publication> searchPublicationsByAuthor(String urlAuthorKey, HttpSession session) throws DBLPException;
//	Publication searchPublication(Publication publication) throws DBLPException;
}
