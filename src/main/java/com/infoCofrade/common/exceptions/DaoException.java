package com.infoCofrade.common.exceptions;

/**
 * Thrown on an database error
 * @author Luis Miguel Cruz
 */
public class DaoException extends Exception {
	private static final long serialVersionUID = 1L;
	private Exception exception;
	private String message;
	
	/**
	 * Create a new DaoException with the specified message.
	 * @param msg the detail message
	 */
	public DaoException(String message) {
		this.message = message;
	}

	/**
	 * Create a new DaoException with the father exception
	 * @param ex exception captured
	 */
	public DaoException(Exception ex) {
		this.exception = ex;
	}
	
	public String getMessage() {
		String message = "";
		if (this.message != null) {
			message = this.message;
		} else if (this.exception != null) {
			message = this.exception.getMessage();
		}
		return message;
	}
}