package br.com.vsc.VSCSystem.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.service.implementation.PublicationServiceImpl;
 
@Controller
public class AuthorGraphController {
 
	private PublicationServiceImpl publicationService;
	
	public AuthorGraphController(){
		this.publicationService = new PublicationServiceImpl();
	}
	
	/*
	 * Buscar e exibir as informações referentes as publicações do autor pesquisado em forma de grafo.
	 * 
	 * 
	 */
	@RequestMapping("/GenerateGraph")
	public ModelAndView GenerateGraph(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		Author authorSearched = new Author(name, urlKey);
		try {
			
			authorSearched.setPublications(publicationService.searchPublicationsByAuthor(urlKey));
			
			Set<Integer> yearsFilter = FilterController.getYearsFilter(authorSearched);
			Set<String> typesFilter = FilterController.getTypesFilter(authorSearched);
			Set<String> venuesFilter = FilterController.getVenuesFilter(authorSearched);
			
			session.setAttribute("authorSearchedSession", authorSearched);
			session.setAttribute("yearsFilterSession", yearsFilter);
			session.setAttribute("typesFilterSession", typesFilter);
			session.setAttribute("venuesFilterSession", venuesFilter);
			
			mv.addObject("msg", "XML successfully processed!");
			mv.addObject("yearsFilter", yearsFilter);
			mv.addObject("typesFilter", typesFilter);
			mv.addObject("venuesFilter", venuesFilter);
			mv.addObject("author", authorSearched);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	

}