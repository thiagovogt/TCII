package br.com.vsc.VSCSystem.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.entity.Collaboration;
import br.com.vsc.VSCSystem.model.entity.Publication;
 
@Controller
public class FilterController {
 
	public FilterController(){
	}
	
	
	/*
	 * Retornar para a tela do grafo de publicações, todas as publicações filtradas
	 * 
	 */
	@RequestMapping("/FilterAuthorGraph")
	public ModelAndView FilterAuthorGraph(int yearFilter, String typeFilter, String venueFilter, HttpSession session) {
		ModelAndView mv = new ModelAndView("AuthorGraph");

		Author authorSession = (Author) session.getAttribute("authorSearchedSession");
		
		Author filteredAuthor = new Author(authorSession.getName(), authorSession.getUrlKey());
		filteredAuthor.setPublications(getFilteredPublications(yearFilter, typeFilter, venueFilter, authorSession.getPublications()));
		
		mv.addObject("author", filteredAuthor);
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("yearFiltered", yearFilter);
		mv.addObject("typeFiltered", typeFilter);
		mv.addObject("venueFiltered", venueFilter);
		return mv;
	}
	
	/*
	 * Retornar para a tela do grafo de publicações, todas as publicações filtradas
	 * 
	 */
	@RequestMapping("/FilterCollaborations")
	public ModelAndView FilterCollaboration(int yearFilter, String typeFilter, String venueFilter, HttpSession session) {
		ModelAndView mv = new ModelAndView("CollaborationsGraph");
		
		Author authorSession = (Author) session.getAttribute("authorSearchedSession");
		
		Author filteredAuthor = new Author(authorSession.getName(), authorSession.getUrlKey());
		filteredAuthor.setPublications(this.getFilteredPublications(yearFilter, typeFilter, venueFilter, authorSession.getPublications()));
		
		filteredAuthor.setCollaborations(this.getFilteredCollaborations(filteredAuthor.getPublications(), authorSession.getCollaborations()));
		
		mv.addObject("author", filteredAuthor);
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("yearFiltered", yearFilter);
		mv.addObject("typeFiltered", typeFilter);
		mv.addObject("venueFiltered", venueFilter);
		return mv;
	}
	
	private List<Collaboration> getFilteredCollaborations(List<Publication> publications, List<Collaboration> collaborations){
		List<Collaboration> filteredCollaborations = new ArrayList<Collaboration>();
		Set<String> coAuthorsNames = new TreeSet<String>();
		
		for (Publication publication : publications) {
			for (Author author : publication.getCoAuthors()) {
				coAuthorsNames.add(author.getName());
			}
		}
		
		Iterator<Collaboration> iterator = collaborations.iterator();
		while(iterator.hasNext()) {
			Collaboration collaboration = iterator.next();
			if(coAuthorsNames.contains(collaboration.getCoAuthor().getName()))
				filteredCollaborations.add(collaboration); 
		}
		
		return filteredCollaborations;
	}

	/*
	 * Filtrar as publicações do autor
	 * */
	private List<Publication> getFilteredPublications(int yearFilter, String typeFilter, String venueFilter, List<Publication> publications) {
		boolean yearFilterActive = false;
		boolean typeFilterActive = false;
		boolean venueFilterActive = false;
		List<Publication> filteredPublications = new ArrayList<Publication>();
		
		if(yearFilter != 0)
			yearFilterActive = true;
		
		if(!typeFilter.equals(""))
			typeFilterActive = true;
		
		if(!venueFilter.equals(""))
			venueFilterActive = true;
		
		
		if(!yearFilterActive && !typeFilterActive && !venueFilterActive){ 
			return publications;
		}else{
			for (Publication publication : publications) {
				
//				boolean sameYear = (yearFilterActive && yearFilter == publication.getYear());
//				boolean sameType = (typeFilterActive && typeFilter.equals(publication.getType()));
//				boolean sameVenue = (venueFilterActive && venueFilter.equals(publication.getVenue()));
//				
				      //T            F   = T
//				if((sameYear || (!sameYear && !yearFilterActive)) &&
//						//     F       T  =   T
//						(sameType || (!sameType && !typeFilterActive)) &&
//						//        F            = 
//							(sameVenue || (!sameVenue && !venueFilterActive))){
//					publicationsFilter.add(publication);
//				}
				
				boolean isValid = true;
				if (yearFilterActive) {
					if (publication.getYear() != yearFilter)
						isValid = false;
				}
				if (isValid) {
					if (typeFilterActive)
						if (!publication.getType().equals(typeFilter))
							isValid = false;
					
					if (venueFilterActive)
						if (!publication.getVenue().equals(venueFilter))
							isValid = false;
				} 
				if(isValid)
					filteredPublications.add(publication);
			}
		}
		return filteredPublications;
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