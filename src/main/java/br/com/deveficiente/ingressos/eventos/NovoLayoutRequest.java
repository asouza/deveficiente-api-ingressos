package br.com.deveficiente.ingressos.eventos;

import java.util.HashSet;
import java.util.Set;

import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class NovoLayoutRequest {

	@Positive
	private int quantidadeAssentos;
	private Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento = new HashSet<>();
	@NotNull
	private Boolean aceitaOverbooking;
	@NotBlank
	private String nome;

	public NovoLayoutRequest(@Positive int quantidadeAssentos,
			Boolean aceitaOverbooking,String nome) {
		super();
		this.quantidadeAssentos = quantidadeAssentos;
		this.aceitaOverbooking = aceitaOverbooking;
		this.nome = nome;
	}

	public void setMetaInformacoesAssento(Set<String> metaInformacoesAssento) {
		this.metaInformacoesAssento = metaInformacoesAssento;
	}

	public LayoutEvento toLayout(Empresa empresa) {
		if(metaInformacoesAssento.isEmpty()) {
			//aqui podia ser alguma meta-informacao configurada
			metaInformacoesAssento.add("geral");
		}
		return new LayoutEvento(empresa,quantidadeAssentos,aceitaOverbooking,metaInformacoesAssento,nome);
	}

}
