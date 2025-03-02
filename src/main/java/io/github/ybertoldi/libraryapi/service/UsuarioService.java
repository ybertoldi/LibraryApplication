package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.model.Usuario;
import io.github.ybertoldi.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario){
        String senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }


}
