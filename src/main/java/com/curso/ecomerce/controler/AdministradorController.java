package com.curso.ecomerce.controler;


import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.IOrdenService;
import com.curso.ecomerce.service.IUsuarioService;
import com.curso.ecomerce.service.ProductoService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@NoArgsConstructor
@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IOrdenService ordenService;

    @GetMapping("")
    public String home(Model model){//para mandar los productos hacia la vista home
        List<Producto> producto = productoService.findAll();
        model.addAttribute("productos",producto);

        return "administrador/home";
    }
    //para ver lista de usuarios
    @GetMapping("/usuarios")
    public String usuario(Model model){
        List<Usuario> usuarios=usuarioService.findAll();
        model.addAttribute("usuarios",usuarios);
        return "administrador/usuarios";
    }
    //lista de ordenes

    @GetMapping("/ordenes")
    public String ordenes( Model model){
        model.addAttribute("ordenes",ordenService.findAll());
        return "administrador/ordenes";
    }
}
