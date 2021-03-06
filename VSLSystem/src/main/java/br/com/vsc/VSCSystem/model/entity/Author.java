package br.com.vsc.VSCSystem.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Author implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String urlKey;
	private String name;
	private Set<String> otherNames = new TreeSet<String>();
	private List<Publication> publications = new ArrayList<Publication>();
	private List<Collaboration> collaborations = new ArrayList<Collaboration>();

	public Author(String name, String urlKey) {
		this.urlKey = urlKey;
		this.name = name.trim();
	}
	
	public Author(String name) {
		this.name = name.trim();
	}
	
	public String getUrlKey() {
		return urlKey;
	}
	
	public void setUrlKey(String urlKey) {
		this.urlKey= urlKey ;
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
	
	public List<Collaboration> getCollaborations() {
		return collaborations;
	}

	public void setCollaborations(List<Collaboration> collaborations) {
		this.collaborations = collaborations;
	}

	public Set<String> getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(Set<String> otherNames) {
		this.otherNames = otherNames;
		this.otherNames.remove(this.name);
	}

	@Override
	public String toString() {
		return "Author [ name: " + name + ", urlKey: " + urlKey + " ]";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final Author other = (Author) obj;
	    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
	        return false;
	    }
	    
	    return true;
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
	    return hash;
	}
}
