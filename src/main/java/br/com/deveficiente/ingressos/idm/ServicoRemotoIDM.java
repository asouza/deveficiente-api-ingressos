package br.com.deveficiente.ingressos.idm;

import org.springframework.http.ResponseEntity;

public interface ServicoRemotoIDM {
	
	/**
	 * Faz o necessário no IDM para registrar uma nova empresa
	 * @param novaEmpresaIDMRequest
	 */
	 ResponseEntity<?> novaEmpresa(NovaEmpresaIDMRequest novaEmpresaIDMRequest);

}
