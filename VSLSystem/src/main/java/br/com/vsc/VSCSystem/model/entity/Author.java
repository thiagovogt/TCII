package br.com.vsc.VSCSystem.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Author implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String urlKey;
	private String name;
	private List<Publication> publications = new ArrayList<Publication>();

	public Author(String name, String urlKey) {
		this.urlKey = urlKey;
		this.name = name.trim();
	}
	
	public String getUrlKey() {
		return urlKey;
	}

	public String getName() {
		return name;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	@Override
	public String toString() {
		return "Author [ name: " + name + ", urlKey: " + urlKey + " ]";
	}
	
	
}
