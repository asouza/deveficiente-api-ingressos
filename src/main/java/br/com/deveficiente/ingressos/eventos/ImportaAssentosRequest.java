package br.com.deveficiente.ingressos.eventos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ImportaAssentosRequest {

	@Size(min = 1)
	private Set<NovoAssentoRequest> novosAssentosRequest = new HashSet<>();

	@JsonCreator(mode = Mode.PROPERTIES)
	public ImportaAssentosRequest(
			@Size(min = 1) Set<NovoAssentoRequest> novosAssentosRequest) {
		super();
		this.novosAssentosRequest = novosAssentosRequest;
	}

	public Collection<Assento> toAssentos(@Valid @NotNull LayoutEvento layout) {
		Assert.notNull(layout, "O layout não pode ser nulo");

		Set<Assento> assentosUnicos = new HashSet<>();
		return novosAssentosRequest.stream().map(request -> {
			Assento novoAssento = request.toAssento(layout);
			Assert.state(assentosUnicos.add(novoAssento),
					"Não deveria ter assentos repetidos neste ponto do código");

			return novoAssento;
		}).toList();
	}

}
