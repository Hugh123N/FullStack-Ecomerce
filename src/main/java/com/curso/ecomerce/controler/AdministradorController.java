package com.curso.ecomerce.controler;


import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.IOrdenService;
import com.curso.ecomerce.service.IUsuarioService;
import com.curso.ecomerce.service.ProductoService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@NoArgsConstructor
@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    private Logger log= LoggerFactory.getLogger(AdministradorController.class);

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
    //lista detalle ordenes
    @GetMapping("/detalle/{id}")
    public String detalleOrden(@PathVariable Integer id,Model model){
       log.info("Id de la orden {}",id);
        Orden orden =ordenService.findById(id).get();

        model.addAttribute("detalles",orden.getDetalle());
        return "administrador/detalleorden";
    }
}
