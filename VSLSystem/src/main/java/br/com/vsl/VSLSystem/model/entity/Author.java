package br.com.vsl.VSLSystem.model.entity;

import java.io.Serializable;

public class Author implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String urlKey;
	private String name;

	public Author(String name, String urlKey) {
		this.urlKey = urlKey;
		this.name = name;
	}
	
	public String getUrlKey() {
		return urlKey;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Author [ name: " + name + ", urlKey: " + urlKey + " ]";
	}
	
	
}
