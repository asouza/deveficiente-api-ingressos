package br.com.deveficiente.ingressos.pessoasusuarias;

public interface PessoasUsuariasCadastradasNaEmpresa {

	public boolean existeEmail(Long idEmpresa,String email);
	
	public boolean existeCpf(Long idEmpresa,String cpf);
}
