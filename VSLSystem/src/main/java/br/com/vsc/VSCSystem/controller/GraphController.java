package br.com.vsc.VSCSystem.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.service.implementation.CollaborationServiceImpl;
import br.com.vsc.VSCSystem.model.service.implementation.PublicationServiceImpl;
 
@Controller
public class GraphController {
 
	private PublicationServiceImpl publicationService;
	private CollaborationServiceImpl collaborationService;
	
	public GraphController(){
		this.publicationService = new PublicationServiceImpl();
		this.collaborationService = new CollaborationServiceImpl();
	}
	
	@RequestMapping("/LoadGraphInformation")
	public ModelAndView LoadGraphInformation(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("GraphTypes");
		Author authorSearched = new Author(name, urlKey);
		try {
			
			authorSearched.setPublications(publicationService.searchPublicationsByAuthor(urlKey));
			authorSearched.setCollaborations(collaborationService.searchAuthorsCollaborations(urlKey));
			
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
	
	/*
	 * Buscar e exibir as informações referentes as publicações do autor pesquisado em forma de grafo.
	 * 
	 * 
	 */
	@RequestMapping("/GenerateAuthorGraph")
	public ModelAndView GenerateAuthorGraph(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
			
		mv.addObject("msg", "XML successfully processed!");
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("author", session.getAttribute("authorSearchedSession"));

		return mv;
	}
	
	/*
	 * Buscar e exibir as informações referentes as colaborações em publicações 
	 * dos coautores com o autor pesquisado, em forma de grafo.
	 * 
	 */
	@RequestMapping("/GenerateCollaborationsGraph")
	public ModelAndView GenerateCollaborationsGraph(String urlKey, String name, HttpSession session) {
		return null;
	}
	

}