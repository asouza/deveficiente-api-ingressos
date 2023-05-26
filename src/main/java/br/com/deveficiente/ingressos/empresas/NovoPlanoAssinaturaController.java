package br.com.deveficiente.ingressos.empresas;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class NovoPlanoAssinaturaController {

	private EntityManager manager;

	public NovoPlanoAssinaturaController(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@PostMapping("/api/planos-assinatura")
	@Transactional
	public ResponseEntity<String> criaPlanoAssinatura(
			@AuthenticatedPrincipal Empresa empresa,
			@RequestBody @Valid NovoPlanoAssinaturaRequest request)
			throws BindException {

		empresa.criaNovoPlano(request::toModel);

		manager.merge(empresa);

		return ResponseEntity.ok("Novo plano criado");
	}

}