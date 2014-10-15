package br.com.vsl.VSLSystem.model.exception;

public class AccessReportException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AccessReportException(){
		super();
	}
	public AccessReportException(String message) {
		super(message);
	}
	public AccessReportException(String message, Throwable cause) {
		super(message, cause);
	}
}
