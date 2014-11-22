package br.com.vsl.VSLSystem;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.service.implementation.PublicationServiceImpl;

public class PublicationServiceImplTest {
	PublicationServiceImpl publicationService = null; 
	List<Publication> publications = null;
	Author author = null;
	
	@Before
	public void setUp() throws Exception {
		publicationService = new PublicationServiceImpl();
		publications = new ArrayList<Publication>();
		author = new Author("Marcelo Yamaguti", "y/Yamaguti:Marcelo_Hideki");
	}

	@After
	public void tearDown() throws Exception {
		publicationService = null;
		publications = null;
		author = null;
	}

	@Test
	public void testSearchPublicationsByAuthor() throws DBLPException {
		publications = publicationService.searchPublicationsByAuthor(author.getUrlKey());
		
		assertTrue(publications.size() > 1);
	}
}
