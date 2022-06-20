package br.com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.application.model.Endereco;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("select distinct e.uf from Endereco e order by e.uf asc")
    List<String> findEnderecoByUf();
}
