package br.com.vsl.VSLSystem.model.entity;

public class Publication {
	private String urlKey;
	
	public Publication(String urlKey) {
		this.urlKey = urlKey;
	}

	public String getUrlKey() {
		return urlKey;
	}

	@Override
	public String toString() {
		return "Publication [urlKey: " + urlKey + "]";
	}
}
