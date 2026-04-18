package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dtos.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.repositories.OrderRepositoy;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepositoy repository;

	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository //
				.findById(id) //
				.orElseThrow( //
						() -> new ResourceNotFoundException("Recurso não encontrado") // Lança exceção customizada caso
																						// não encontre objeto
				);

		return new OrderDTO(order);
	}

}
