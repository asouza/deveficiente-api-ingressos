package br.com.deveficiente.ingressos.eventos;

import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Representa uma função acoplada ao Spring que sabe recuperar valores declarados
 * como PathVariable.
 * 
 * @author albertoluizsouza
 *
 */

// #post Acho que vale um post rápido dessa gambi e sobre a alucinação do chat gpt
public class RecuperaValorPathVariable {

	/**
	 * 
	 * @param request 
	 * @param nomeVariavel nome da variavel que foi declarada no path variable do mapeamento de url
	 * @return
	 */
	public static Long longValue(HttpServletRequest request, String nomeVariavel){
		@SuppressWarnings("unchecked")
		Map<String, String> pathVariables = (Map<String, String>) request
				.getAttribute(
						"org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");

		String valor = pathVariables.get(nomeVariavel);
		
		Assert.state(Optional.ofNullable(valor).isPresent(),
				"A variavel "+nomeVariavel+" não foi encontrada como pathvariable");
		
		Assert.state(StringUtils.hasText(valor),"A variavel foi encontrada como pathvariable, mas está vazia");
		
		return Long.valueOf(valor);
				
		
		
		
	}
}
