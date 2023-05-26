package br.com.deveficiente.ingressos.empresas;

import jakarta.validation.constraints.NotBlank;

public class NovoBeneficioPlanoRequest {

	@NotBlank
	private String nomeBeneficio;
	@NotBlank
	private String descricao;

	public NovoBeneficioPlanoRequest(String nomeBeneficio,
			String descricao) {
		super();
		this.nomeBeneficio = nomeBeneficio;
		this.descricao = descricao;
	}
	
	public BeneficioPlano toModel(PlanoAssinatura plano) {
		return new BeneficioPlano(plano,nomeBeneficio,descricao);
	}

}
