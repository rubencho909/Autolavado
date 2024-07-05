package com.proyecto.autolavado.controller;

import com.proyecto.autolavado.modelos.Servicio;
import com.proyecto.autolavado.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    ServicioService servicioService;

    @GetMapping("/lista")
    public String verServicios(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Servicio> listaServicios = servicioService.getAllServicios();
        model.addAttribute("titulo", "Lista de Servicios");
        model.addAttribute("serlist", listaServicios);
        model.addAttribute("mensaje", mensaje);
        return "/servicio/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoServicio(Model model, @ModelAttribute("mensaje") String mensaje){
        Servicio ser = new Servicio();
        model.addAttribute("titulo", "Agregar Nuevo Servicio");
        model.addAttribute("ser", ser);
        model.addAttribute("mensaje", mensaje);
        return "/servicio/nuevo";
    }

    @PostMapping("/guardar")
    public String crearServicio(Servicio ser, RedirectAttributes redirectAttributes) {

        if (servicioService.saveOrUpdateServicio(ser) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/servicio/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/nuevo";
    }

    @GetMapping("/editar/{id}")
    public String editarServicio(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Servicio ser = servicioService.getServicioById(id);
        model.addAttribute("ser", ser);
        model.addAttribute("mensaje", mensaje);
        return "/servicio/editar";
    }

    @PostMapping("/editar")
    public String updateEmpresa(@ModelAttribute("ser") Servicio ser, RedirectAttributes redirectAttributes){
        if (servicioService.saveOrUpdateServicio(ser)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/servicio/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/servicio/editar" + ser.getId();
    }

    @GetMapping("/detalle/{id}")
    public String detalle(Model model, @PathVariable Integer id) throws Exception {
        Servicio servicio = null;

        if (id != null) {
            servicio = servicioService.getServicioById(id);
            model.addAttribute("titulo", "Formulario: Detalle Servicio");
        }
        model.addAttribute("servicio", servicio);
        return "/servicio/detalle";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarServicio(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (servicioService.deleteServicio(id) == true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/servicio/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/servicio/lista";
    }


}
