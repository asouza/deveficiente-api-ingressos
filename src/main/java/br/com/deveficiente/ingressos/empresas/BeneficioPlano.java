package br.com.deveficiente.ingressos.empresas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class BeneficioPlano {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@NotNull
	private PlanoAssinatura plano;
	private @NotBlank String nome;
	private @NotBlank String descricao;

	
	@Deprecated
	public BeneficioPlano() {
		// TODO Auto-generated constructor stub
	}
	
	public BeneficioPlano(@NotNull @Valid PlanoAssinatura plano, @NotBlank String nome,
			@NotBlank String descricao) {
				this.plano = plano;
				this.nome = nome;
				this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((plano == null) ? 0 : plano.hashCode());
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
		BeneficioPlano other = (BeneficioPlano) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (plano == null) {
			if (other.plano != null)
				return false;
		} else if (!plano.equals(other.plano))
			return false;
		return true;
	}

	public String getNome() {
		return this.nome;
	}

	public String getDescricao() {
		return this.descricao;
	}
	
	

}
