package br.edu.ifrn.visitante.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifrn.visitante.dominio.Usuario;

@Controller
public class TelaInicioController {
	
	@GetMapping("/")
	public String Telainicio(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "funcoes";
	}
	
	@GetMapping("/login")
	public String login() {
		return "telainicio";
	}
	
	//Login inválido
	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgErro", "Usuário ou senha incorreta. Tente novamente.");
		return "telainicio";
	}
}
