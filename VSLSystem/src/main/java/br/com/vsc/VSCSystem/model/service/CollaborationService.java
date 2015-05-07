package br.com.vsc.VSCSystem.model.service;

import java.util.HashMap;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;

public interface CollaborationService {
	HashMap<Author, Integer> searchAuthorsCollaborations(String urlAuthorKey) throws DBLPException;
}
