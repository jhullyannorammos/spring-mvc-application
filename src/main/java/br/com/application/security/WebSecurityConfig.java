package br.com.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.application.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired private UsuarioService usuarioService;

	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/seguranca/cadastro").access("hasRole('ADMIN') or hasRole('ROLE_CADASTRO_USUARIO')")
				.antMatchers("/seguranca/consulta").access("hasRole('ADMIN') or hasRole('CONSULTA_USUARIO')")
				.antMatchers("/seguranca/home").authenticated().anyRequest().authenticated().and().formLogin()
				.loginPage("/").defaultSuccessUrl("/seguranca/home", true).permitAll() 
				.and()
				.logout().logoutSuccessUrl("/").logoutUrl("/logout").permitAll();
		http.exceptionHandling().accessDeniedPage("/layout/acessoNegado");
		http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();
	}*/
	
	@Autowired public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());

	}
	
	
    
	
	

}
