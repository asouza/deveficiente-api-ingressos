package br.com.deveficiente.ingressos.eventos;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class CriaNovoLayoutController {

	private EntityManager manager;

	public CriaNovoLayoutController(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@PostMapping("/api/{idEmpresa}/eventos/layout")
	@Transactional
	public void executa(@PathVariable("idEmpresa") Long idEmpresa,
			@RequestBody @Valid NovoLayoutRequest request) {
		Optional<Empresa> possivelEmpresa = Optional
				.ofNullable(manager.find(Empresa.class, idEmpresa));

		possivelEmpresa.map(empresa -> {
			LayoutEvento novoLayout = request.toLayout(empresa);
			manager.persist(novoLayout);
			return ResponseEntity.ok("Novo layout criado");
		})
		.orElse(ResponseEntity.notFound().build());

	}

}
