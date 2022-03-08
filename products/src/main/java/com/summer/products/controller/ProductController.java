package com.summer.products.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

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

	@Autowired
	private ProductService productService;
	
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
			@RequestBody Product produto){
		
		productService.saveProduct(produto);
		URI location = URI.create(String.format("/produtos/%s", produto.getId()));
		return ResponseEntity.created(location).body(produto);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product newproduto) throws ProductNotFoundException {
	
		Product oldproduto = productService.findById(id);
		
		oldproduto.setName(newproduto.getNome());
		oldproduto.setPreco(newproduto.getPreco());
		oldproduto.setQuantidade(newproduto.getQuantidade());
		oldproduto.setTipo(newproduto.getTipo());
		
		final Product produtoResult = productService.saveProduct(oldproduto);
		return ResponseEntity.ok(produtoResult);
		
	}
	//Update Nome
	@PatchMapping("/{id}/nome/{newNome}")
	public ResponseEntity<Product> patchProduct(@PathVariable Long id, @PathVariable String newNome) {
		try {
			Product produto = productService.findById(id);
			produto.setName(newNome);

			Product productResult = productService.saveProduct(produto);

			return ResponseEntity.ok(productResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Preco
	@PatchMapping("/{id}/preco/{newPreco}")
	public ResponseEntity<Product> patchWorker(@PathVariable Long id, @PathVariable BigDecimal newPreco) {
		try {
			Product produto = productService.findById(id);
			produto.setPreco(newPreco);

			Product productResult = productService.saveProduct(produto);

			return ResponseEntity.ok(productResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Quantidade
	@PatchMapping("/{id}/quantidade/{newQuantidade}")
	public ResponseEntity<Product> patchWorker(@PathVariable Long id, @PathVariable Integer newQuantidade) {
		try {
			Product produto = productService.findById(id);
			produto.setQuantidade(newQuantidade);

			Product productResult = productService.saveProduct(produto);

			return ResponseEntity.ok(productResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Tipo
	@PatchMapping("/{id}/tipo/{newTipo}")
	public ResponseEntity<Product> patchWorker(@PathVariable Long id, @PathVariable Tipos newTipo) {
		try {
			Product produto = productService.findById(id);
			produto.setTipo(newTipo);

			Product productResult = productService.saveProduct(produto);

			return ResponseEntity.ok(productResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
}




