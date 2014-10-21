package br.com.vsl.VSLSystem.model.exception;

public class AuthorSearchException extends Exception{
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
