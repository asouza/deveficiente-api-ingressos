package br.com.deveficiente.ingressos.email;

/**
 * Abstração para o serviço de email que eventualmente vai ser utilizado
 * @author albertoluizsouza
 *
 */
public interface ServicoRemotoEmail {

	void envia(String titulo, String to, String corpo);

	
}
