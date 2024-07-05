package com.proyecto.autolavado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Metodo que muestra la pagina Inicial de la aplicacion
     * @return
     */
    @GetMapping({"/index","/home","/"})
    public String index() {
        return ("home");
    }

}
