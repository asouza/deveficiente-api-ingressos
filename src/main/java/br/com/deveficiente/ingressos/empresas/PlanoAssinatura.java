package br.com.deveficiente.ingressos.empresas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class PlanoAssinatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;

	@ElementCollection
	private List<ValorPlano> valores = new ArrayList<>();

	@NotNull
	@Valid
	@ManyToOne
	private Empresa empresa;

	@OneToMany(mappedBy = "plano", cascade = CascadeType.MERGE)
	private Set<BeneficioPlano> beneficios = new HashSet<>();

	@Deprecated
	public PlanoAssinatura() {
		// TODO Auto-generated constructor stub
	}

	public PlanoAssinatura(@NotNull @Valid Empresa empresa,
			@NotBlank String nome, @Positive BigDecimal valor) {
		super();
		this.empresa = empresa;
		this.nome = nome;
		this.valores.add(new ValorPlano(valor));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		PlanoAssinatura other = (PlanoAssinatura) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void adicionaBeneficio(
			Function<PlanoAssinatura, BeneficioPlano> criadorBeneficio) {
		BeneficioPlano novoBeneficio = criadorBeneficio.apply(this);
		Assert.isTrue(this.beneficios.add(novoBeneficio),
				"Já existe um beneficio de nome [" + novoBeneficio.getNome()
						+ "] de mesmo para o plano [" + this.nome
						+ "] da empresa [" + this.empresa.getNome() + "]");
	}

}