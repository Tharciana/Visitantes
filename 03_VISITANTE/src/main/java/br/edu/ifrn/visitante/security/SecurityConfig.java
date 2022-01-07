package br.edu.ifrn.visitante.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.visitante.dominio.Funcionario;
import br.edu.ifrn.visitante.service.UsuarioService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService service;
	
	/** Sobreescrevendo as configurações padrão do Spring Security */
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/css/**", "/imagens/**", "/js/**").permitAll() // permitindo o acesso as URL's  informadas 
			.antMatchers("/telainicio", "/funcionarios/cadastroFunc", "/funcionarios/salvarFunc").permitAll()
			
			.antMatchers().hasAuthority(Funcionario.ADMIN)
			
			.anyRequest().authenticated() // para acessar as demais URL's, só estando autenticado
			.and() // adc regras de outro tipo 
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-error")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
			.and()
				.rememberMe();
	}
	
	protected void  configure(AuthenticationManagerBuilder auth) throws Exception {
		//Especificando  o tipo de criptografia  utilidzada na senha
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
		
	}

}
