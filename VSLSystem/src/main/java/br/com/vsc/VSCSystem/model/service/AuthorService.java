package br.com.vsc.VSCSystem.model.service;

import java.util.List;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;

public interface AuthorService {
	List<Author> searchAuthorByName(String name) throws DBLPException;
}
