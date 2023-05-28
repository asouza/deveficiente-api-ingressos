package br.com.deveficiente.ingressos.compartilhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import feign.FeignException;

/**
 * Faz a execução remota com o feign lidando com possíveis problema 
 * na chamada de rede
 * @author albertoluizsouza
 *
 */
@Component
public class ExecucaoRemotaComOpenFeign {
	
	
	private static final Logger log = LoggerFactory
			.getLogger(ExecucaoRemotaComOpenFeign.class);


	public HttpStatus executa(Runnable runnable) {
		try {
			runnable.run();
			return HttpStatus.OK;
		} catch (FeignException feignException) {
			Log5WBuilder
				.metodo()
				.oQueEstaAcontecendo("Problema na hora de realizar uma chamada remota")
				.adicionaInformacao("status", feignException.status())
				.adicionaInformacao("possivelMotivo", feignException.contentUTF8())
				.erro(log);
				
			return HttpStatus.valueOf(feignException.status());
		}
	}
}
