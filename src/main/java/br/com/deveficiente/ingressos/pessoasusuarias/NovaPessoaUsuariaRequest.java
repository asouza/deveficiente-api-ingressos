package br.com.deveficiente.ingressos.pessoasusuarias;

import org.hibernate.validator.constraints.br.CPF;

import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NovaPessoaUsuariaRequest {

	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@CPF
	private String cpf;
	@NotBlank
	@Size(min = 6)
	private String senha;

	public NovaPessoaUsuariaRequest(@NotBlank String nome,
			@NotBlank @Email String email, @NotBlank @CPF String cpf,@NotBlank @Size(min = 6) String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.senha = senha;
	}
	
	public PessoaUsuaria toModel(Empresa empresa) {
		return new PessoaUsuaria(empresa,nome,email,cpf,new SenhaLimpa(senha));
	}

}
