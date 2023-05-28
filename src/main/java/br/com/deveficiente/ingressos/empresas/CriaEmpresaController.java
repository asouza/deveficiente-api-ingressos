package br.com.deveficiente.ingressos.empresas;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.compartilhado.ExecutaTransacao;
import br.com.deveficiente.ingressos.idm.NovaEmpresaIDMRequest;
import br.com.deveficiente.ingressos.idm.ServicoRemotoIDM;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@RestController
public class CriaEmpresaController {

	private EntityManager manager;
	private ExecutaTransacao executaTransacao;
	private ServicoRemotoIDM servicoRemotoIDM;

	public CriaEmpresaController(EntityManager manager,
			ExecutaTransacao executaTransacao,
			ServicoRemotoIDM servicoRemotoIDM) {
		super();
		this.manager = manager;
		this.executaTransacao = executaTransacao;
		this.servicoRemotoIDM = servicoRemotoIDM;
	}

	@PostMapping("/api/empresas")
	public void executa(@Valid @RequestBody NovaEmpresaRequest request) {
		Empresa novaEmpresa = new Empresa(request.nome);

		executaTransacao.executa(() -> {
			manager.persist(novaEmpresa);			
		});
		
		servicoRemotoIDM.novaEmpresa(new NovaEmpresaIDMRequest(novaEmpresa.getNome()));

	}
}
