package br.com.deveficiente.ingressos.pessoasusuarias;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaLimpa {

	private String texto;

	public SenhaLimpa(String texto) {
		super();
		this.texto = texto;
	}
	
	public String encodaBcrypt() {
		return new BCryptPasswordEncoder().encode(texto);
	}

}
