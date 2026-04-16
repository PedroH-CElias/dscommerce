package com.devsuperior.dscommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		
		/**
		 * Instancia um novo User
		 * Atribuiu o username passado por parametro ao email.
		 * Como é somente um usuário com esse username, pega o get(0) e associa a senha
		 * buscada do bd ao password do user.
		 * Percorre a lista, que pode possuir mais de um dado devido ao usuario ter mais de um perfil (Ex: ADM e CLIENT)
		 * E no loop cria um novo Role (Perfil/Função do Usuário) adicionado o Id que buscou da tabela de Role e o tipo de Autorização
		 * que o usuário possui no sistema (Ex: ADM e CLIENT), associando esses dados ao User criado.
		 */
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			Role role = new Role(projection.getRoleId(), projection.getAuthority());
			user.addRole(role);
		}
		
		return user;
	}
}
