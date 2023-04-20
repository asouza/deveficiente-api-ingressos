package br.com.deveficiente.ingressos.eventos;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AssentosUnicosValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ImportaAssentosRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}

		ImportaAssentosRequest request = (ImportaAssentosRequest) target;
		List<NovoAssentoRequest> assentosRepetidos = request
				.buscaAssentosRepetidos();

		assentosRepetidos.forEach(repetido -> {
			StringBuilder paresFormatado = new StringBuilder();

			repetido.formataPares((metaInformacao,valor) -> {
				paresFormatado.append(metaInformacao).append("-")
				.append(valor)
				.append(";");				
			});

			errors.reject(null, "O assento com as informacoes[" + paresFormatado
					+ "] está repetido");
		});
	}

}
