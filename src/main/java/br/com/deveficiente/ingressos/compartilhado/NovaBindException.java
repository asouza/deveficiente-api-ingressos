package br.com.deveficiente.ingressos.compartilhado;

import org.springframework.validation.BindException;

/**
 * Classe utilitaria para facilitar lançar novas {@link BindException} quando 
 * for realizar validacoes no Controller. Sim, é para ser utilizada primordialmente
 * em controllers, usou longe de lá, precisa ter um bom motivo. 
 * @author albertoluizsouza
 *
 */
public class NovaBindException {

	public static void lanca(Object request,String... mensagens) throws BindException {
		BindException bindException = new BindException(request,"");
		
		for(String mensagem : mensagens) {
			bindException.reject(null, mensagem);			
		}
		throw bindException;		
	}
}
