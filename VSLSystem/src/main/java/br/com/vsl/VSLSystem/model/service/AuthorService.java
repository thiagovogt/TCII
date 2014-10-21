package br.com.vsl.VSLSystem.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.exception.AuthorSearchException;
import br.com.vsl.VSLSystem.model.exception.DBLPException;

public interface AuthorService {
	List<Author> searchAuthorByName(String name) throws DBLPException;
}
