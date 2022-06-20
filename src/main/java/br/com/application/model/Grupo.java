package br.com.application.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@SuppressWarnings("serial")
@Entity @Table(name="grupos")
public class Grupo implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_grupo") private Long id;
	
	@Column(name="ds_nome")
	private String nome;
	
	@Column(name="ds_descricao")
	private String descricao;
	
	@Column(name="checked")
	private Boolean checked;

	@ManyToMany
	@JoinTable(name="usuario_x_grupo",
			joinColumns=@JoinColumn(name="id_grupo", referencedColumnName="id_grupo"),
			inverseJoinColumns=@JoinColumn(name="id_usuario", referencedColumnName="id_usuario"))
	private List<Usuario> usuarios;
	
	@ManyToMany
	@JoinTable(name="permissao_x_grupo", 
	        joinColumns=@JoinColumn(name="id_grupo", referencedColumnName="id_grupo"),
			inverseJoinColumns=@JoinColumn(name="id_permissao", referencedColumnName="id_permissao"))
	private List<Permissao> permissaos;
	
	public Grupo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



}
