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
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	public List<Author> getCoAuthors() {
		return coAuthors;
	}

	public void setCoAuthors(List<Author> coAuthors) {
		this.coAuthors = coAuthors;
	}

	@Override
	public String toString() {
		return "Publication [\n urlKey: " + urlKey + ", \n year: " + year + ", \n title: "
				+ title + ", \n type: " + type + ", \n venue: " + venue + "\n ]";
	}
}
