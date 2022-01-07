package br.edu.ifrn.visitante.controladores;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ifrn.visitante.dominio.Arquivo;
import br.edu.ifrn.visitante.repository.ArquivoRepository;

@Controller
public class DownloadArquivoController {
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile (@PathVariable("id") Long idArquivo,
											@PathParam("salvar") String salvar) {
		
		
		Arquivo arquivoBD = arquivoRepository.findById(idArquivo).get();
		
		String texto = (salvar == null || salvar.equals("true")) ?
				"attachment; filename=\"" + arquivoBD.getNomeArquivo() + "\"" :
				"inline; filename =\"" + arquivoBD.getNomeArquivo() + "\"";
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(arquivoBD.getTipoArquivo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, texto)
				.body(new ByteArrayResource(arquivoBD.getDados()));
		
	}

}
