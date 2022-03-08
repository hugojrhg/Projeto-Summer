package com.summer.payments.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.summer.payments.enums.Tipos;

@Entity
@Table(name="Pagamento")
public class Payment implements Serializable {
    
    private static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String notafiscal;
	private BigDecimal valorpagamento;
	private Tipos tipo;
    private Integer parcelas;
	
	public Payment() {}
	
	public Payment(Long id, String notafiscal, BigDecimal valorpagamento, Tipos tipo, Integer parcelas) {
		super();
		this.id = id;
		this.notafiscal = notafiscal;
		this.valorpagamento = valorpagamento;
		this.tipo = tipo;
        this.parcelas = parcelas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNotaFiscal() {
		return notafiscal;
	}

	public void setNotaFiscal(String notafiscal) {
		this.notafiscal = notafiscal;
	}

	public BigDecimal getValorPagamento() {
		return valorpagamento;
	}

	public void setValorPagamento(BigDecimal valorpagamento) {
		this.valorpagamento = valorpagamento;
	}


	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
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
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", notafiscal=" + notafiscal + "  , valorpagamento=" + valorpagamento + ",  tipo="
				+ tipo + "parcelas=" + parcelas + " ]";
	}
}
