package br.edu.ifrn.visitante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.visitante.dominio.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
