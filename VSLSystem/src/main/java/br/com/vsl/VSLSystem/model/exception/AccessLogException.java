package br.com.vsl.VSLSystem.model.exception;

public class AccessLogException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AccessLogException(){
		super();
	}
	public AccessLogException(String message) {
		super(message);
	}
	public AccessLogException(String message, Throwable cause) {
		super(message, cause);
	}
}
