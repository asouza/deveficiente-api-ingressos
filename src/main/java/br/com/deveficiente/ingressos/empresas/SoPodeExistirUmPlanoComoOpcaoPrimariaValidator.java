package br.com.deveficiente.ingressos.empresas;

import org.springframework.stereotype.Component;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import jakarta.validation.Valid;

@Component
public class SoPodeExistirUmPlanoComoOpcaoPrimariaValidator {

	public boolean valida(@AuthenticatedPrincipal Empresa empresa,
			@Valid NovoPlanoAssinaturaRequest request) {
		if(!request.isOpcaoPrimaria()) {
			return true;
		}
		return !empresa.existePlanoComoOpcaoPrimaria();
	}

	
}
