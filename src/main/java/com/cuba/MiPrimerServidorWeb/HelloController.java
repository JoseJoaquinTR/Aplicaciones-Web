package com.cuba.MiPrimerServidorWeb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")//https://Mipagina.com
public class HelloController {

    @GetMapping("hi")
    // Get https://Mipagina.com/api/hi
    public String hi(){
        return "Hola desde mi primer servidor ";
    }
    @GetMapping("/saludo")
    // Get https://Mipagina.com/api/hi
    public String saludo(@RequestParam String nombre){
        return "Saludos desde el servidor de: " + nombre ;
    }
}
