package br.com.application.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.application.model.Grupo;
import br.com.application.repository.GrupoRepository;

@Service
@Transactional
public class GrupoService {

	@Autowired private GrupoRepository grupoRepository;

	@Transactional(readOnly = true)
	public List<Grupo> consultarGrupos(){
		List<Grupo> grupos =  new ArrayList<Grupo>();
		grupos = this.grupoRepository.findAll();
		return grupos;
	}
	
}
