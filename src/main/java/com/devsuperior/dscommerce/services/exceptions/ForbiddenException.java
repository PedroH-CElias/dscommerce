package com.devsuperior.dscommerce.services.exceptions;

/**
 * Criado para retornar erro quando um usuário tentar acessar
 * um pedido que não é dele. Para os casos em que o usuário seja um CLIENTE
 */
@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {

	public ForbiddenException(String msg) {
		super(msg);
	}
}
