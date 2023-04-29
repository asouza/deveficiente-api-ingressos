package br.com.deveficiente.ingressos.eventos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
	private AssentosUnicosValidator assentosUnicosValidator;
	private AssentosJaExistentesValidator assentosJaExistentesValidator;
	private MetaInformacoesPertencemAoLayoutValidator metaInformacoesPertencemAoLayoutValidator;

	public ImporaAssentosPorLayoutController(EntityManager manager,
			AssentosUnicosValidator assentosUnicosValidator,
			AssentosJaExistentesValidator assentosJaExistentesValidator,
			MetaInformacoesPertencemAoLayoutValidator metaInformacoesPertencemAoLayoutValidator) {
		super();
		this.manager = manager;
		this.assentosUnicosValidator = assentosUnicosValidator;
		this.assentosJaExistentesValidator = assentosJaExistentesValidator;
		this.metaInformacoesPertencemAoLayoutValidator = metaInformacoesPertencemAoLayoutValidator;
	}

	@InitBinder
	public void init(WebDataBinder binder) {
		/*
		 * #paraPensar Aqui eu tive que organizar a ordem de execução dos validadores
		 * porque em algum validador eu de fato instancio um novo assento. Só que quando 
		 * um novo assento vai ser instanciado ele verifica se as meta informacoes 
		 * batem com o layout
		 */
		binder.addValidators(assentosUnicosValidator,metaInformacoesPertencemAoLayoutValidator,assentosJaExistentesValidator);
	}

	@PostMapping("/api/eventos/layout/{idLayout}/importa-assentos")
	@Transactional
	public ResponseEntity<String> executa(
			@AuthenticatedPrincipal Empresa empresa,
			@PathVariable("idLayout") Long idLayout,
			@Valid @RequestBody ImportaAssentosRequest request) {

		LayoutEvento layout = manager.find(LayoutEvento.class, idLayout);

		if (layout == null) {
			return ResponseEntity.notFound().build();
		}

		if (!layout.pertenceAEmpresa(empresa)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		layout.adicionaAssentos(request::toAssentos);
		/*
		 * #naoSeiExplicar: Eu já passei por isso e continuo sem saber explicar.
		 * Pq eu preciso chamar esse merge aqui? Quando comita não deveria
		 * mergear automaticamente?
		 */
		manager.merge(layout);

		return ResponseEntity.ok("Assentos importados");
	}
}
