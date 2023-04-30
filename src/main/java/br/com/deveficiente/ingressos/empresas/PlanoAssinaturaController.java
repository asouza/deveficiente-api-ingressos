package br.com.deveficiente.ingressos.empresas;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import br.com.deveficiente.ingressos.compartilhado.NovaBindException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class PlanoAssinaturaController {

	private EntityManager manager;
	private SoPodeExistirUmPlanoComoOpcaoPrimariaValidator soPodeExistirUmPlanoComoOpcaoPrimariaValidator;
	private OPrimeiroPlanoPrecisaSerOPrimarioValidator oPrimeiroPlanoPrecisaSerOPrimarioValidator;

	public PlanoAssinaturaController(EntityManager manager,
			SoPodeExistirUmPlanoComoOpcaoPrimariaValidator soPodeExistirUmPlanoComoOpcaoPrimariaValidator,
			OPrimeiroPlanoPrecisaSerOPrimarioValidator oPrimeiroPlanoPrecisaSerOPrimarioValidator) {
		super();
		this.manager = manager;
		this.soPodeExistirUmPlanoComoOpcaoPrimariaValidator = soPodeExistirUmPlanoComoOpcaoPrimariaValidator;
		this.oPrimeiroPlanoPrecisaSerOPrimarioValidator = oPrimeiroPlanoPrecisaSerOPrimarioValidator;
	}

	@PostMapping("/api/planos-assinatura")
	@Transactional
	public ResponseEntity<String> criaPlanoAssinatura(
			@AuthenticatedPrincipal Empresa empresa,
			@RequestBody @Valid NovoPlanoAssinaturaRequest request)
			throws BindException {

		if (!oPrimeiroPlanoPrecisaSerOPrimarioValidator.valida(empresa,
				request)) {
			NovaBindException.lanca(request,
					"O primeiro plano precisa ser a opção padrao para novos usuários. Marque ele como opcao primaria");

		}

		if (!soPodeExistirUmPlanoComoOpcaoPrimariaValidator.valida(empresa,
				request)) {
			NovaBindException.lanca(request,
					"Já existe um plano como opcao primaria");
		}

		empresa.criaNovoPlano(request::toModel);

		manager.merge(empresa);

		return ResponseEntity.ok("Novo plano criado");
	}

}