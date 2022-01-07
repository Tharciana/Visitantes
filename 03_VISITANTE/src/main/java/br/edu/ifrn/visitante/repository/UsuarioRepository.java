package br.edu.ifrn.visitante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.visitante.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query("select u from Usuario u where u.email like %:email% " + 
			"and u.nome like %:nome% ")
	List<Usuario> findByEmailAndNome(@Param("email") String email,
									@Param("nome") String nome);
	
	@Query("select u from Usuario u where u.nome like %:nome% ")
	List<Usuario> findByNome(@Param("nome") String nome);

	
}
