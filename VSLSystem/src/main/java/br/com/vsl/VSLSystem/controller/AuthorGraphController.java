package br.com.vsl.VSLSystem.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.entity.Publication;
import br.com.vsl.VSLSystem.model.exception.AccessReportException;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.service.AccessReportService;
import br.com.vsl.VSLSystem.model.service.implementation.AccessReportServiceImpl;
import br.com.vsl.VSLSystem.model.service.implementation.AuthorServiceImpl;
import br.com.vsl.VSLSystem.model.service.implementation.PublicationServiceImpl;
 
@Controller
public class AuthorGraphController {
 
	private AuthorServiceImpl authorService;
	private PublicationServiceImpl publicationService;
	private AccessReportServiceImpl accessReportService;
	
	public AuthorGraphController(){
		this.authorService = new AuthorServiceImpl();
		this.publicationService = new PublicationServiceImpl();
		this.accessReportService = new AccessReportServiceImpl();
	}
	
	@RequestMapping("/SearchAuthor")
	public ModelAndView SearchAuthor() {
		ModelAndView mv = new ModelAndView("SearchAuthor");
		return mv;
	}
	
	/*
	 * Buscar e exibir uma lista de autores através de uma String que representa o nome ou sobrenome a ser buscado
	 * 
	 */
	@RequestMapping("/ListAuthors")
	public ModelAndView ListAuthors(String searchName) {
		ModelAndView mv = new ModelAndView("ListAuthors");
		List<Author> authors = new ArrayList<Author>();
		
		try{
			
			authors = authorService.searchAuthorByName(searchName);
			
			if(authors.size() == 1){
				return this.GenerateGraph(authors.get(0).getUrlKey(), searchName);
			}
			
			mv.addObject("msg", "XML processado com sucesso!");
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
	public ModelAndView GenerateGraph(String urlKey, String name) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		Author authorSearched = new Author(name, urlKey);
		try {

			authorSearched.setPublications(publicationService.searchPublicationsByAuthor(urlKey));
			
//			for (Publication pubAux : authorSearched.getPublications()) {
//				pubAux = publicationService.searchPublication(pubAux);
//			}
			mv.addObject("msg", "XML processado com sucesso!");
			mv.addObject("author", authorSearched);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	
	public String insertAccessLog() {
		try {
			accessReportService.insertAccessLog(new GregorianCalendar());
			return "";
		} catch (AccessReportException e) {
			return e.getMessage();
		}
	}
	
	
	@RequestMapping("/AccessReport")
	public ModelAndView AccessReport() {
		ModelAndView mv = new ModelAndView("AccessReport");

		HashMap<String, Integer> accessReport;
		try {
			accessReport = accessReportService.getAccessLogReport();
			
			mv.addObject("accessDay", accessReport.get("accessDay"));
			mv.addObject("accessMonth", accessReport.get("accessMonth"));
			mv.addObject("accessYear", accessReport.get("accessYear"));
		} catch (AccessReportException e) {
			mv.addObject("msg", e.getMessage());
		}
		
		return mv;
	}
}