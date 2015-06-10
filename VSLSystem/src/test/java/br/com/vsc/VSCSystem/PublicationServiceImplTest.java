package br.com.vsc.VSCSystem;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.entity.Publication;
import br.com.vsc.VSCSystem.model.service.implementation.PublicationServiceImpl;

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

//	@Test
//	public void testSearchPublicationsByAuthor() throws DBLPException {
//		publications = publicationService.searchPublicationsByAuthor(author.getUrlKey());
//		
//		assertTrue(publications.size() > 1);
//	}
}
