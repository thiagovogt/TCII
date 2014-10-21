package br.com.vsl.VSLSystem.controller;

import java.io.InputStream;
import java.net.URL;




import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.service.implementation.AuthorServiceImpl;
 
@Controller
public class AuthorGraphController {
 
	private AuthorServiceImpl authorService;
	
	public AuthorGraphController(){
		this.authorService = new AuthorServiceImpl();
	}
	
	@RequestMapping("/SearchAuthor")
	public ModelAndView SearchAuthor() {
		ModelAndView mv = new ModelAndView("SearchAuthor");
		return mv;
	}
	
	@RequestMapping("/ListAuthors")
	public ModelAndView ListAuthors(String searchName) {
		ModelAndView mv = new ModelAndView("ListAuthors");
		List<Author> authors = new ArrayList<Author>();
		
		try{
			
			authors = authorService.searchAuthorByName(searchName);
			
			mv.addObject("msg", "XML processado com sucesso!");
			mv.addObject("authors", authors);
		} catch (DBLPException dblpe) {
			mv.addObject("msg","Erro ao processar XML : " + dblpe.getMessage());
		}

		return mv;
	}
	@RequestMapping("/GenerateGraph")
	public ModelAndView GenerateGraph(String name) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		return mv;
	}
}