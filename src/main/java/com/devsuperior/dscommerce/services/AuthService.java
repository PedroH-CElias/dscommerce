package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;

/**
 * Implementar regras de negócio relacionadas a controle de acesso
 */
@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(long userId) {
		User userLogged = userService.authenticated();
		
		//Verifica se o usuario logado não é admin nem o passado por parametro
		if (!userLogged.hasRole("ROLE_ADMIN") && !userLogged.getId().equals(userId)) {
			throw new ForbiddenException("Access denied");
		}
	}
}
