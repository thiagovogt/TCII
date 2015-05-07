package br.com.vsc.VSCSystem.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Publication implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String urlKey;
	private int year;
	private String title;
	private String type;
	private String venue;
	private String eePath;
	private String urlPath;
	private List<Author> coAuthors = new ArrayList<Author>();
	
	public Publication(String urlKey) {
		this.urlKey = urlKey;
	}
	
	public String getUrlKey() {
		return urlKey;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = this.formatTextValue(title);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = this.getPublicationType(type);
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = this.formatTextValue(venue);
	}
	
	public List<Author> getCoAuthors() {
		return coAuthors;
	}

	public void setCoAuthors(List<Author> coAuthors) {
		this.coAuthors = coAuthors;
	}

	public String getEePath() {
		return eePath;
	}

	public void setEePath(String eePath) {
		eePath = this.formatTextValue(eePath);
		if(!eePath.contains("http:")){
			this.eePath = "http://dblp.uni-trier.de/" + eePath; 
		}else{
			this.eePath = eePath;
		}
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		urlPath = this.formatTextValue(urlPath);
		if(!urlPath.contains("http:")){
			this.urlPath = "http://dblp.uni-trier.de/" + urlPath; 
		}else{
			this.urlPath = urlPath;
		}
	}
	
	/*
	 * 
	 * Define o tipo da publicação baseado na tag xml
	 * 
	 * */
	private String getPublicationType(String typeCode){
		if(typeCode.equals("book") || typeCode.equals("phdthesis") || typeCode.equals("masterthesis")){
			return "Books and Theses";
		}else if(typeCode.equals("article")){
			return "Journal Article";
		}else if(typeCode.equals("inproceedings") || typeCode.equals("proceedings")){
			return "Conference and Workshop Papers";
		}else if(typeCode.equals("incollection")){
			return "Parts in Books or Collections";
		}else if(typeCode.equals("editor") || typeCode.equals("edited publication")){
			return "Editorship";
		}else if(typeCode.equals("reference") || typeCode.equals("encyclopedia entry") || typeCode.equals("survey")){
			return "Reference Works";
		}else if(typeCode.equals("informal publication") || typeCode.equals("www")){
			return "Informal and Other Publications";
		}else{
			return "";
		}
	}
	
	/*
	 * 
	 * Formata o valor do texto substituindo todas as ocorrências de '\' por '\\'
	 * 
	 * */
	private String formatTextValue(String text){
		return text.replaceAll("\"", "\\\\\"");
	}
	
	@Override
	public String toString() {
		return "Publication [\n urlKey: " + urlKey + ", \n year: " + year + ", \n title: "
				+ title + ", \n type: " + type + ", \n venue: " + venue + "\n ]";
	}
}
