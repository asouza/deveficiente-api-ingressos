package br.com.deveficiente.ingressos.idm;

import jakarta.validation.constraints.NotBlank;

public class NovaEmpresaIDMRequest {

	public final @NotBlank String nome;

	public NovaEmpresaIDMRequest(@NotBlank String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "NovaEmpresaIDMRequest [nome=" + nome + "]";
	}
	
	

}
