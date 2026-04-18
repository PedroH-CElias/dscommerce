package com.devsuperior.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dtos.OrderDTO;
import com.devsuperior.dscommerce.dtos.OrderItemDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.OrderItemRepositoy;
import com.devsuperior.dscommerce.repositories.OrderRepositoy;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepositoy repository;
	
	@Autowired
	private OrderItemRepositoy orderItemRepositoy;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private AuthService authService;

	/**
	 * Só libera o pedido, se o usuário solicitante for o dono dele ou ADMIN
	 */
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository //
				.findById(id) //
				.orElseThrow( //
						() -> new ResourceNotFoundException("Recurso não encontrado") // Lança exceção customizada caso
																						// não encontre objeto
				);
		
		// Passa o id do client vinculado ao pedido que está sendo buscado
		authService.validateSelfOrAdmin(order.getClient().getId());

		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		return saveEntity(dto, order);
	}

	@Transactional
	private OrderDTO saveEntity(OrderDTO dto, Order order) {
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		User userLogged = userService.authenticated();
		order.setClient(userLogged);
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			//Pego o id do produto vinculado ao item do pedido, e instancio um novo objeto product
			Product product = productRepository.getReferenceById(itemDto.getProductId());
			
			//Preço é o do produto para manter o dado de quando foi vendido
			OrderItem orderItem = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
			
			order.getItems().add(orderItem);
		}
		
		order = repository.save(order);
		orderItemRepositoy.saveAll(order.getItems());
		return new OrderDTO(order);
	}

}
