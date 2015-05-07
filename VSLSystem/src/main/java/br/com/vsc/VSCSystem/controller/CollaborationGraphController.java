package br.com.vsc.VSCSystem.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.service.implementation.CollaborationServiceImpl;
 
@Controller
public class CollaborationGraphController {
 
	private CollaborationServiceImpl collaborationService;
	
	public CollaborationGraphController(){
		this.collaborationService = new CollaborationServiceImpl();
	}
	
	/*
	 * Buscar e exibir as informações referentes as colaborações em publicações 
	 * dos coautores com o autor pesquisado, em forma de grafo.
	 * 
	 */
	@RequestMapping("/GenerateCollaborationsGraph")
	public ModelAndView GenerateCollaborationsGraph(String urlKey, String name, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		Author authorSearched = new Author(name, urlKey);
		try {
			
			HashMap<Author, Integer> teste = collaborationService.searchAuthorsCollaborations(urlKey);
			
			int s = 1;
			

		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	

}