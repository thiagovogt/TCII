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
 
@Controller
public class FilterController {
 
	public FilterController(){
	}
	
	
	/*
	 * Filtrar as publicações do autor
	 * 
	 * 
	 */
	@RequestMapping("/Filter")
	public ModelAndView Filter(int yearFilter, String typeFilter, String venueFilter, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		boolean yearFilterActive = false;
		boolean typeFilterActive = false;
		boolean venueFilterActive = false;
		
		Author authorSession = (Author) session.getAttribute("authorSearchedSession");
		List<Publication> publicationsFilter = new ArrayList<Publication>();
		
		if(yearFilter != 0){
			yearFilterActive = true;
		}
		if(!typeFilter.equals("")){
			typeFilterActive = true;
		}
		if(!venueFilter.equals("")){
			venueFilterActive = true;
		}
		
		if(!yearFilterActive && !typeFilterActive && !venueFilterActive){ 
			publicationsFilter.addAll(authorSession.getPublications());
		}else{
			for (Publication publication : authorSession.getPublications()) {
				if(yearFilterActive && typeFilterActive && venueFilterActive){ 
					if(publication.getYear() == yearFilter &&
							publication.getType().equals(typeFilter) &&
								publication.getVenue().equals(venueFilter)){
						publicationsFilter.add(publication);
					}
				}else if(yearFilterActive && typeFilterActive && !venueFilterActive){ 
					if(publication.getYear() == yearFilter &&
							publication.getType().equals(typeFilter)){
						publicationsFilter.add(publication);
					}
				}else if(yearFilterActive && !typeFilterActive && venueFilterActive){   
					if(publication.getYear() == yearFilter &&
								publication.getVenue().equals(venueFilter)){
						publicationsFilter.add(publication);
					}
				}else if(!yearFilterActive && typeFilterActive && venueFilterActive){ 
					if(publication.getType().equals(typeFilter) &&
								publication.getVenue().equals(venueFilter)){
						publicationsFilter.add(publication);
					}
				}else if(!yearFilterActive && !typeFilterActive && venueFilterActive){ 
					if(publication.getVenue().equals(venueFilter)){
						publicationsFilter.add(publication);
					}
				}else if(yearFilterActive && !typeFilterActive && !venueFilterActive){ 
					if(publication.getYear() == yearFilter){
						publicationsFilter.add(publication);
					}
				}
			}
		}
		
		Author authorfilter = new Author(authorSession.getName(), authorSession.getUrlKey());
		authorfilter.setPublications(publicationsFilter);
		
		mv.addObject("author", authorfilter);
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("yearFiltered", yearFilter);
		mv.addObject("typeFiltered", typeFilter);
		mv.addObject("venueFiltered", venueFilter);
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
	/*
	 * Obter todos os tipos das publicação do autor para montar o filtro por tipo 
	 * apenas com tipos existentes nas publicações
	 * 
	 */
	public static Set<String> getTypesFilter(Author authorSearched){
		Set<String> typesFilter = new TreeSet<String>();
		for (Publication publication : authorSearched.getPublications()) {
			typesFilter.add(publication.getType());
		}
		return typesFilter;
	}
	/*
	 * Obter todos os 'venues' das publicação do autor para montar o filtro por 'venue' 
	 * apenas com 'venues' existentes nas publicações
	 * 
	 */
	public static Set<String> getVenuesFilter(Author authorSearched){
		Set<String> venuesFilter = new TreeSet<String>();
		for (Publication publication : authorSearched.getPublications()) {
			venuesFilter.add(publication.getVenue());
		}
		return venuesFilter;
	}
	
}