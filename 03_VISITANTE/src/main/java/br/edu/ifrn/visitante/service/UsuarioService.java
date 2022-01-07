package br.edu.ifrn.visitante.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.visitante.dominio.Funcionario;
import br.edu.ifrn.visitante.repository.FuncionarioRepository;

/** 
 * Classe com métodos  de serviços referentes à entidade Funcionário
 * como esta classe implementa  UserDetailsService, é ela que será procurada
 * pelo Spring para carregar os dados do funcionário que esta tentando logar.
*/
@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private FuncionarioRepository repository;
	
	//Carregar os dados do usuário que está tentando logar
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Funcionario funcionario = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado."));
		
		//Objeto que será testado pelo Spring Security para conferir o login
		return new User (
			funcionario.getEmail(),
			funcionario.getSenha(),
			AuthorityUtils.createAuthorityList(funcionario.getPerfil())
		);
	}
}
	
	


