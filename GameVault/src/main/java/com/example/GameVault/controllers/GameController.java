package com.example.GameVault.controllers;

import com.example.GameVault.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping("/fragments-demo")
    public String fragmentsDemo() {
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String listarJuegos(
            @RequestParam(name = "buscar", required = false) String buscar,
            Model model) {

        if (buscar != null && !buscar.isEmpty()) {
            model.addAttribute("juegos", juegoService.buscarPorTitulo(buscar));
            model.addAttribute("busquedaActual", buscar);
        } else {
            model.addAttribute("juegos", juegoService.ObtenerTodos());
        }

        return "juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario() {
        model.addAttribute("juego",new Juego());
        return "formulario";
    }

    @GetMapping("/mis-juegos")
    public String dashboard(Model model){
        Usuario logueado = usuarioService.obtenerUsuarioLogeado();

        model.addAtribute("juegos", juegoService.obtenerJuegosPorUsuario(logueado.getId));
        model.addAtribute("usuario", logueado);
        return "mis-juegos";
    }

    @PostMapping("/juegos")
    public String guardarJuego(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("portada") MultipartFile portada,
            @RequestParam("precio") double precio,
            @RequestParam("titulo") String titulo
    ) {
        nuevoJuego.setTitulo(titulo)

        juegoService.guardar(titulo, descripcion, portada);
        return "redirect:/mis-juegos";
    }
}
