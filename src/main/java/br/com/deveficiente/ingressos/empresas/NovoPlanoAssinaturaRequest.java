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

	@NotNull
	private Boolean opcaoPrimaria;

	public NovoPlanoAssinaturaRequest(String nome, BigDecimal valor,
			Boolean opcaoPrimaria) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.opcaoPrimaria = opcaoPrimaria;
	}

	public PlanoAssinatura toModel(Empresa empresa) {
		return new PlanoAssinatura(empresa, nome, valor, opcaoPrimaria);
	}

	public boolean isOpcaoPrimaria() {
		return opcaoPrimaria;
	}

}