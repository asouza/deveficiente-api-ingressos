package br.com.deveficiente.ingressos.empresas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

/**
 * 
 * @author albertoluizsouza
 *
 */
@Embeddable
public class ValorPlano {

	@Positive
	private BigDecimal valor;
	@PastOrPresent
	private LocalDateTime instanteCriacao;
	
	@Deprecated
	public ValorPlano() {
		// TODO Auto-generated constructor stub
	}

	public ValorPlano(@Positive BigDecimal valor) {
		super();
		this.valor = valor;
		this.instanteCriacao = LocalDateTime.now();
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instanteCriacao == null) ? 0 : instanteCriacao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValorPlano other = (ValorPlano) obj;
		if (instanteCriacao == null) {
			if (other.instanteCriacao != null)
				return false;
		} else if (!instanteCriacao.equals(other.instanteCriacao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	

}
