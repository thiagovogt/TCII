package br.com.vsc.VSCSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping("/FilterPublicationsGraph")
	public ModelAndView FilterAuthorGraph(@RequestParam(value = "yearFilter", required = false, defaultValue = "0") int[] yearFilter, String typeFilter, String venueFilter, HttpSession session) {
		ModelAndView mv = new ModelAndView("PublicationsGraph");

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
	@RequestMapping("/FilterCollaborationsGraph")
	public ModelAndView FilterCollaboration(@RequestParam(value = "yearFilter", required = false, defaultValue = "0") int[] yearFilter, String typeFilter, String venueFilter, @RequestParam(value = "minNumberFilter", required = false, defaultValue = "0") int minNumberFilter, HttpSession session) {
		ModelAndView mv = new ModelAndView("CollaborationsGraph");
		
		Author authorSession = (Author) session.getAttribute("authorSearchedSession");
		
		Author filteredAuthor = new Author(authorSession.getName(), authorSession.getUrlKey());
		filteredAuthor.setPublications(this.getFilteredPublications(yearFilter, typeFilter, venueFilter, authorSession.getPublications()));
		
		filteredAuthor.setCollaborations(this.getFilteredCollaborations(filteredAuthor.getPublications(), authorSession.getCollaborations()));
		
		mv.addObject("author", filteredAuthor);
		mv.addObject("yearsFilter", session.getAttribute("yearsFilterSession"));
		mv.addObject("typesFilter", session.getAttribute("typesFilterSession"));
		mv.addObject("venuesFilter", session.getAttribute("venuesFilterSession"));
		mv.addObject("minNumbersFilter", session.getAttribute("minNumbersFilterSession"));
		mv.addObject("yearFiltered", yearFilter);
		mv.addObject("typeFiltered", typeFilter);
		mv.addObject("venueFiltered", venueFilter);
		mv.addObject("minNumbersFiltered", minNumberFilter);
		return mv;
	}
	
	/*
	 * Filtrar as colaborações do autor
	 * */
	private List<Collaboration> getFilteredCollaborations(List<Publication> publications, List<Collaboration> collaborations){
		List<Collaboration> filteredCollaborations = new ArrayList<Collaboration>();
		String coAuthorNameAux = null;
		Map<String, Integer> coAuthorsCollaborations = new TreeMap<String, Integer>();
		Map<String, List<Publication>> collaborationPublications = new TreeMap<String, List<Publication>>();
	
		for (Publication publication : publications) {
			for (Author author : publication.getCoAuthors()) {
				coAuthorNameAux = author.getName();
				if(coAuthorsCollaborations.containsKey(coAuthorNameAux)){
					coAuthorsCollaborations.put(coAuthorNameAux, (coAuthorsCollaborations.get(coAuthorNameAux)+1));
				}else{
					coAuthorsCollaborations.put(coAuthorNameAux,1);
					collaborationPublications.put(coAuthorNameAux, new ArrayList<Publication>());
				}
				collaborationPublications.get(coAuthorNameAux).add(publication);
			}
		}
		
		for (Collaboration collaboration : collaborations) {
			coAuthorNameAux = collaboration.getCoAuthor().getName();
			if(coAuthorsCollaborations.containsKey(coAuthorNameAux)){
				int numberOfFilteredCollaborations = coAuthorsCollaborations.get(coAuthorNameAux);
				List<Publication> publicationsAux = collaborationPublications.get(coAuthorNameAux);
				Collaboration collaborationAux = new Collaboration(collaboration.getCoAuthor(), numberOfFilteredCollaborations);
				collaborationAux.setPublications(publicationsAux);
				filteredCollaborations.add(collaborationAux);
			}
		}
		return filteredCollaborations;
	}

	/*
	 * Filtrar as publicações do autor
	 * */
	private List<Publication> getFilteredPublications(int[] yearFilter, String typeFilter, String venueFilter, List<Publication> publications) {
		boolean yearFilterActive = false;
		boolean typeFilterActive = false;
		boolean venueFilterActive = false;
		Set<Integer> yearFilterAux = new TreeSet<Integer>();
		List<Publication> filteredPublications = new ArrayList<Publication>();
		if(yearFilter[0] != 0){
			yearFilterActive = true;
			for (int i = 0; i < yearFilter.length; i++) {
				yearFilterAux.add(yearFilter[i]);
			}
		}
		
		if(!typeFilter.equals(""))
			typeFilterActive = true;
		
		if(!venueFilter.equals(""))
			venueFilterActive = true;
		
		
		if(!yearFilterActive && !typeFilterActive && !venueFilterActive){ 
			return publications;
		}else{
			for (Publication publication : publications) {
				boolean isValid = true;
				if (yearFilterActive) {
					if (!yearFilterAux.contains(publication.getYear()))
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
	
	/*
	 * Obter todos os números referentes as colaborações do autor para montar o filtro 
	 * por número mínimo de colaborações
	 * 
	 */
	public static Set<Integer> getMinimumNumCollaborationsFilter(Author authorSearched){
		Set<Integer> minNumbersFilter = new TreeSet<Integer>();
		for (Collaboration collaboration : authorSearched.getCollaborations()) {
			minNumbersFilter.add(collaboration.getNumberOfCollaborations());
		}
		return minNumbersFilter;
	}
	
}