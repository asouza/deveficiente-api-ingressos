package br.com.deveficiente.ingressos.empresas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;

	@Deprecated
	public Empresa() {
		// TODO Auto-generated constructor stub
	}
	
	public Empresa(@NotBlank String nome) {
		super();
		this.nome = nome;
	}

}
