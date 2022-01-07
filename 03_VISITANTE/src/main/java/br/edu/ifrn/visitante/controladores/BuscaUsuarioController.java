package br.edu.ifrn.visitante.controladores;
/** 
 * 
 * Objetivo: esta classe tem o objetivo de buscar os visitantes cadastrados no sistema.
 * 
 * Autor: Tharciana Vitória (tharciana.vitoria@gmail.com)
 * 
 * Data de criação: 17/01/2022
 * 
*/

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.visitante.dominio.Usuario;
import br.edu.ifrn.visitante.repository.UsuarioRepository;

@Controller 
@RequestMapping("/usuarios")
public class BuscaUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * 
	 * @return quando a url /busca for acionada, o sistema irá exibir a página de 
	 * busca de visitantes.
	 */
	@GetMapping("/busca")
	public String entrarBusca() {
		return "usuario/busca";
	}

	/**
	 * 
	 * @param nome  parâmetro da requisição de acesso.
	 * @param email parâmetro da requisição de acesso.
	 * @param mostrarTodosDados onde o funcionário clicará para ser exibido todos os dados dos
	 * visitantes.
	 * @param sessao onde as informações serão salvas, isto é, na sessão do servidor.
	 * @param model para enviar uma informação.
	 * @return retornará a essa página após a ação bem sucedida.
	 */
	@Transactional(readOnly = true)
	@GetMapping("/buscar")
	public String buscar(
				@RequestParam(name = "nome", required = false) String nome,
				@RequestParam(name = "email", required = false) String email,
				@RequestParam(name = "mostrarTodosDados", required = false) 
				Boolean mostrarTodosDados, 
				HttpSession sessao,
				ModelMap model
			) {
		
		List<Usuario> usuariosEncontrados = 
				usuarioRepository.findByEmailAndNome(email,nome);
		
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);
		
		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}
		
		return "usuario/busca";
	}
	
	/**
	 * 
	 * @param idUsuario o usuário será removido pelo id.
	 * @param sessao onde as informações serão salvas, isto é, na sessão do servidor.
	 * @param attr serve para exibir uma mensagem de retorno após alguma ação.
	 * @return retornará a essa página após a ação bem sucedida.
	 */
	@Transactional(readOnly = false)
	@GetMapping("/remover/{id}")
	public String remover (
			@PathVariable("id") Integer idUsuario,
			HttpSession sessao,
			RedirectAttributes attr
			) {
		
		try {
			usuarioRepository.deleteById(idUsuario);
			attr.addFlashAttribute("msgSucesso" , "Usuário removido com sucesso!");
			return "redirect:/usuarios/buscar";
		} catch (DataIntegrityViolationException e) {
			attr.addFlashAttribute("msgErro" , "Não é possível remover o visitante selecionado, pois ele possui visitas cadastradas.");
			return "redirect:/usuarios/buscar";
		}
		
	}
}
