package com.devsuperior.dscommerce.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dtos.CategoryDTO;
import com.devsuperior.dscommerce.dtos.ProductDTO;
import com.devsuperior.dscommerce.dtos.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = productRepository //
				.findById(id) //
				.orElseThrow( //
						() -> new ResourceNotFoundException("Recurso não encontrado") // Lança exceção customizada caso
																						// não encontre objeto
				);

		return new ProductDTO(product);
	}

	/**
	 * Busca paginada Retorna apenas os dados necessarios com o DTO
	 */
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
		Page<Product> products = productRepository.searchByName(name, pageable);
		return products.map(product -> new ProductMinDTO(product));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		return saveEntity(dto, entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		Product entity = productRepository.getReferenceById(id); // Busca um produto já existente, apenas por
																	// referencia, não no BD
		return saveEntity(dto, entity);
	}

	private ProductDTO saveEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());

		Set<Category> entityCategories = entity.getCategories();
		entityCategories.clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category catEntity = new Category();
			catEntity.setId(catDto.getId());
			
			entityCategories.add(catEntity);
		}

		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
}
