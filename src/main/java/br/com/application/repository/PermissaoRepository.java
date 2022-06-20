package br.com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.application.model.Permissao;
import br.com.application.model.Grupo;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	List<Permissao> findByGrupos(Grupo grupo);
}
