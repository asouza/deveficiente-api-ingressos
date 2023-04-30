package br.com.deveficiente.ingressos.empresas;

import org.springframework.stereotype.Component;

import jakarta.validation.Valid;

@Component
public class OPrimeiroPlanoPrecisaSerOPrimarioValidator {

	public boolean valida(Empresa empresa,
			@Valid NovoPlanoAssinaturaRequest request) {
		if(empresa.existeAlgumPlano()) {
			return true;
		}
		
		return request.isOpcaoPrimaria();
	}

}
