package br.com.vsl.VSLSystem.model.exception;

public class DBLPException extends Exception{
	public DBLPException(){
		super();
	}
	public DBLPException(String message) {
		super(message);
	}
	public DBLPException(String message, Throwable cause) {
		super(message, cause);
	}
}
