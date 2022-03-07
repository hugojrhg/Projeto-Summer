package com.summer.users.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders implements Serializable {
    private static final long serialVersionUID = 1l;
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Long id_produto;
	private Integer qtd_produtos;
	private BigDecimal valor;
	private Long user;
		
	public Orders() {}


	
	public Orders(Long id, String descricao, Long id_produto, Integer qtd_produtos, BigDecimal valor) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.id_produto = id_produto;
		this.qtd_produtos = qtd_produtos;
		this.valor = valor;
	}



	public Long getId() {
		return id;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Long getId_produto() {
		return id_produto;
	}


	public void setId_produto(Long id_produto) {
		this.id_produto = id_produto;
	}


	public Integer getQtd_produtos() {
		return qtd_produtos;
	}


	public void setQtd_produtos(Integer qtd_produtos) {
		this.qtd_produtos = qtd_produtos;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, id_produto);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orders other = (Orders) obj;
		return Objects.equals(id, other.id) && Objects.equals(id_produto, other.id_produto);
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", descricao=" + descricao + ", id_pedido=" + id_produto + ", qtd_produtos="
				+ qtd_produtos + ", valor=" + valor + "]";
	}



	public Long getUser() {
		return user;
	}



	public void setUser(Long user) {
		this.user = user;
	}
	
	
    
}

