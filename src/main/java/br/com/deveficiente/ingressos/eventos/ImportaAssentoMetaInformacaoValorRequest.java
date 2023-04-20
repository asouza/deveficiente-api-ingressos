package br.com.deveficiente.ingressos.eventos;

import java.util.function.BiConsumer;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((metaInformacao == null) ? 0 : metaInformacao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ImportaAssentoMetaInformacaoValorRequest other = (ImportaAssentoMetaInformacaoValorRequest) obj;
		if (metaInformacao == null) {
			if (other.metaInformacao != null)
				return false;
		} else if (!metaInformacao.equals(other.metaInformacao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	public void formata(BiConsumer<String, String> funcaoFormatadora) {
		funcaoFormatadora.accept(metaInformacao, valor);
	}
	

}
