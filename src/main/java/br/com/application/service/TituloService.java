package br.com.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.application.enumerator.StatusTitulo;
import br.com.application.model.Titulo;
import br.com.application.repository.TituloRepository;
import br.com.application.repository.filter.TituloFilter;


@Service
public class TituloService {
	
	@Autowired private TituloRepository tituloRepository;
	
	public void salvar(Titulo titulo) {
		try {
			tituloRepository.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public void remove(Long codigo) {
		Titulo titulo = tituloRepository.getOne(codigo);
		tituloRepository.delete(titulo);
	}
	
	public void remove(Titulo titulo) {
		tituloRepository.delete(titulo);
	}
	
	public void excluir(Long codigo) {
		tituloRepository.deleteById(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = tituloRepository.getOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tituloRepository.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return tituloRepository.findByDescricaoContaining(descricao);
	}

}
