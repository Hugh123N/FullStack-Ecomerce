package com.curso.ecomerce.controler;

import com.curso.ecomerce.model.Producto;
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
@RequestMapping("/")
public class HomeControler {
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String home(Model model){
        List<Producto> productos=productoService.findAll();
        model.addAttribute("productos",productos);

        return "usuario/home";
    }


}
