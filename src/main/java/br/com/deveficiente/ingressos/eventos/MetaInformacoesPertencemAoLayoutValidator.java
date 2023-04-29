package br.com.deveficiente.ingressos.eventos;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class MetaInformacoesPertencemAoLayoutValidator implements Validator {

	private EntityManager manager;
	private HttpServletRequest servletRequest;

	public MetaInformacoesPertencemAoLayoutValidator(EntityManager manager,
			HttpServletRequest request) {
		super();
		this.manager = manager;
		this.servletRequest = request;
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

		System.out.println("chegou aqui...");
		ImportaAssentosRequest request = (ImportaAssentosRequest) target;

		Long idLayout = RecuperaValorPathVariable.longValue(servletRequest,
				"idLayout");
		LayoutEvento layout = manager.find(LayoutEvento.class, idLayout);
		Assert.state(Objects.nonNull(layout),
				"O idLayout se refere a um layout inexistente");

		Collection<String> metaInformacoes = request
				.getTodasMetaInformacoesDosAssentos();

		System.out.println(metaInformacoes);
		metaInformacoes.stream()
				.filter(meta -> !layout.suportaMetaInformacao(meta))
				.forEach(metaInformacaoNaoSuportada -> {
					errors.reject(null, "A " + metaInformacaoNaoSuportada
							+ " não é suportada");
				});

	}

}
