package br.com.deveficiente.ingressos.empresas;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class PlanoAssinaturaController {

	private PlanoAssinaturaRepository planoAssinaturaRepository;
	private SoPodeExistirUmPlanoComoOpcaoPrimariaValidator soPodeExistirUmPlanoComoOpcaoPrimariaValidator;
	private OPrimeiroPlanoPrecisaSerOPrimarioValidator oPrimeiroPlanoPrecisaSerOPrimarioValidator;

	public PlanoAssinaturaController(
			PlanoAssinaturaRepository planoAssinaturaRepository,
			SoPodeExistirUmPlanoComoOpcaoPrimariaValidator soPodeExistirUmPlanoComoOpcaoPrimariaValidator,
			OPrimeiroPlanoPrecisaSerOPrimarioValidator oPrimeiroPlanoPrecisaSerOPrimarioValidator) {
		super();
		this.planoAssinaturaRepository = planoAssinaturaRepository;
		this.soPodeExistirUmPlanoComoOpcaoPrimariaValidator = soPodeExistirUmPlanoComoOpcaoPrimariaValidator;
		this.oPrimeiroPlanoPrecisaSerOPrimarioValidator = oPrimeiroPlanoPrecisaSerOPrimarioValidator;
	}

	@PostMapping("/api/planos-assinatura")
	@Transactional
	public ResponseEntity<String> criaPlanoAssinatura(
			@AuthenticatedPrincipal Empresa empresa,
			@RequestBody @Valid NovoPlanoAssinaturaRequest request) throws BindException {
		
		if(!oPrimeiroPlanoPrecisaSerOPrimarioValidator.valida(empresa,request)) {
			BindException bindException = new BindException(request,"");
			bindException.reject(null, "O primeiro plano precisa ser a opção padrao para novos usuários. Marque ele como opcao primaria");
			throw bindException;
		}

		// anatomia de um código mais longe do domínio
		if (!soPodeExistirUmPlanoComoOpcaoPrimariaValidator.valida(empresa,
				request)) {
			BindException bindException = new BindException(request,"");
			bindException.reject(null, "Já existe um plano como opcao primaria");
			throw bindException;
		}

		PlanoAssinatura planoAssinatura = request.toModel(empresa);
		planoAssinaturaRepository.save(planoAssinatura);

		return ResponseEntity.ok("Novo plano criado");
	}

}