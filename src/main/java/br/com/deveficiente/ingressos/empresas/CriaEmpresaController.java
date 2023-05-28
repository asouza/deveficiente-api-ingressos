package br.com.deveficiente.ingressos.empresas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deveficiente.ingressos.compartilhado.ExecucaoRemotaComOpenFeign;
import br.com.deveficiente.ingressos.compartilhado.ExecutaTransacao;
import br.com.deveficiente.ingressos.compartilhado.Log5WBuilder;
import br.com.deveficiente.ingressos.email.ServicoRemotoEmail;
import br.com.deveficiente.ingressos.idm.NovaEmpresaIDMRequest;
import br.com.deveficiente.ingressos.idm.ServicoRemotoIDM;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@RestController
public class CriaEmpresaController {

	private EntityManager manager;
	private ExecutaTransacao executaTransacao;
	private ServicoRemotoIDM servicoRemotoIDM;
	private ServicoRemotoEmail servicoRemotoEmail;
	private ExecucaoRemotaComOpenFeign execucaoRemotaComOpenFeign;

	private static final Logger log = LoggerFactory
			.getLogger(CriaEmpresaController.class);

	public CriaEmpresaController(EntityManager manager,
			ExecutaTransacao executaTransacao,
			ServicoRemotoIDM servicoRemotoIDM,
			ServicoRemotoEmail servicoRemotoEmail,
			ExecucaoRemotaComOpenFeign execucaoRemotaComOpenFeign) {
		super();
		this.manager = manager;
		this.executaTransacao = executaTransacao;
		this.servicoRemotoIDM = servicoRemotoIDM;
		this.servicoRemotoEmail = servicoRemotoEmail;
		this.execucaoRemotaComOpenFeign = execucaoRemotaComOpenFeign;
	}

	@PostMapping("/api/empresas")
	public void executa(@Valid @RequestBody NovaEmpresaRequest request) {
		Empresa novaEmpresa = request.toModel();

		executaTransacao.executa(() -> {
			manager.persist(novaEmpresa);
		});

		// aqui tem que definir como vai ser a politica de retry
		// aqui também tem chance de brincar de eventos de domínio. Criar uma
		// empresa possivelmente seria um evento de domínio

		Log5WBuilder.metodo()
				.oQueEstaAcontecendo(
						"Tentando registrar uma nova empresa no idm")
				.adicionaInformacao("nomeEmpresa", novaEmpresa.getNome())
				.info(log);

		HttpStatus statusDominio = execucaoRemotaComOpenFeign.executa(() -> {

			servicoRemotoIDM.novaEmpresa(
					new NovaEmpresaIDMRequest(novaEmpresa.getNome()));

		});

		Log5WBuilder.metodo()
				.oQueEstaAcontecendo("Retorno da tentativa de registro de nova empresa")
				.adicionaInformacao("nomeEmpresa", novaEmpresa.getNome())
				.adicionaInformacao("status", statusDominio)
				.info(log);

		if (statusDominio.is2xxSuccessful()) {

			Log5WBuilder.metodo().oQueEstaAcontecendo(
					"Tentando enviar email de registro com sucesso da nova empresa")
					.adicionaInformacao("nomeEmpresa", novaEmpresa.getNome())
					.info(log);

			HttpStatus statusEmail = execucaoRemotaComOpenFeign.executa(() -> {
				servicoRemotoEmail.envia("email de contato da empresa",
						"titulo email", "corpo do email");
			});

			Log5WBuilder.metodo()
			.oQueEstaAcontecendo("Retorno da tentativa de envio de email para registro de nova empresa")
			.adicionaInformacao("nomeEmpresa", novaEmpresa.getNome())
			.adicionaInformacao("status", statusEmail)
			.info(log);

		}

	}
}
