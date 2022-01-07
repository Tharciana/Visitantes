package br.edu.ifrn.visitante.controladores;
/** 
 * 
 * Objetivo: esta classe tem o objetivo de buscar as visitas já cadastradas no sistema.
 * 
 * Autor: Tharciana Vitória (tharciana.vitoria@gmail.com)
 * 
 * Data de criação: 26/01/2022
 * 
*/
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.edu.ifrn.visitante.dominio.Visita;
import br.edu.ifrn.visitante.repository.VisitaRepository;

@Controller 
@RequestMapping("/visita")
public class BuscaVisitaController {

	@Autowired
	private VisitaRepository visitaRepository;
	
	/**
	 * 
	 * @return retornará a página de busca de visitas quando a url for acionada.
	 */
	@GetMapping("/busca")
	public String entrarBusca() {
		return "visitas/buscaVisita";
	}

	/**
	 * 
	 * @param data parâmetro da requisição de acesso.
	 * @param hora parâmetro da requisição de acesso.
	 * @param nomeVisitante parâmetro da requisição de acesso.
	 * @param sessao onde as informações serão salvas, isto é, na sessão do servidor.
	 * @param model  para enviar uma informação.
	 * @return retornará a essa página após a ação bem sucedida.
	 */
	@Transactional(readOnly = true)
	@GetMapping("/buscarV")
	public String buscar(
				@RequestParam(name = "data", required = false) String data,
				@RequestParam(name = "hora", required = false) String hora,
				@RequestParam(name = "visitante", required = false) String nomeVisitante, 
				HttpSession sessao,
				ModelMap model
			) {
		
		List<Visita> visitasEncontradas = 
				visitaRepository.findByDataHoraVisitante(data,hora,nomeVisitante);
		
		model.addAttribute("visitasEncontradas", visitasEncontradas);
		
		return "visitas/buscaVisita";
	}
	

	
}
