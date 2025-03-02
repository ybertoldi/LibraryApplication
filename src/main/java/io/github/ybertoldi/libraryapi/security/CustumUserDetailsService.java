package io.github.ybertoldi.libraryapi.security;

import io.github.ybertoldi.libraryapi.model.Usuario;
import io.github.ybertoldi.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class CustumUserDetailsService implements UserDetailsService {
    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(login);
        if (usuario == null){
            throw new UsernameNotFoundException("Usuario nao encontrado");
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
