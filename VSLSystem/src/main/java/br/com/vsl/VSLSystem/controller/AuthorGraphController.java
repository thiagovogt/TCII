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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class AuthorGraphController {
 
	@RequestMapping("/SearchAuthor")
	public ModelAndView SearchAuthor() {
		ModelAndView mv = new ModelAndView("SearchAuthor");
		return mv;
	}
	
	@RequestMapping("/ListAuthors")
	public ModelAndView ListAuthors(String searchName) {
		ModelAndView mv = new ModelAndView("ListAuthors");
		mv.addObject("nome", searchName);
		try{
			URL url = new URL("http://dblp.uni-trier.de/search/author?xauthor=" + searchName);
			System.out.println(url);
			InputStream input = url.openStream();
			int ptr = 0;
			StringBuilder builder = new StringBuilder();
			while ((ptr = input.read()) != -1) {
			    builder.append((char) ptr);
			}
			String xml = builder.toString();
			JSONObject jsonObject = (JSONObject) XML.toJSONObject(xml).get("authors");
			List<String> li = new ArrayList<String>();
		    JSONArray c = jsonObject.getJSONArray("author");
		    
		    for (int i = 0 ; i < c.length(); i++) {
		        JSONObject obj = c.getJSONObject(i);
		        li.add(obj.get("content").toString());
		    }

			mv.addObject("msg", "XML processado com sucesso!");
			mv.addObject("lista", li);
		} catch (Exception e) {
			mv.addObject("msg","Erro ao processar XML : " + e.getMessage());
			throw new IllegalStateException(e);
		}

		return mv;
	}
	@RequestMapping("/GenerateGraph")
	public ModelAndView GenerateGraph(String name) {
		ModelAndView mv = new ModelAndView("AuthorGraph");
		return mv;
	}
}