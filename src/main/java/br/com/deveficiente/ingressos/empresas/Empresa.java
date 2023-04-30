package br.com.deveficiente.ingressos.empresas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	public boolean existePlanoComoOpcaoPrimaria() {
		verificaInvariante();
		return planos.stream().filter(PlanoAssinatura::isOpcaoPrimaria)
				.findFirst().isPresent();
	}

	private void verificaInvariante() {
		long numeroDePlanosPrimarios = planos.stream()
				.filter(PlanoAssinatura::isOpcaoPrimaria).count();
		Assert.state(numeroDePlanosPrimarios <= 1,
				"Por algum motivo do planeta existe mais de um plano como opcao primaria para a empresa "
						+ this.nome);
	}

	public boolean existeAlgumPlano() {
		verificaInvariante();
		return planos.iterator().hasNext();
	}

}
