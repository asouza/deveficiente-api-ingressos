package br.com.deveficiente.ingressos.empresas;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@RestController
public class AdicionaBeneficioPlanoController {

	private EntityManager manager;

	public AdicionaBeneficioPlanoController(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@PostMapping("/api/planos-assinatura/{idPlano}/beneficios")
	@Transactional
	public void executa(@AuthenticatedPrincipal Empresa empresa,
			@PathVariable("idPlano") Long idPlano,
			@RequestBody @Validated NovoBeneficioPlanoRequest request) {

		PlanoAssinatura plano = manager.find(PlanoAssinatura.class, idPlano);
				
		Optional.ofNullable(plano).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if(!empresa.ehDonaDoPlano(plano)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		plano.adicionaBeneficio(request::toModel);
		manager.merge(plano);
	}
}
