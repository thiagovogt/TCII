package br.com.vsl.VSLSystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.service.implementation.AuthorServiceImpl;
import br.com.vsl.VSLSystem.model.service.implementation.PublicationServiceImpl;
 
@Controller
public class AuthorGraphController {
 
	private AuthorServiceImpl authorService;
	private PublicationServiceImpl publicationService;
	
	public AuthorGraphController(){
		this.authorService = new AuthorServiceImpl();
		this.publicationService = new PublicationServiceImpl();
	}
	
	@RequestMapping("/SearchAuthor")
	public ModelAndView SearchAuthor() {
		ModelAndView mv = new ModelAndView("SearchAuthor");
		return mv;
	}
	
	@RequestMapping("/ListAuthors")
	public ModelAndView ListAuthors(String searchName) {
		ModelAndView mv = new ModelAndView("ListAuthors");
		List<Author> authors = new ArrayList<Author>();
		
		try{
			
			authors = authorService.searchAuthorByName(searchName);
			
			mv.addObject("msg", "XML processado com sucesso!");
			mv.addObject("authors", authors);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	@RequestMapping("/GenerateGraph")
	public ModelAndView GenerateGraph(String urlKey, String name) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		Author authorSearched = new Author(name, urlKey);
		try {

			authorSearched.setPublications(publicationService.searchPublicationsByAuthor(urlKey));
			
			mv.addObject("msg", "XML processado com sucesso!");
			mv.addObject("author", authorSearched);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
}