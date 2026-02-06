package com.devsuperior.dscommerce.services.exceptions;

/**
 * Exceção customizada
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
