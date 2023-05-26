package br.com.deveficiente.ingressos.empresas;

import java.math.BigDecimal;
import java.util.Map;

public class DetalhesPlanoAssinaturaResponse {

	public final BigDecimal valor;
	public final Map<String, String> mapaBeneficios;

	public DetalhesPlanoAssinaturaResponse(PlanoAssinatura plano) {
		this.valor = plano.getValorAtual();
		this.mapaBeneficios = plano.getMapaBeneficios();
	}

}
