package com.curso.ecomerce.controler;

//importa todo el paquete de logger con => *
import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.model.Usuario;
import com.curso.ecomerce.service.ProductoService;
import com.curso.ecomerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoControler {

    // logger es para hacer prueba del guardado correcto por consola con INFO
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoControler.class);
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UploadFileService upload;

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
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("este es el objeto producto {}",producto);
        Usuario u=new Usuario(1,"","","","","","","",null,null);
        producto.setUsuario(u);

        //imagen
        if(producto.getId()==null){//cuando se crea un producto
            String imagen= upload.saveImage(file);
            producto.setImagen(imagen);
        } else {
            if(file.isEmpty()){//cuando editamos el producto pero no cambiamos la img
                Producto p=new Producto();
                p=productoService.get(producto.getId()).get();
                producto.setImagen(p.getImagen());
            }else{//cuando cambiamos e
                String imagen= upload.saveImage(file);
                producto.setImagen(imagen);
            }
        }

        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){//model lleva datos de back a front
        Producto product=new Producto();
        Optional<Producto> optionalProducto=productoService.get(id);
        product=optionalProducto.get();
        LOGGER.info("producto buscado: {} ",product);
        model.addAttribute("producto",product);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto){
        productoService.update(producto);

        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        productoService.delete(id);
        return "redirect:/productos";
    }

}
