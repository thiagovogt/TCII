package br.com.vsl.VSLSystem.model.exception;

public class AuthorSearchException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AuthorSearchException(){
		super();
	}
	public AuthorSearchException(String message) {
		super(message);
	}
	public AuthorSearchException(String message, Throwable cause) {
		super(message, cause);
	}
}
