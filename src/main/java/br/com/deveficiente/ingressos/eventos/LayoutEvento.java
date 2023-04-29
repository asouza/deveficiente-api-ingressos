package br.com.deveficiente.ingressos.eventos;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.util.Assert;

import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@ManyToOne
	private Empresa empresa;
	@NotBlank
	private String nome;
	@OneToMany(mappedBy = "layout",cascade = {CascadeType.MERGE})
	private Set<Assento> assentos = new HashSet<>();
	
	@Deprecated
	public LayoutEvento() {
		// TODO Auto-generated constructor stub
	}

	public LayoutEvento(Empresa empresa, @Positive int quantidadeAssentos,
			boolean aceitaOverbooking,
			@Size(min = 1) Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento,
			@NotBlank String nome) {

		Assert.notNull(empresa, "Empresa não pode ser nula");
		Assert.isTrue(metaInformacoesAssento.iterator().hasNext(),
				"Precisa de pelo menos uma metainformacao sobre o assento do evento");
		Assert.isTrue(quantidadeAssentos > 0,
				"O numero de assentos precisa ser positivo");
		Assert.hasText(nome, "O nome do layout precisa estar preenchido");

		this.empresa = empresa;
		this.quantidadeAssentos = quantidadeAssentos;
		this.aceitaOverbooking = aceitaOverbooking;
		this.metaInformacoesAssento = metaInformacoesAssento;
		this.nome = nome;
	}

	public boolean pertenceAEmpresa(@Valid @NotNull Empresa empresa) {
		Assert.notNull(empresa, "Empresa não pode ser nula");
		return this.empresa.equals(empresa);
	}

	public boolean suportaMetaInformacao(String metaInformacao) {
		return this.metaInformacoesAssento.contains(metaInformacao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LayoutEvento other = (LayoutEvento) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public void adicionaAssentos(Function<LayoutEvento, Collection<Assento>> produtorDeNovoasAssentos) {
		Collection<Assento> novosAssentos = produtorDeNovoasAssentos.apply(this);
		//checar se os assentos de fato pertencem ao layout corrente
		
		/*
		 * aqui eu não chamo o método que busca repetido interno para não
		 * fazer um loop inteiro na colecao antes de adicionar, já que podemos
		 * ir adicionando e testando
		 */
		novosAssentos.forEach(novoAssento -> {
			Assert.state(this.assentos.add(novoAssento),"O assento em questão já foi cadastrado. Mais informações => "+novoAssento);
		});
	}

	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @param assentosASeremAdicionados colecao de assentos que querem ser adicionados
	 * @return
	 */
	public Collection<Assento> buscaAssentosExistentes(
			Collection<Assento> assentosASeremAdicionados) {
		//preciso copiar pq eu não quero adicionar nada na colecao da referencia.
		//ai fiz uma copia local e garanto nao alterar nada
		HashSet<Assento> copiaDosAssentosExistes = new HashSet<>(this.assentos);
		
		return assentosASeremAdicionados.stream().filter(novoAssento -> {
			return !copiaDosAssentosExistes.add(novoAssento);
		}).toList();		
	}

}
