package br.com.deveficiente.ingressos.eventos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ImportaAssentosRequest {

	@Size(min = 1)
	private List<NovoAssentoRequest> novosAssentosRequest = new ArrayList<>();

	@JsonCreator(mode = Mode.PROPERTIES)
	public ImportaAssentosRequest(
			@Size(min = 1) List<NovoAssentoRequest> novosAssentosRequest) {
		super();
		this.novosAssentosRequest = novosAssentosRequest;
	}

	public Collection<Assento> toAssentos(@Valid @NotNull LayoutEvento layout) {
		Assert.notNull(layout, "O layout não pode ser nulo");
		Assert.state(this.buscaAssentosRepetidos().isEmpty(),"Não deveria ter assentos repetidos neste ponto do código");
		
		return novosAssentosRequest.stream().map(request -> {
			return request.toAssento(layout);
		}).toList();
	}

	public List<NovoAssentoRequest> buscaAssentosRepetidos() {
		Set<NovoAssentoRequest> assentosUnicos = new HashSet<>();
		
		List<NovoAssentoRequest> repetidos = novosAssentosRequest.stream().filter(novoAssentoRequest -> {
			return !assentosUnicos.add(novoAssentoRequest);
		}).toList();
		
		System.out.println("Repetidos => "+repetidos);
		return repetidos;
	}

}
