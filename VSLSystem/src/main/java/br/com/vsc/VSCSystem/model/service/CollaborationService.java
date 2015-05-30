package br.com.vsc.VSCSystem.model.service;

import java.util.List;

import br.com.vsc.VSCSystem.model.entity.Collaboration;
import br.com.vsc.VSCSystem.model.exception.DBLPException;

public interface CollaborationService {
	List<Collaboration> searchAuthorsCollaborations(String urlAuthorKey) throws DBLPException;
}
