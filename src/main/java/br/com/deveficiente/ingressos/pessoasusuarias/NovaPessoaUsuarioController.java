package br.com.deveficiente.ingressos.pessoasusuarias;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.compartilhado.AuthenticatedPrincipal;
import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class NovaPessoaUsuarioController {

	private PessoaUsuariaRepository pessoaUsuariaRepository;
	private EntityManager manager;

	public NovaPessoaUsuarioController(
			PessoaUsuariaRepository pessoaUsuariaRepository,
			EntityManager manager) {
		super();
		this.pessoaUsuariaRepository = pessoaUsuariaRepository;
		this.manager = manager;
	}

	@PostMapping("/api/empresas/pessoas-usuarias")
	@Transactional
	public void executa(@AuthenticatedPrincipal Empresa empresa,
			@RequestBody @Valid NovaPessoaUsuariaRequest request) {

		empresa.adicionaPessoaUsuaria(request::toModel,
				pessoaUsuariaRepository);
		manager.merge(empresa);

	}
}
