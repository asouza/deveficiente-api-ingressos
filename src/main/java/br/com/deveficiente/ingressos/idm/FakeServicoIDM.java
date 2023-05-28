package br.com.deveficiente.ingressos.idm;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FakeServicoIDM implements ServicoRemotoIDM{

	@Override
	public ResponseEntity<String> novaEmpresa(NovaEmpresaIDMRequest novaEmpresaIDMRequest) {
		System.out.println("Cadastrando uma nova empresa. "+novaEmpresaIDMRequest);
		
		return ResponseEntity.ok("Empresa cadastrada");
	}

}
