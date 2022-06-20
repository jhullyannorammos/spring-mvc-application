package br.com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.application.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByLoginAndPassword(String login, String password);
	Usuario findByLogin(String login);
}
