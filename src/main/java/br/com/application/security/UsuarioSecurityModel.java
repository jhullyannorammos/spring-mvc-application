package br.com.application.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class UsuarioSecurityModel extends User {
	
	public UsuarioSecurityModel(String username, 
			                    String password, 
			                    boolean enabled, 
			                    boolean accountNonExpired,
			                    boolean credentialsNonExpired, 
			                    boolean accountNonLocked,
			                    Collection<? extends GrantedAuthority> authorities) {
		                        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public UsuarioSecurityModel(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	

}
