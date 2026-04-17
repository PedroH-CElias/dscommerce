package com.devsuperior.dscommerce.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.devsuperior.dscommerce.entities.User;

public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private String phone;
	private LocalDate birthDate;
	
	//Para obter apenas os nomes(Authority)
	private List<String> roles = new ArrayList<>();

	public UserDTO(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.birthDate = user.getBirthDate();
		for (GrantedAuthority role : user.getRoles()) {
			roles.add(role.getAuthority());
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public List<String> getRoles() {
		return roles;
	}
	
}
