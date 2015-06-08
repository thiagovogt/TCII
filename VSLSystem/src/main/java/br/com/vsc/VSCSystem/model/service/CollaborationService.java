package br.com.vsc.VSCSystem.model.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.vsc.VSCSystem.model.entity.Collaboration;
import br.com.vsc.VSCSystem.model.exception.DBLPException;

public interface CollaborationService {
	List<Collaboration> searchAuthorsCollaborations(String urlAuthorKey, HttpSession session) throws DBLPException;
}
