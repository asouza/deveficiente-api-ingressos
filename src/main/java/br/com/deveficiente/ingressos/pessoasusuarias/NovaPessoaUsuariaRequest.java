package br.com.deveficiente.ingressos.pessoasusuarias;

import org.hibernate.validator.constraints.br.CPF;

import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NovaPessoaUsuariaRequest {

	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@CPF
	private String cpf;

	public NovaPessoaUsuariaRequest(@NotBlank String nome,
			@NotBlank @Email String email, @NotBlank @CPF String cpf) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
	}
	
	public PessoaUsuaria toModel(Empresa empresa) {
		return new PessoaUsuaria(empresa,nome,email,cpf);
	}

}
