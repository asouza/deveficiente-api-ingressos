package br.com.deveficiente.ingressos.eventos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Assento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private @Valid @NotNull LayoutEvento layout;

	@ElementCollection
	@MapKeyColumn(name = "meta-informacao")
	@Column(name = "valor")
	private Map<String, String> valoresMetaInformacoesLayout = new HashMap<>();
	
	@Deprecated
	public Assento() {
		// TODO Auto-generated constructor stub
	}

	public Assento(@Valid @NotNull LayoutEvento layout,
			@Size(min = 1) Set<@Valid ImportaAssentoMetaInformacaoValorRequest> pares) {
		
		Assert.notNull(layout,"O layout não pode ser nulo");

		this.layout = layout;

		HashSet<Object> chavesUnicas = new HashSet<>();
		pares.forEach(par -> {
			String metaInformacao = par.getMetaInformacao();
			Assert.state(chavesUnicas.add(metaInformacao),
					"Não pode ter metainformacao igual para o mesmo assento");

			Assert.state(layout.suportaMetaInformacao(metaInformacao),"O layout não suporta a metainformacao => "+metaInformacao);
			
			valoresMetaInformacoesLayout.put(metaInformacao,
					par.getValor());

		});

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((layout == null) ? 0 : layout.hashCode());
		result = prime * result + ((valoresMetaInformacoesLayout == null) ? 0
				: valoresMetaInformacoesLayout.hashCode());
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
		Assento other = (Assento) obj;
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
			return false;
		if (valoresMetaInformacoesLayout == null) {
			if (other.valoresMetaInformacoesLayout != null)
				return false;
		} else if (!valoresMetaInformacoesLayout
				.equals(other.valoresMetaInformacoesLayout))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Assento [layout=" + layout.getNome() + ", valoresMetaInformacoesLayout="
				+ valoresMetaInformacoesLayout + "]";
	}
	
	
	
	

}
