package br.com.deveficiente.ingressos.empresas;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class NovoPlanoAssinaturaRequest {

	@NotBlank
	private String nome;

	@NotNull
	@Positive
	private BigDecimal valor;

	public NovoPlanoAssinaturaRequest(String nome, BigDecimal valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	public PlanoAssinatura toModel(Empresa empresa) {
		return new PlanoAssinatura(empresa, nome, valor);
	}

}