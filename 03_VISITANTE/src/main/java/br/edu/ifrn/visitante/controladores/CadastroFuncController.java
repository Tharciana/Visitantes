package br.edu.ifrn.visitante.controladores;
/** 
 * 
 * Objetivo: esta classe tem o objetivo de cadastrar os funcionários, que são os usuários
 * do sistema.
 * 
 * Autor: Tharciana Vitória (tharciana.vitoria@gmail.com)
 * 
 * Data de criação: 26/01/2022
 * 
*/
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.visitante.dominio.Arquivo;
import br.edu.ifrn.visitante.dominio.Funcionario;
import br.edu.ifrn.visitante.repository.ArquivoRepository;
import br.edu.ifrn.visitante.repository.FuncionarioRepository;


@Controller
@RequestMapping("/funcionarios")
public class CadastroFuncController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	/**
	 * 
	 * @param model para enviar uma informação.
	 * @return vai para a página de cadastro de funcionários.
	 */
	@GetMapping("/cadastroFunc")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("funcionario", new Funcionario());
		return "funcionario/cadastroFunc";
	}
	
	/**
	 * 
	 * @param funcionario o método precisa receber algum funcionário e esse objeto é o que 
	 * vai armazenar todas as informações que o funcionário digitar. 
	 * @param result usado para caso haja algum erro, o sistema irá retornar para a 
	 * página de casdatro de funcionários e não para outra.
	 * @param map enviar uma informação.
	 * @param sessao onde as informações serão salvas, isto é, na sessão do servidor.
	 * @return caso a execução seja um sucesso, o sistema retornará a página inicial, 
	 * que é a de login.
	 */
	@Transactional(readOnly = false)
	@PostMapping ("/salvarFunc")
	public String salvar(@Valid Funcionario funcionario, BindingResult result,  ModelMap map, HttpSession sessao) {
		
		if (result.hasErrors()) {
			  return "funcionario/cadastroFunc";
			}
			
			//Criptografando Senha
			String senhaCriptografada = new BCryptPasswordEncoder().encode(funcionario.getSenha());
			funcionario.setSenha(senhaCriptografada);
			
			//Serve p/ cadastro e edicao
			funcionarioRepository.save(funcionario);
			map.addAttribute("msgSucesso", "Operação realizada com sucesso!");
		
		
		return "/telainicio";

	}
}