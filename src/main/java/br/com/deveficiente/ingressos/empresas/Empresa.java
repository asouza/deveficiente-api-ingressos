package br.com.deveficiente.ingressos.empresas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.MERGE)
	private Set<PlanoAssinatura> planos = new HashSet<>();

	@Deprecated
	public Empresa() {
		// TODO Auto-generated constructor stub
	}

	public Empresa(@NotBlank String nome) {
		super();
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Empresa other = (Empresa) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nome=" + nome + "]";
	}


	public boolean existeAlgumPlano() {
		return planos.iterator().hasNext();
	}

	public PlanoAssinatura criaNovoPlano(
			Function<Empresa, PlanoAssinatura> criadorPlano) {
		
		PlanoAssinatura novoPlano = criadorPlano.apply(this);
		
		Assert.state(planos.add(novoPlano),"Não foi possível adicionar o novo plano. Provavalmente já existe outro igual. Detalhes do plano que falhou:"+novoPlano.getNome());
		
		return novoPlano;
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return this.nome;
	}
	
	public boolean ehDonaDoPlano(PlanoAssinatura plano) {
		return this.planos.contains(plano);
	}

}
