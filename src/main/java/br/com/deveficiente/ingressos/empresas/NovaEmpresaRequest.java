package br.com.deveficiente.ingressos.empresas;

import jakarta.validation.constraints.NotBlank;

public class NovaEmpresaRequest {

	@NotBlank
	public final String nome;
	@NotBlank
	public final String emailContato;

	public NovaEmpresaRequest(String nome, String emailContato) {
		super();
		this.nome = nome;
		this.emailContato = emailContato;
	}

	public Empresa toModel() {
		return new Empresa(nome,emailContato);
	}
	
	
}
