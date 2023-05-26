package br.com.deveficiente.ingressos.empresas;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;

@RestController
public class DetalhePlanoAssinaturaController {

	private EntityManager manager;

	public DetalhePlanoAssinaturaController(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@GetMapping("/api/planos-assinatura/{idPlano}")
	public DetalhesPlanoAssinaturaResponse executa(
			@PathVariable("idPlano") Long idPlano) {
		
		PlanoAssinatura plano = manager.find(PlanoAssinatura.class, idPlano);

		Optional.ofNullable(plano).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return new DetalhesPlanoAssinaturaResponse(plano);

	}
}
