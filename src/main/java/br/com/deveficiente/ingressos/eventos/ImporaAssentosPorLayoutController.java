package br.com.deveficiente.ingressos.eventos;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class ImporaAssentosPorLayoutController {

	private EntityManager manager;

	public ImporaAssentosPorLayoutController(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@PostMapping("/api/eventos/layout/{idLayout}/importa-assentos")
	@Transactional
	public ResponseEntity<String> executa(@AuthenticatedPrincipal Empresa empresa,
			@PathVariable("idLayout") Long idLayout,
			@Valid @RequestBody ImportaAssentosRequest request) {

		LayoutEvento layout = manager.find(LayoutEvento.class,
				idLayout);
		
		if(layout == null) {
			return ResponseEntity.notFound().build();
		}

		if(!layout.pertenceAEmpresa(empresa)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		Collection<Assento> assentos = request.toAssentos(layout);
		assentos.forEach(manager :: persist);
		
		return ResponseEntity.ok("Assentos importados");
	}
}
