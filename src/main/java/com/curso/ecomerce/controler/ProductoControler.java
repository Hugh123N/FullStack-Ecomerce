package com.curso.ecomerce.controler;

//importa todo el paquete de logger con => *
import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoControler {
    // logger es para hacer prueba del guardado correcto por consola con INFO
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoControler.class);
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model){ //model lleva datos de back asta la vista
        model.addAttribute("products",productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    //metodo que mapea la informacion que va guardar un producto
    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("este es el objeto producto {}",producto);
        Usuario u=new Usuario(1,"","","","","","","",null,null);
        producto.setUsuario(u);
        productoService.save(producto);
        return "redirect:/productos";
    }

}
