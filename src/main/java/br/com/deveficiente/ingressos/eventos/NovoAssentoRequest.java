package br.com.deveficiente.ingressos.eventos;

import java.util.HashSet;
import java.util.Set;

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

	public Assento toAssento(@Valid @NotNull LayoutEvento layout) {
		return new Assento(layout, pares);
	}

}
