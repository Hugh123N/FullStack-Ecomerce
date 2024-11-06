package com.curso.ecomerce.controler;

import com.curso.ecomerce.model.DetalleOrden;
import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.IDetalleOrdenService;
import com.curso.ecomerce.service.IOrdenService;
import com.curso.ecomerce.service.IUsuarioService;
import com.curso.ecomerce.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@Controller
@RequestMapping("/")
public class HomeControler {
    private final Logger log= LoggerFactory.getLogger(HomeControler.class);
    @Autowired
    private ProductoService productoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IOrdenService ordenService;
    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    //para almacenar los detalles de la orden
    List<DetalleOrden> detalles=new ArrayList<DetalleOrden>();
    //almacena datos de la orden en general
    Orden orden=new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session){

        log.info("sesion del usuario: {}",session.getAttribute("idUsuario"));
        List<Producto> productos=productoService.findAll();
        model.addAttribute("productos",productos);
        //session
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id,Model model, HttpSession session){

        log.info("id enviado como parametro {}",id);
        Producto producto=new Producto();
        Optional<Producto> productoOptional=productoService.get(id);
        producto=productoOptional.get();
        model.addAttribute("producto",producto);
        //session
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id,@RequestParam Integer cantidad, Model model, HttpSession session){
        DetalleOrden detalleOrden=new DetalleOrden();
        Producto producto=new Producto();
        double sumaTotal=0;
        Optional<Producto> optionalProducto=productoService.get(id);
        log.info("producto añadido {}",optionalProducto.get());
        log.info("cantidad {}",cantidad);
        producto=optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);
        //validar que el producto nose añada dos veces
        int idProducto= producto.getId();
        //recorre por toda el array detalles como el for
        boolean ingresado=detalles.stream().anyMatch(p ->p.getProducto().getId()==idProducto);
        if(!ingresado){
            //agregamos al array detalle todos los datos
            detalles.add(detalleOrden);
        }

        //recorre la tabla detalle y suma todos los totales
        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        //session
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/carrito";
    }

    //quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Integer id, Model model){

        List<DetalleOrden> ordenesNueva=new ArrayList<DetalleOrden>();
        for(DetalleOrden detalleOrden: detalles){
            if(detalleOrden.getProducto().getId()!=id){
                ordenesNueva.add(detalleOrden);
            }
            //poner la nueva lista con los productos restante
            detalles=ordenesNueva;

            double sumaTotal=0;
            sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
            orden.setTotal(sumaTotal);
            model.addAttribute("cart",detalles);
            model.addAttribute("orden",orden);
        }
        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        //sesion
        model.addAttribute("sesion",session.getAttribute("idUsuario"));

        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session){
        try {
            int idUsuario=Integer.parseInt(session.getAttribute("idUsuario").toString());
            if(idUsuario>0){
                Usuario usuario=usuarioService.findById(idUsuario).get();

                model.addAttribute("usuario",usuario);
                model.addAttribute("cart",detalles);
                model.addAttribute("orden",orden);
                return "usuario/resumenorden";
            }

        }catch (Exception e){
            e.fillInStackTrace();
        }
        return "usuario/login";
    }
    //GUARDAR   ORDENESSSS
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){
        Date fechaCreacion=new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());
        //usuario
        int idUsuario=Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario=usuarioService.findById(idUsuario).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);
        //guardar detalles
        for(DetalleOrden dt:detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
        //limpiar el carrito o la lista
        orden=new Orden();
        detalles.clear();
        return "redirect:/";
    }
    //busqueda de producto desde el navegador
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre,Model model){
        log.info("nombre del producto buscado {}",nombre);
        //filtramos para que busque en la lista todos los nombre que contengan el nombre que ingresamos
        List<Producto> productos=productoService.findAll().stream().filter(p ->p.getNombre().contains(nombre)).collect(Collectors.toList());

        model.addAttribute("productos",productos);

        return "usuario/home";
    }


}
