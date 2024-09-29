package com.curso.ecomerce.controler;

import com.curso.ecomerce.model.DetalleOrden;
import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.IOrdenService;
import com.curso.ecomerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger log= LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IOrdenService ordenService;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        log.info("usuarios enviados  {}", usuario);
        usuario.setTipo("USER");
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
            if(user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else
                return "redirect:/";
        }else
            log.info("Usuario no existe");
        return "redirect:/";
    }

    //COMPRAS
    @GetMapping("/compras")
    public String obtener(Model model, HttpSession session){
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        int idUsuario=Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario=usuarioService.findById(idUsuario).get();
        List<Orden> ordenes=ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes",ordenes);
        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id,HttpSession session, Model model){
        log.info("ID de la orden {}",id);
        //session
        model.addAttribute("sesion",session.getAttribute("idUsuario"));

        Optional<Orden> orden=ordenService.findById(id);
        model.addAttribute("detalles",orden.get().getDetalle());
        log.info("detalles listados {}",orden.get().getDetalle());
        return "usuario/detallecompra";
    }
    //cierre de sesion
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){
        session.removeAttribute("idUsuario");
        return "redirect:/";
    }
}
