package com.summer.products.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import com.summer.products.exception.InvalidInputException;
import com.summer.products.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.summer.products.enums.Tipos;
import com.summer.products.model.Product;
import com.summer.products.service.ProductService;

@RestController
@RequestMapping(value = "/produtos")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService=productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		List<Product> produtos = productService.findAll();
		return ResponseEntity.ok(produtos);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) throws ProductNotFoundException {

		Product produto = productService.findById(id);
		return ResponseEntity.ok(produto);
	}
	
	@PostMapping()
	public ResponseEntity<Product> createProduct(@RequestHeader(value="api-key") String string,
			@RequestBody Product produto) throws InvalidInputException {
		
		productService.saveProduct(produto);
		return ResponseEntity.created(URI.create(String.format("/produtos/%s", produto.getId()))).body(produto);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product newproduto)
			throws ProductNotFoundException, InvalidInputException {

		Product produtoResult = productService.putProduct(id,newproduto );
		return ResponseEntity.ok(produtoResult);
		
	}
	//Update Nome
	@PatchMapping("/{id}/nome/{newNome}")
	public ResponseEntity<Product> patchProduct(@PathVariable Long id, @PathVariable String newNome) throws ProductNotFoundException, InvalidInputException {
		var product = productService.patchName(id,newNome);
		return ResponseEntity.ok(product);
	}
	//Update Preco
	@PatchMapping("/{id}/preco/{newPreco}")
	public ResponseEntity<Product> patchWorker(@PathVariable Long id, @PathVariable BigDecimal newPreco) throws ProductNotFoundException, InvalidInputException {
		var product = productService.patchPreco(id,newPreco);
		return ResponseEntity.ok(product);
	}
	//Update Quantidade
	@PatchMapping("/{id}/quantidade/{newQuantidade}")
	public ResponseEntity<Product> patchWorker(@PathVariable Long id, @PathVariable Integer newQuantidade) throws ProductNotFoundException, InvalidInputException {
		var product = productService.patchQuantidade(id,newQuantidade);
		return ResponseEntity.ok(product);
	}
	//Update Tipo
	@PatchMapping("/{id}/tipo/{newTipo}")
	public ResponseEntity<Product> patchWorker(@PathVariable Long id, @PathVariable Tipos newTipo)
			throws ProductNotFoundException, InvalidInputException {
		var product = productService.patchTipo(id,newTipo);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) throws ProductNotFoundException {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
}




