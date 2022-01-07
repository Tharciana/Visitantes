package br.edu.ifrn.visitante.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.visitante.dominio.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
	
	@Query("select f from Funcionario f where f.email like %:email% ")
	Optional<Funcionario> findByEmail(@Param("email") String email);

}
