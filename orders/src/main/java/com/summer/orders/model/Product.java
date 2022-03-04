package com.summer.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.summer.orders.enums.Tipos;



public class Product implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private Long id;
	private String nome;
	private BigDecimal preco;
	private Integer quantidade;
	private Tipos tipo;
	
	public Product() {}
	
	public Product(Long id, String nome, BigDecimal preco, Integer quantidade, Tipos tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setName(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", nome=" + nome + ", preco=" + preco + ", quantidade=" + quantidade + ", tipo="
				+ tipo + "]";
	}
	
	
	
	
}

