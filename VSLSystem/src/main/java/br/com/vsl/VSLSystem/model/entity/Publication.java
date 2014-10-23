package br.com.vsl.VSLSystem.model.entity;

public class Publication {
	private String urlKey;
	private String xmlInfo;
	
	public Publication(String urlKey) {
		this.urlKey = urlKey;
	}

	public String getUrlKey() {
		return urlKey;
	}

	public String getXmlInfo() {
		return xmlInfo;
	}
	
	public void setXmlInfo(String xmlInfo) {
		this.xmlInfo = xmlInfo;
	}
	
	@Override
	public String toString() {
		return "Publication [urlKey: " + urlKey + ", xmlInfo: " + xmlInfo + "]";
	}

}
