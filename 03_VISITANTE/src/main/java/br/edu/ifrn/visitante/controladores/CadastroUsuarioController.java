package br.edu.ifrn.visitante.controladores;
/** 
 * 
 * Objetivo: esta classe tem o objetivo de cadastrar os visitantes no sistema.
 * 
 * Autor: Tharciana Vitória (tharciana.vitoria@gmail.com)
 * 
 * Data de criação: 17/01/2022
 * 
*/


import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.visitante.dominio.Arquivo;
import br.edu.ifrn.visitante.dominio.Usuario;
import br.edu.ifrn.visitante.repository.ArquivoRepository;
import br.edu.ifrn.visitante.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class CadastroUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ArquivoRepository arquivoRepository;

	/**
	 * 
	 * @param model para enviar uma informação.
	 * @return retornará a página de cadastro de visitantes quando a url for acionada.
	 */
	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastro";
	}
	
	/**
	 * 
	 * @param usuario método precisa receber algum usuario e esse objeto é o que 
	 * vai armazenar todas as informações que o funcionário digitar na hora de 
	 * cadastrar o visitante.
	 * @param result usado para caso haja algum erro, o sistema irá retornar para a 
	 * página de casdatro de funcionários e não para outra.
	 * @param attr serve para exibir uma mensagem de retorno após alguma ação.
	 * @param sessao onde as informações serão salvas, isto é, na sessão do servidor.
	 * @param arquivo Arquivo que será recebido.
	 * @return retornará a página de cadastro de visitantes quando a url for acionada.
	 */
	@Transactional(readOnly = false)
	@PostMapping ("/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result,  RedirectAttributes attr, HttpSession sessao, 
			@RequestParam("file") MultipartFile arquivo) {
		
		try { 
			if (arquivo != null && !arquivo.isEmpty()) {
				//Normalizando o nome do arquivo
				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
				Arquivo arquivoBD = new Arquivo(nomeArquivo, arquivo.getContentType(), arquivo.getBytes());
				arquivoRepository.save(arquivoBD); //salva a nova foto no banco
				
				// Se já tinha uma foto salva, remove-a 
				if (usuario.getFoto() != null && usuario.getFoto().getId() != null &&
						usuario.getFoto().getId() > 0)
					arquivoRepository.delete(usuario.getFoto());
				
				usuario.setFoto(arquivoBD); //Salva a nova foto do usuário
			} else {
				usuario.setFoto(null);
			}
		
		if (result.hasErrors()) {
			  return "usuario/cadastro";
			}
		
		//Serve p/ cadastro e edicao
		usuarioRepository.save(usuario);
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/usuarios/cadastro";
}
	
	/**
	 * 
	 * @param idUsuario o usuário será editado pelo id
	 * @param model para enviar uma informação.
	 * @param sessao  onde as informações serão salvas, isto é, na sessão do servidor.
	 * @return retornará a página de cadastro de visitantes quando a url for acionada.
	 */
	@GetMapping("/editar/{id}") 
	public String iniciarEdicao(
			@PathVariable("id")  Integer idUsuario, 
			ModelMap model,
			HttpSession sessao
			) {
		
		Usuario u = usuarioRepository.findById(idUsuario).get();
		
		model.addAttribute("usuario", u);
		
		return "/usuario/cadastro";
	}
	
	
	
	
	
	
	
}
