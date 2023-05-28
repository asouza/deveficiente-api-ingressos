package br.com.deveficiente.ingressos.idm;

public interface ServicoRemotoIDM {
	
	/**
	 * Faz o necessário no IDM para registrar uma nova empresa
	 * @param novaEmpresaIDMRequest
	 */
	void novaEmpresa(NovaEmpresaIDMRequest novaEmpresaIDMRequest);

}
