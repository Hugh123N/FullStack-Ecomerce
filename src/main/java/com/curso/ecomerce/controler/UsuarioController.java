package com.curso.ecomerce.controler;

import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger log= LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;


    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        log.info("usuarios enviados  {}", usuario);
        usuario.setUsername("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }
    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){
        log.info("accesos : {}",usuario);
        Optional<Usuario> user=usuarioService.findByEmail(usuario.getEmail());
        //si esta presente ese usuario entonces añadimos
        if(user.isPresent()){
            session.setAttribute("idUsuario",user.get().getId());
            if(user.get().getTipo().equals("admin")){
                return "redirect:/administrador";
            }else
                return "redirect:/";
        }else
            log.info("Usuario no existe");
        return "redirect:/";
    }
}
