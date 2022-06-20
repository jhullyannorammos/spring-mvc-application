package br.com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.application.model.Grupo;
import br.com.application.model.Usuario;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

	List<Grupo> findByUsuarios(Usuario usuario);
	
}
