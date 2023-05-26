package br.com.deveficiente.ingressos.pessoasusuarias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaUsuariaRepository
		extends JpaRepository<PessoaUsuaria, Long>,PessoasUsuariasCadastradasNaEmpresa {

	public default boolean existeEmail(Long idEmpresa,String email) {
		return this.existsByEmpresaIdAndEmail(idEmpresa, email);
	}
	
	public default boolean existeCpf(Long idEmpresa,String cpf) {
		return this.existsByEmpresaIdAndCpf(idEmpresa, cpf);
	}
	
	public boolean existsByEmpresaIdAndEmail(Long idEmpresa,String email);
	
	public boolean existsByEmpresaIdAndCpf(Long idEmpresa,String cpf);
}
