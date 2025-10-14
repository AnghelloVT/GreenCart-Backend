package com.GreenCart.GreenCart.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistasController {
    @GetMapping("/Principal")
    public String mostrarPrincipal(){
        return "Principal";
    }

    @GetMapping("/CarritoCompras")
    public String mostrarCarritoCompras(){
        return "CarritoCompras";
    }
    @GetMapping("/Productos")
    public String mostrarProductos(){
        return "productos";
    }
    @GetMapping("/Reclamos")
    public String mostrarReclamos(){
        return "Reclamos";
    }
    @GetMapping("/Registro")
    public String mostrarRegistro(){
        return "Registro";
    }
    @GetMapping("/Login")
    public String mostrarLogin(){
        return "Login";
    }
}
