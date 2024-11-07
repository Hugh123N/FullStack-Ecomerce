package com.curso.ecomerce.service;

import com.curso.ecomerce.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired //inyecta a esta clase
    private IUsuarioService usuarioService;
    @Autowired
    HttpSession session;
    private Logger log= LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("esto es el username");
        Optional<Usuario> optionalUser=usuarioService.findByEmail(username);
        if(optionalUser.isPresent()){
            session.setAttribute("idUsuario", optionalUser.get().getId());
            Usuario usuario=optionalUser.get();
            return new SecurityUser(usuario);
        }else
            throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
