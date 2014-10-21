package br.com.vsl.VSLSystem.model.service.implementation;

import java.util.List;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.service.AuthorService;
import br.com.vsl.VSLSystem.model.repository.AuthorDBLP;

public class AuthorServiceImpl implements AuthorService{
	@Override
	public List<Author> searchAuthorByName(String searchName) throws DBLPException{
		return AuthorDBLP.getInstance().searchAuthorByName(searchName);
	}
}
