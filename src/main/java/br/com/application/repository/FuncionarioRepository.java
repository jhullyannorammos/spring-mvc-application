package br.com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import br.com.application.model.Funcionario;

import javax.persistence.Tuple;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("select f.id as id, f.nome as nome " +
            "from Funcionario f " +
            "where f.nome like %:nome% " +
            "AND f.dataDemissao is not null " +
            "AND f.nivel.id = 1")
    List<Tuple> findFuncionariosByNome(String nome);
    
    @Procedure(procedureName = "PROCEDURE_BUSCAR_PELO_NOME")
    public String findByName(@Param("P_IN_ID") Long id);
}
