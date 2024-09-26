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
    public String addCart(@RequestParam Integer id,@RequestParam Integer cantidad, Model model){
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
        //agregamos al array detalle todos los datos
        detalles.add(detalleOrden);
        //recorre la tabla detalle y suma todos los totales
        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
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

}
