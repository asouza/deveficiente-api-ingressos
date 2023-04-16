package br.com.deveficiente.ingressos.eventos;

import java.util.Set;

import org.springframework.util.Assert;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class LayoutEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @Positive int quantidadeAssentos;
	private boolean aceitaOverbooking;
	@ElementCollection
	private Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento;

	public LayoutEvento(@Positive int quantidadeAssentos,
			boolean aceitaOverbooking,
			@Size(min = 1) Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento) {
		
		Assert.isTrue(metaInformacoesAssento.iterator().hasNext(),"Precisa de pelo menos uma metainformacao sobre o assento do evento");
		Assert.isTrue(quantidadeAssentos > 0, "O numero de assentos precisa ser positivo");
		
		this.quantidadeAssentos = quantidadeAssentos;
		this.aceitaOverbooking = aceitaOverbooking;
		this.metaInformacoesAssento = metaInformacoesAssento;
	}

}
