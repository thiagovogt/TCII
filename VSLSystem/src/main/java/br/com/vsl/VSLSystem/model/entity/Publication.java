package br.com.vsl.VSLSystem.model.entity;

public class Publication {
	private String urlKey;
	private String url;
	private String title;
	private String pages;
	private int year;
	private int local;
	private int type;
	public Publication(String urlKey, String url, String title, String pages,
							int year, int local, int type) {
		this.urlKey = urlKey;
		this.url = url;
		this.title = title;
		this.pages = pages;
		this.year = year;
		this.local = local;
		this.type = type;
	}
	public String getUrlKey() {
		return urlKey;
	}
	public String getTitle() {
		return title;
	}
	public String getPages() {
		return pages;
	}
	public int getYear() {
		return year;
	}
	public int getLocal() {
		return local;
	}
	public int getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	
	@Override
	public String toString() {
		return "Publication [ urlKey: " + urlKey + ", url: " + url + ", title: "
				+ title + ", pages: " + pages + ", year: " + year + ", local: "
				+ local + ", type: " + type + " ]";
	}
	
	
}
