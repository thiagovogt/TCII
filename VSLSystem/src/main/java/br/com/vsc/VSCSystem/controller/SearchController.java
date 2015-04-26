package br.com.vsc.VSCSystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsc.VSCSystem.model.entity.Author;
import br.com.vsc.VSCSystem.model.exception.DBLPException;
import br.com.vsc.VSCSystem.model.service.implementation.AuthorServiceImpl;
 
@Controller
public class SearchController {
 
	private AuthorServiceImpl authorService;
	
	public SearchController(){
		this.authorService = new AuthorServiceImpl();
	}
	
	/*
	 * Redirecionar para a tela inicial do sistema
	 * 
	 */
	@RequestMapping("/Home")
	public String Home(HttpSession session) {
		session.invalidate();
		return "../../index";
	}
	
	
	/*
	 * Redirecionar para a tela de busca por nome do autor
	 * 
	 */
	@RequestMapping("/SearchAuthor")
	public ModelAndView SearchAuthor(HttpSession session) {
		ModelAndView mv = new ModelAndView("SearchAuthor");
		return mv;
	}
	
	/*
	 * Buscar e exibir uma lista de autores através de uma String que
	 * representa o nome ou sobrenome a ser buscado
	 * 
	 */
	@RequestMapping("/ListAuthors")
	public ModelAndView ListAuthors(String searchName, HttpSession session) {
		ModelAndView mv = new ModelAndView("ListAuthors");
		List<Author> authors = new ArrayList<Author>();
		
		try{
			
			authors = authorService.searchAuthorByName(searchName);
			
			if(authors.size() == 1){
				return new AuthorGraphController().GenerateGraph(authors.get(0).getUrlKey(), authors.get(0).getName(), session);
			}
			
			mv.addObject("msg", "XML successfully processed!");
			mv.addObject("authors", authors);
		} catch (DBLPException dblpe) {
			mv.addObject("msg", dblpe.getMessage());
		}

		return mv;
	}
	
}