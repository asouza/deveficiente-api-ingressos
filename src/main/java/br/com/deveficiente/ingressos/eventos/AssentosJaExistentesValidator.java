package br.com.deveficiente.ingressos.eventos;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

@Component
@RequestScope
public class AssentosJaExistentesValidator implements Validator {

	private EntityManager manager;
	private HttpServletRequest springWebRequest;

	public AssentosJaExistentesValidator(EntityManager manager,
			HttpServletRequest springWebRequest) {
		super();
		this.manager = manager;
		this.springWebRequest = springWebRequest;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ImportaAssentosRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}

		// #post Acho que vale um post rápido dessa gambi
		@SuppressWarnings("unchecked")
		Map<String, String> pathVariables = (Map<String, String>) springWebRequest
				.getAttribute(
						"org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");

		String idLayout = pathVariables.get("idLayout");

		Assert.state(Optional.ofNullable(idLayout).isPresent(),
				"Esse validador precisa da variavel idLayout como pathvariable");

		LayoutEvento layout = manager.find(LayoutEvento.class,
				Long.valueOf(idLayout));

		Assert.state(Optional.ofNullable(layout).isPresent(),
				"O id de layout passado não existe");

		ImportaAssentosRequest request = (ImportaAssentosRequest) target;

		Collection<Assento> assentosASeremAdicionados = request
				.toAssentos(layout);

		Collection<Assento> existentes = layout
				.buscaAssentosExistentes(assentosASeremAdicionados);

		existentes.forEach(assentoExistente -> {
			errors.reject(null,
					"Existem assentos que já estão cadastrados =>" + assentoExistente);			
		});

	}

}
