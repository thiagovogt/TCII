package br.com.vsc.VSCSystem.controller;

import java.util.Set;
import java.util.TreeSet;

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
	
	/*
	 * Buscar as informações referentes as publicações do autor e colaborações em publicações 
	 * dos coautores com o autor pesquisado  .
	 * 
	 * 
	 */
	@RequestMapping("/LoadGraphInformation")
	public ModelAndView LoadGraphInformation(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("GraphTypes");
		Author authorSearched = new Author(name, urlKey);
		try {
			
			authorSearched.setPublications(publicationService.searchPublicationsByAuthor(urlKey, session));
			if(session.getAttribute("fUrlAuthorKey") != null && !session.getAttribute("fUrlAuthorKey").equals("null")){
				authorSearched.setUrlKey(String.valueOf(session.getAttribute("fUrlAuthorKey")));
				session.setAttribute("fUrlAuthorKey", "null");
			}
			authorSearched.setCollaborations(collaborationService.searchAuthorsCollaborations(authorSearched.getUrlKey()));
			
			authorSearched.setOtherNames((TreeSet<String>)session.getAttribute("otherAuthorNames"));
			
			session.setAttribute("authorSearchedSession", authorSearched);
			
			Set<Integer> yearsFilter = FilterController.getYearsFilter(authorSearched);
			Set<String> typesFilter = FilterController.getTypesFilter(authorSearched);
			Set<String> venuesFilter = FilterController.getVenuesFilter(authorSearched);
			
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
	 * Exibir as informações referentes as publicações do autor pesquisado em forma de grafo.
	 * 
	 * 
	 */
	@RequestMapping("/GenerateAuthorGraph")
	public ModelAndView GenerateAuthorGraph(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
			
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("author", session.getAttribute("authorSearchedSession"));

		return mv;
	}
	
	/*
	 * Buscar e exibir a colaborações em publicações dos coautores com o autor pesquisadoem em forma de grafo.
	 * 
	 */
	@RequestMapping("/GenerateCollaborationsGraph")
	public ModelAndView GenerateCollaborationsGraph(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("CollaborationsGraph");
		
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("author", session.getAttribute("authorSearchedSession"));

		return mv;
	}
	

}