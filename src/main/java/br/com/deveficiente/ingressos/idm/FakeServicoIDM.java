package br.com.deveficiente.ingressos.idm;

import org.springframework.stereotype.Component;

@Component
public class FakeServicoIDM implements ServicoRemotoIDM{

	@Override
	public void novaEmpresa(NovaEmpresaIDMRequest novaEmpresaIDMRequest) {
		System.out.println("Cadastrando uma nova empresa. "+novaEmpresaIDMRequest);
	}

}
