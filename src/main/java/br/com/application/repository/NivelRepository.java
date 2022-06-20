package br.com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.application.model.Nivel;

import java.util.List;

public interface NivelRepository extends JpaRepository<Nivel, Long> {

    @Query("select n.nivel from Nivel n")
    List<String> findNiveis();
}
