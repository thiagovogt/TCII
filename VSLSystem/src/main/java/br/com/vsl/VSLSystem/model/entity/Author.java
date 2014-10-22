package br.com.vsl.VSLSystem.model.entity;

public class Author {
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
