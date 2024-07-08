package com.proyecto.autolavado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    /**
     * Metodo que muestra la pagina Inicial de la aplicacion
     * @return
     */
    @GetMapping({"/index","/home","/"})
    public String index(Model model) {
        List<String> imageFilenames = List.of("2.jpg", "3.jpg"); // Agrega todos los nombres de archivos que tienes
        model.addAttribute("images", imageFilenames);
        return ("home");
    }

}
