package com.summer.users.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name="Usuario")
public class User implements Serializable{

	private static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@CPF
	private String cpf;
	@Email
	private String email;
	//private String cep;
	@NotBlank
	private String numCartão;
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "user")
	private List<Orders> compras;
	
	public User() {}

	public User(Long id, String nome, @CPF String cpf, @Email String email, String numCartão, List<Orders> compras) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.numCartão = numCartão;
		this.compras = compras;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumCartão() {
		return numCartão;
	}

	public void setNumCartão(String numCartão) {
		this.numCartão = numCartão;
	}

	public List<Orders> getCompras() {
		return compras;
	}

	public void setCompras(List<Orders> compras) {
		this.compras = compras;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", numCartão=" + numCartão
				+ ", compras=" + compras + "]";
	}

	
	
	
	
}
