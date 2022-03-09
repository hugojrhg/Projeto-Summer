package com.summer.payments.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.summer.payments.enums.Tipos;

@Entity
@Table(name="Pagamento")
public class Payment implements Serializable {
    
    private static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dataDePagamento = LocalDate.now();
	@OneToMany(mappedBy= "user", targetEntity = Orders.class)
	private List<Long> orderId;
	private Tipos formaPagamento;
	private BigDecimal valorTotal;
	private String nomePagante;
	private Integer parcelas;
	
	public Payment() {}
	
	public Payment(Long id, LocalDate dataDePagamento, List<Long> orderId, Tipos formaPagamento, BigDecimal valorTotal,
			String nomePagante, Integer parcelas) {
		super();
		this.id = id;
		this.dataDePagamento = dataDePagamento;
		this.orderId = orderId;
		this.formaPagamento = formaPagamento;
		this.valorTotal = valorTotal;
		this.nomePagante = nomePagante;
		this.parcelas = parcelas;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDataDePagamento() {
		return dataDePagamento;
	}

	public void setDataDePagamento(LocalDate dataDePagamento) {
		this.dataDePagamento = dataDePagamento;
	}

	public List<Long> getOrderId() {
		return orderId;
	}

	public void setOrderId(List<Long> orderId) {
		this.orderId = orderId;
	}

	public Tipos getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Tipos formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getNomePagante() {
		return nomePagante;
	}

	public void setNomePagante(String nomePagante) {
		this.nomePagante = nomePagante;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
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
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", dataDePagamento=" + dataDePagamento + ", orderId=" + orderId
				+ ", formaPagamento=" + formaPagamento + ", valorTotal=" + valorTotal + ", nomePagante=" + nomePagante
				+ ", parcelas=" + parcelas + "]";
	}

	
	
}
