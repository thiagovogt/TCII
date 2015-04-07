package br.com.vsc.VSCSystem;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.service.implementation.AuthorServiceImpl;

public class AuthorServiceImplTest extends TestCase{
	AuthorServiceImpl authorService = null; 
	List<Author> authors = null;
	
	@Before
	public void setUp() throws Exception {
		authorService = new AuthorServiceImpl(); 
		authors = new ArrayList<Author>();
	}

	@After
	public void tearDown() throws Exception {
		authorService = null;
		authors = null;
	}

	@Test
	public void testSearchAuthorByFirstName() throws DBLPException {
		String firstName = "Sabrina";
		authors = authorService.searchAuthorByName(firstName);
		
		assertTrue(authors.size() > 1);
	}
	
	@Test
	public void testSearchAuthorByFullName() throws DBLPException {
		String fullName = "Sabrina Marczak";
		authors = authorService.searchAuthorByName(fullName);
		
		assertTrue(authors.size() == 1);
	}
	
	@Test
	public void testSearchAuthorByWrongName() throws DBLPException {
		String wrongName = "aaHASkahshhh";
		authors = authorService.searchAuthorByName(wrongName);
		
		assertTrue(authors.size() == 0);
	}
	
	@Test
	public void testSearchAuthorByFullNameWithSpecialCharacters() throws DBLPException {
		String fullNameWithSpecialCharacteres = "Hans-Jörg Schek";
		authors = authorService.searchAuthorByName(fullNameWithSpecialCharacteres);
		
		assertTrue(authors.size() == 1);
	}

}
