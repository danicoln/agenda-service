package br.com.microservice.agendaservice.domain.services;

import br.com.microservice.agendaservice.domain.entity.Usuario;
import br.com.microservice.agendaservice.domain.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Optional<Usuario> optUsuario = usuarioRepository.findByUsuario(usuario);
        if(optUsuario.isEmpty()){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        Usuario user = optUsuario.get();
        return new User(user.getUsuario(), user.getSenha(), new ArrayList<>());
    }

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario){
        //pega a senha que o usuario passou e criptografa pelo encode
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
}
