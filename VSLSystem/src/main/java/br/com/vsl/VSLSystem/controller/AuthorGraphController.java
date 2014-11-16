package br.com.vsl.VSLSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	
	@RequestMapping("/Home")
	public String Home(HttpSession session) {
		session.invalidate();
		return "../../index";
	}
	
	@RequestMapping("/SearchAuthor")
	public ModelAndView SearchAuthor(HttpSession session) {
		ModelAndView mv = new ModelAndView("SearchAuthor");
		return mv;
	}
	
	/*
	 * Buscar e exibir uma lista de autores através de uma String que representa o nome ou sobrenome a ser buscado
	 * 
	 */
	@RequestMapping("/ListAuthors")
	public ModelAndView ListAuthors(String searchName, HttpSession session) {
		ModelAndView mv = new ModelAndView("ListAuthors");
		List<Author> authors = new ArrayList<Author>();
		
		try{
			
			authors = authorService.searchAuthorByName(searchName);
			
			if(authors.size() == 1){
				return this.GenerateGraph(authors.get(0).getUrlKey(), authors.get(0).getName(), session);
			}
			
			mv.addObject("msg", "XML successfully processed!");
			mv.addObject("authors", authors);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	
	/*
	 * Buscar e exibir as informações referentes as publicações do autor pesquisado.
	 * 
	 * 
	 */
	@RequestMapping("/GenerateGraph")
	public ModelAndView GenerateGraph(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		Author authorSearched = new Author(name, urlKey);
		try {
			
			authorSearched.setPublications(publicationService.searchPublicationsByAuthor(urlKey));
			
			Set<Integer> yearsFilter = this.getYearsFilter(authorSearched);
			
			session.setAttribute("authorSearchedSession", authorSearched);
			session.setAttribute("yearsFilterSession", yearsFilter);
			
			mv.addObject("msg", "XML successfully processed!");
			mv.addObject("yearsFilter", yearsFilter);
			mv.addObject("author", authorSearched);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	
	@RequestMapping("/FilterByYear")
	public ModelAndView TestVisJs(int yearFilter, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		
		Author authorSession = (Author) session.getAttribute("authorSearchedSession");
		List<Publication> publicationsByYear = new ArrayList<Publication>();
		if(yearFilter == 0){
			publicationsByYear.addAll(authorSession.getPublications());
		}else{
			for (Publication publication : authorSession.getPublications()) {
				if(publication.getYear() == yearFilter){
					publicationsByYear.add(publication);
				}
			}
		}
		
		Author authorfilter = new Author(authorSession.getName(), authorSession.getUrlKey());
		authorfilter.setPublications(publicationsByYear);
		
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("author", authorfilter);
		mv.addObject("yearFiltered", yearFilter);
		return mv;
	}
	
	private Set<Integer> getYearsFilter(Author authorSearched){
		Set<Integer> yearsFilter = new TreeSet<Integer>();
		for (Publication publication : authorSearched.getPublications()) {
			yearsFilter.add(publication.getYear());
		}
		return yearsFilter;
	}
}