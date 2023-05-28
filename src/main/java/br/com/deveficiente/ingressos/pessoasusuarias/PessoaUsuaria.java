package br.com.deveficiente.ingressos.pessoasusuarias;

import org.hibernate.validator.constraints.br.CPF;

import br.com.deveficiente.ingressos.empresas.Empresa;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class PessoaUsuaria {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@ManyToOne
	private Empresa empresa;
	private @NotBlank String nome;
	private @NotBlank @Email String email;
	private @NotBlank @CPF String cpf;
	private String senha;
	
	@Deprecated
	public PessoaUsuaria() {
		// TODO Auto-generated constructor stub
	}

	public PessoaUsuaria(@NotNull @Valid Empresa empresa, @NotBlank String nome,
			@NotBlank @Email String email, @NotBlank @CPF String cpf,@NotNull @Valid SenhaLimpa senha) {
				this.empresa = empresa;
				this.nome = nome;
				this.email = email;
				this.cpf = cpf;
				this.senha = senha.encodaBcrypt();
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
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
		PessoaUsuaria other = (PessoaUsuaria) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		return true;
	}
	
	


	

}
