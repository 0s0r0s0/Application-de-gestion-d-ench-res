package org.projet.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	private List<String> errors;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public boolean hasErrors() {
		if (this.errors == null) {
			return false;
		} else if (this.errors.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void addError(String erreur) {
		if (this.errors != null) {
			this.errors.add(erreur);
		} else {
			this.errors = new ArrayList();
			this.errors.add(erreur);
		}
	}

	public List<String> getErrors() {
		return errors;
	}
	

}
