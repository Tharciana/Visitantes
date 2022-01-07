package br.edu.ifrn.visitante.controladores;
/** 
 * 
 * Objetivo: esta classe tem o objetivo de cadastrar as visitas no sistema.
 * 
 * Autor: Tharciana Vitória (tharciana.vitoria@gmail.com)
 * 
 * Data de criação: 17/01/2022
 * 
*/
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.visitante.dominio.Usuario;
import br.edu.ifrn.visitante.dominio.Visita;
import br.edu.ifrn.visitante.dto.AutocompleteDTO;
import br.edu.ifrn.visitante.repository.UsuarioRepository;
import br.edu.ifrn.visitante.repository.VisitaRepository;

@Controller
@RequestMapping("/visita")
public class CadastroVisitaController {
	
	@Autowired
	private VisitaRepository visitaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * 
	 * @param model para enviar uma informação.
	 * @return retornará a página de cadastro de visita quando a url for acionada.
	 */
	@GetMapping("/cadastroV")
	public String entrarCadastroV(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("visita", new Visita());
		return "visitas/cadastroVisita";
	}
	
	/**
	 * 
	 * @param visita o método precisará de uma visita já com as informações armazenadas para 
	 * executar a ação.
	 * @param result usado para caso haja algum erro, o sistema irá retornar para a 
	 * página de casdatro de funcionários e não para outra.
	 * @param attr serve para exibir uma mensagem de retorno após alguma ação.
	 * @param sessao  onde as informações serão salvas, isto é, na sessão do servidor.
	 * @return retornará a página de cadastro de visita quando a url for acionada.
	 */
	@Transactional(readOnly = false)
	@PostMapping ("/salvarV")
	public String salvar(@Valid Visita visita, BindingResult result,  RedirectAttributes attr, HttpSession sessao) {
		
		if (result.hasErrors()) {
			  return "visitas/cadastroVisita";
			}
		
		//Serve p/ cadastro e edicao
		visitaRepository.save(visita);
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");
		
		return "redirect:/visita/cadastroV";
	}
	/**
	 * 
	 * @param idVisita a visita será editada pelo id.
	 * @param model para enviar uma informação.
	 * @param sessao onde as informações serão salvas, isto é, na sessão do servidor.
	 * @return  retornará a página de cadastro de visita após a ação.
	 */
	@GetMapping("/editarV/{id}") 
	public String iniciarEdicao(
			@PathVariable("id")  Integer idVisita, 
			ModelMap model,
			HttpSession sessao
			) {
		
		Visita v = visitaRepository.findById(idVisita).get();
		
		model.addAttribute("visita", v);
		
		return "/visitas/cadastroVisita";
	}
	/**
	 * 
	 * @param termo parâmetro da requisição de acesso.
	 * @return  retornará os resultados, no caso, o nome das visitas cadastradas de acordo
	 * com o que o está sendo digitado.
	 */
	@GetMapping("/autocompleteVisitantes")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> autocompleteVisitantes(
					@RequestParam("term") String termo) {
		
			List<Usuario> visitantes = usuarioRepository.findByNome(termo);
			
			List<AutocompleteDTO> resultados = new ArrayList<>();
			
			visitantes.forEach(u ->  resultados.add(
					new AutocompleteDTO(u.getNome(), u.getId()) 
					));
			
			return resultados;
			
	}
	
	
	

}
