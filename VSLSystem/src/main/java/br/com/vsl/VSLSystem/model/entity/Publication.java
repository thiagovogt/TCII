package br.com.vsl.VSLSystem.model.entity;

public class Publication {
	private String urlKey;
	private int year;
	private String title;
	private String type;
	private String local;
	
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

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public String toString() {
		return "Publication [\n urlKey: " + urlKey + ", \n year: " + year + ", \n title: "
				+ title + ", \n type: " + type + ", \n local: " + local + "\n ]";
	}
}
