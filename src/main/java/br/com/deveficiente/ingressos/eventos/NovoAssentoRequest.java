package br.com.deveficiente.ingressos.eventos;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class NovoAssentoRequest {

	private Set<@Valid ImportaAssentoMetaInformacaoValorRequest> pares = new HashSet<>();

	@JsonCreator(mode = Mode.PROPERTIES)
	public NovoAssentoRequest(
			Set<@Valid ImportaAssentoMetaInformacaoValorRequest> pares) {
		super();
		this.pares = pares;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pares == null) ? 0 : pares.hashCode());
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
		NovoAssentoRequest other = (NovoAssentoRequest) obj;
		if (pares == null) {
			if (other.pares != null)
				return false;
		} else if (!pares.equals(other.pares))
			return false;
		return true;
	}



	public Set<ImportaAssentoMetaInformacaoValorRequest> getPares() {
		return pares;
	}

	public Assento toAssento(@Valid @NotNull LayoutEvento layout) {
		return new Assento(layout, pares);
	}


	public void formataPares(BiConsumer<String, String> funcaoFormatacao) {
		this.pares.forEach(par -> par.formata(funcaoFormatacao));
	}

}
