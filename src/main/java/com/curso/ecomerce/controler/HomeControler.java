package com.curso.ecomerce.controler;

import com.curso.ecomerce.model.DetalleOrden;
import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.service.ProductoService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Controller
@RequestMapping("/")
public class HomeControler {
    private final Logger log= LoggerFactory.getLogger(HomeControler.class);
    @Autowired
    private ProductoService productoService;

    //para almacenar los detalles de la orden
    List<DetalleOrden> detalles=new ArrayList<DetalleOrden>();
    //almacena datos de la orden en general
    Orden orden=new Orden();

    @GetMapping("")
    public String home(Model model){
        List<Producto> productos=productoService.findAll();
        model.addAttribute("productos",productos);
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id,Model model){
        log.info("id enviado como parametro {}",id);
        Producto producto=new Producto();
        Optional<Producto> productoOptional=productoService.get(id);
        producto=productoOptional.get();
        model.addAttribute("producto",producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id,@RequestParam Integer cantidad){
        DetalleOrden detalleOrden=new DetalleOrden();
        Producto producto=new Producto();
        double sumaTotal=0;

        Optional<Producto> optionalProducto=productoService.get(id);
        log.info("producto añadido {}",optionalProducto.get());
        log.info("cantidad {}",cantidad);

        return "usuario/carrito";
    }

}
