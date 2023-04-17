package br.com.deveficiente.ingressos.eventos;

import jakarta.validation.constraints.NotBlank;

public class ImportaAssentoMetaInformacaoValorRequest {

	@NotBlank
	private String metaInformacao;
	@NotBlank
	private String valor;

	public ImportaAssentoMetaInformacaoValorRequest(String metaInformacao, String valor) {
		super();
		this.metaInformacao = metaInformacao;
		this.valor = valor;
	}
	
	public String getMetaInformacao() {
		return metaInformacao;
	}
	
	public String getValor() {
		return valor;
	}

}
