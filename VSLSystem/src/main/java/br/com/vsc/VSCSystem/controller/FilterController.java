package br.com.vsc.VSCSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.entity.Publication;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.service.implementation.AuthorServiceImpl;
import br.com.vsc.VSCSystem.model.service.implementation.PublicationServiceImpl;
 
@Controller
public class FilterController {
 
	public FilterController(){
	}
	
	
	/*
	 * Filtrar as publicações do autor por um deterinado ano.
	 * 
	 * 
	 */
	@RequestMapping("/FilterByYear")
	public ModelAndView FilterByYear(int yearFilter, HttpSession session) {
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
	
	/*
	 * Obter todos os anos das publicação do autor para montar o filtro por ano 
	 * apenas com anos existentes nas publicações
	 * 
	 */
	public static Set<Integer> getYearsFilter(Author authorSearched){
		Set<Integer> yearsFilter = new TreeSet<Integer>();
		for (Publication publication : authorSearched.getPublications()) {
			yearsFilter.add(publication.getYear());
		}
		return yearsFilter;
	}
	
}