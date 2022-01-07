package br.edu.ifrn.visitante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.visitante.dominio.Usuario;
import br.edu.ifrn.visitante.dominio.Visita;

public interface VisitaRepository extends JpaRepository<Visita, Integer>{

	@Query("select v from Visita v where v.data like %:data% " + "and v.hora like %:hora% " +
			"and v.visitante.nome like %:visitante% ")
	List<Visita> findByDataHoraVisitante(@Param("data") String data,
									@Param("hora") String hora,@Param("visitante") String nomeVisitante);
	
}