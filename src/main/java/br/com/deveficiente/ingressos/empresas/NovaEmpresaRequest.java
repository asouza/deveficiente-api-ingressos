package br.com.deveficiente.ingressos.empresas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.constraints.NotBlank;

public class NovaEmpresaRequest {

	@NotBlank
	public final String nome;

	@JsonCreator(mode = Mode.PROPERTIES)
	public NovaEmpresaRequest(String nome) {
		super();
		this.nome = nome;
	}
	
	
}
