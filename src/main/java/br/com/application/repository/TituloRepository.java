package br.com.application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.application.model.Titulo;


public interface TituloRepository extends JpaRepository<Titulo, Long> {
	public List<Titulo> findByDescricaoContaining(String descricao);
}
