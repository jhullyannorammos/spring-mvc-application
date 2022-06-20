package br.com.application.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.application.model.Usuario;
import br.com.application.model.Permissao;
import br.com.application.model.Grupo;
import br.com.application.repository.PermissaoRepository;
import br.com.application.repository.GrupoRepository;
import br.com.application.repository.UsuarioRepository;
import br.com.application.security.UsuarioSecurityModel;

@Component
public class UsuarioService  implements UserDetailsService {

	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private GrupoRepository grupoRepository; 
	@Autowired private PermissaoRepository permissaoRepository;
	private List<Grupo> grupos;
	private Grupo grupo;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws BadCredentialsException, DisabledException {
				
		Usuario usuario = usuarioRepository.findByLogin(login);
		
		if(usuario == null)
			throw new BadCredentialsException("Usuário não encontrado no sistema!");
		
		if(!usuario.isAtivo())
			throw new DisabledException("Usuário não está ativo no sistema!");
				
		return new UsuarioSecurityModel(usuario.getLogin(), 
				                        usuario.getPassword(), 
				                        usuario.isAtivo(),
				                        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.buscarPermissoesUsuario(usuario));
	}
	
	public List<GrantedAuthority> buscarPermissoesUsuario(Usuario usuario) {
		List<Grupo> grupos = grupoRepository.findByUsuarios(usuario);
		return this.buscarPermissoesDosGrupos(grupos);
	}
	
	public List<GrantedAuthority> buscarPermissoesDosGrupos(List<Grupo> grupos) {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		for (Grupo GrupoEditado: grupos) {
			List<Permissao> lista = permissaoRepository.findByGrupos(GrupoEditado);
			for (Permissao permissao: lista) {
				auths.add(new SimpleGrantedAuthority(permissao.getPermissao()));
			}
		}
		return auths;
	}
	
	public void persist(Usuario usuario){
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		List<Grupo> grupos =  new ArrayList<Grupo>();
		for (Grupo grupoEditado : usuario.getGrupos()){
			if(grupoEditado != null) {
				grupos.add(grupoEditado);
			}
		}
		usuario.setGrupos(grupos);
		this.usuarioRepository.save(usuario);
	}	
			
	public List<Usuario> findAll(){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = this.usuarioRepository.findAll();
		return usuarios;
	}
	
	public void remove(Usuario usuario){
		this.usuarioRepository.delete(usuario);
	}
	
	public void remove(Long codigo) {
		Usuario usuario = this.usuarioRepository.getOne(codigo);
		try {
			this.usuarioRepository.delete(usuario);
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public Usuario findBy(Long codigo){
		Usuario usuario = this.usuarioRepository.getOne(codigo);
        return usuario; 
	}
	
	public void update(Usuario usuario){
		
		Usuario usuarioEditado = this.usuarioRepository.getOne(usuario.getId());
		usuarioEditado.setUsername(usuario.getUsername());
		usuarioEditado.setPassword(usuario.getPassword());
		usuarioEditado.setLogin(usuario.getLogin());
		usuarioEditado.setAtivo(usuario.isAtivo());

		for (Grupo grupoEditado : usuarioEditado.getGrupos()){
			if(grupoEditado != null){
				grupo = grupoRepository.getOne(grupoEditado.getId());
				grupos.add(grupo);
			}
		}
		usuarioEditado.setGrupos(grupos);
		this.usuarioRepository.saveAndFlush(usuarioEditado);
	}
	
	
	
	

}
