package br.com.deveficiente.ingressos.email;

import org.springframework.stereotype.Component;

@Component
public class FakeServicoRemotoEmail implements ServicoRemotoEmail {

	@Override
	public void envia(String titulo, String to, String corpo) {
		System.out.println("Enviando email");
		System.out.println("Titulo = "+titulo);
		System.out.println("To = "+to);
		System.out.println("Corpo = "+corpo);
	}

}
