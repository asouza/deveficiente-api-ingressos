package br.com.deveficiente.ingressos.compartilhado;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

/**
 * Abstração para executar trechos de código em escopo transacional.
 * @author albertoluizsouza
 *
 */
@Component
public class ExecutaTransacao {

	@Transactional
	public void executa(Runnable runnable) {
		runnable.run();
	}
	
	@Transactional
	public <T> T executa(Supplier<T> supplier) {
		return supplier.get();
	}
}
