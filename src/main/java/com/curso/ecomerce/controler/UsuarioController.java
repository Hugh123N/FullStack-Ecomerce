package com.curso.ecomerce.controler;

import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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

}
