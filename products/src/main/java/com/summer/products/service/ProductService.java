package com.summer.products.service;

import java.math.BigDecimal;
import java.util.List;

import com.summer.products.enums.Tipos;
import com.summer.products.exception.InvalidInputException;
import com.summer.products.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summer.products.model.Product;
import com.summer.products.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}


	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Product findById(Long id) throws ProductNotFoundException {
		return productRepository.findById(id).orElseThrow(
				() -> new ProductNotFoundException("Produto não encontrado")
		);
	}
	
	public Product saveProduct(Product product) throws InvalidInputException {
		if (product.getPreco().intValue() < 0 || product.getQuantidade() < 0 )
			throw new InvalidInputException("Valores de Valor ou Quantidade inválidos.");
		else if(product.getNome().isEmpty())
			throw new InvalidInputException("Nome não pode ser um campo vazio");

		return productRepository.save(product);
	}

	public Product putProduct(Long id , Product newProduct) throws ProductNotFoundException, InvalidInputException {
		Product oldproduto = findById(id);
		oldproduto.setName(newProduct.getNome());
		oldproduto.setPreco(newProduct.getPreco());
		oldproduto.setQuantidade(newProduct.getQuantidade());
		oldproduto.setTipo(newProduct.getTipo());
		return saveProduct(oldproduto);
	}

	public Product patchName(Long id,String newName) throws ProductNotFoundException, InvalidInputException {
		Product produto = findById(id);
		produto.setName(newName);
		return saveProduct(produto);
	}

	public Product patchPreco(Long id, BigDecimal newPreco) throws ProductNotFoundException, InvalidInputException {
		Product produto = findById(id);
		produto.setPreco(newPreco);
		return saveProduct(produto);
	}

	public Product patchQuantidade(Long id, Integer newQuantidade) throws ProductNotFoundException, InvalidInputException {
		Product produto = findById(id);
		produto.setQuantidade(newQuantidade);
		return saveProduct(produto);
	}

	public Product patchTipo(Long id, Tipos newTipo) throws ProductNotFoundException, InvalidInputException {
		Product produto = findById(id);
		produto.setTipo(newTipo);
		return saveProduct(produto);
	}

	public void deleteProduct(Long id) throws ProductNotFoundException {
		Product product = findById(id);
		productRepository.delete(product);
	}
	
}
