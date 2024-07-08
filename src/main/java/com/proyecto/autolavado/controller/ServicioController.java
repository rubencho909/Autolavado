package com.proyecto.autolavado.controller;

import com.proyecto.autolavado.modelos.Servicio;
import com.proyecto.autolavado.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    ServicioService servicioService;

    /**
     * Metodo que realiza la consulta de los servicios creados en BD
     * @param model
     * @param mensaje
     * @return
     */
    @GetMapping("/lista")
    public String verServicios(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Servicio> listaServicios = servicioService.getAllServicios();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        for (Servicio servicio : listaServicios) {
            String valorString = currencyFormat.format(servicio.getValor());
            servicio.setValorFormat(valorString);
        }

        model.addAttribute("titulo", "Lista de Servicios");
        model.addAttribute("serlist", listaServicios);
        model.addAttribute("mensaje", mensaje);
        return "/servicio/lista";
    }

    /**
     * Metodo que muestra el formulario para crear un nuevo servicio
     * @param model
     * @param mensaje
     * @return
     */
    @GetMapping("/nuevo")
    public String nuevoServicio(Model model, @ModelAttribute("mensaje") String mensaje){
        Servicio ser = new Servicio();
        model.addAttribute("titulo", "Agregar Nuevo Servicio");
        model.addAttribute("ser", ser);
        model.addAttribute("mensaje", mensaje);
        return "/servicio/nuevo";
    }

    /**
     * Metodo que realiza el registro del nuevo servicio en BD
     * @param ser
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/guardar")
    public String crearServicio(Servicio ser, RedirectAttributes redirectAttributes) {

        if (servicioService.saveOrUpdateServicio(ser) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/servicio/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/nuevo";
    }

    /**
     * Metodo que obtiene el servicio por su ID para ser editado
     * @param model
     * @param id
     * @param mensaje
     * @return
     */
    @GetMapping("/editar/{id}")
    public String editarServicio(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Servicio ser = servicioService.getServicioById(id);
        model.addAttribute("ser", ser);
        model.addAttribute("mensaje", mensaje);
        return "/servicio/editar";
    }

    /**
     * Metodo que realiza la edicion del servicio consultado y registra los cambios en BD
     * @param ser
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/editar")
    public String updateServicio(@ModelAttribute("ser") Servicio ser, RedirectAttributes redirectAttributes){
        if (servicioService.saveOrUpdateServicio(ser)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/servicio/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/servicio/editar" + ser.getId();
    }

    /**
     * Metodo que realiza la consulta del servicio por ID y muestra su informacion
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/detalle/{id}")
    public String detalle(Model model, @PathVariable Integer id) throws Exception {
        Servicio servicio = null;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        if (id != null) {
            servicio = servicioService.getServicioById(id);
            String valorString = currencyFormat.format(servicio.getValor());
            servicio.setValorFormat(valorString);
            model.addAttribute("titulo", "Formulario: Detalle Servicio");
        }

        model.addAttribute("servicio", servicio);
        return "/servicio/detalle";
    }

    /**
     * Metodo que realiza la eliminacion del servicio por ID en BD
     * @param id
     * @param redirectAttributes
     * @return
     */
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
