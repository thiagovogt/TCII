package br.com.vsc.VSCSystem.model.entity;

import java.io.Serializable;

public class Collaboration implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Author coAuthor;
	private int numberOfCollaborations;
	
	public Collaboration(Author coAuthor, int numberOfCollaborations) {
		this.coAuthor = coAuthor;
		this.numberOfCollaborations = numberOfCollaborations;
	}

	public Author getCoAuthor() {
		return coAuthor;
	}

	public void setCoAuthor(Author coAuthor) {
		this.coAuthor = coAuthor;
	}

	public int getNumberOfCollaborations() {
		return numberOfCollaborations;
	}

	public void setNumberOfCollaborations(int numberOfCollaborations) {
		this.numberOfCollaborations = numberOfCollaborations;
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final Collaboration other = (Collaboration) obj;
	    if ((this.coAuthor == null) ? (other.coAuthor != null) : !this.coAuthor.equals(other.coAuthor)) {
	        return false;
	    }
	    return true;
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.coAuthor != null ? this.coAuthor.hashCode() : 0);
	    return hash;
	}
}
