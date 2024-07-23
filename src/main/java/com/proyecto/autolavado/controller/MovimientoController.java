package com.proyecto.autolavado.controller;

import com.proyecto.autolavado.modelos.Empleado;
import com.proyecto.autolavado.modelos.MovimientoDinero;
import com.proyecto.autolavado.modelos.Servicio;
import com.proyecto.autolavado.service.EmpleadoService;
import com.proyecto.autolavado.service.MovimientosService;
import com.proyecto.autolavado.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/movimiento")
public class MovimientoController {

    @Autowired
    MovimientosService movimientosService;

    @Autowired
    ServicioService servicioService;

    @GetMapping("/lista")
    public String verMovimientos(Model model){
        List<MovimientoDinero> listaMovimientos = movimientosService.getAllMovimientos();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        for (MovimientoDinero movimiento : listaMovimientos) {
            String montoString = currencyFormat.format(movimiento.getMonto());
            movimiento.setMontoFormat(montoString);
        }

        model.addAttribute("titulo", "Lista de Movimientos");
        model.addAttribute("movlist", listaMovimientos);

        //Long sumaMonto = movimientosService.obtenerSumaMontos();
        //String sumaMontoFormat = currencyFormat.format(sumaMonto);
        //model.addAttribute("sumaMonto", sumaMontoFormat);

        return "/movimiento/lista";
    }

    /**
     * Metodo que muestra el formulario para crear un nuevo movimiento
     * @param model
     * @return
     */
    @GetMapping("/nuevo")
    public String nuevoEMovimiento(Model model){
        MovimientoDinero mov = new MovimientoDinero();
        model.addAttribute("titulo", "Agregar Nuevo Movimiento");
        model.addAttribute("mov", mov);
        List<Servicio> listaServicios = servicioService.getAllServicios();
        model.addAttribute("serlist", listaServicios);

        return "/movimiento/nuevo";
    }

    /**
     * Metodo que guarda en BD un movimiento
     * @param mov
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/guardar")
    public String crearMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes) {
        if (!mov.getConcepto().equals("9")) {
            mov.setServicio(null);
        }
        if (movimientosService.saveOrUpdateMovimiento(mov) == true) {
            redirectAttributes.addFlashAttribute("success", "Movimiento creado con exito!");
            return "redirect:/movimiento/lista";
        }
        redirectAttributes.addFlashAttribute("error", "Error al crear un movimiento");
        return "redirect:/nuevo";
    }

    /**
     * Metodo que obtiene el movimiento por su ID para ser editado
     * @param model
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/editar/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, RedirectAttributes redirectAttributes){
        MovimientoDinero mov = movimientosService.getMovimientoById(id);
        model.addAttribute("titulo", "Editar Movimiento");
        if(mov == null) {
            redirectAttributes.addFlashAttribute("error", "El ID del movimiento no existe");
            return "redirect:/movimiento/lista";
        }

        model.addAttribute("mov", mov);
        List<Servicio> listaServicios = servicioService.getAllServicios();
        model.addAttribute("serlist", listaServicios);
        return "/movimiento/editar";
    }

    /**
     * Metodo que realiza la edicion del empleado consultado y registra los cambios en BD
     * @param mov
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/editar")
    public String updateMovimiento(@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if (!mov.getConcepto().equals("9")) {
            mov.setServicio(null);
        }

        if (movimientosService.saveOrUpdateMovimiento(mov)) {
            redirectAttributes.addFlashAttribute("success", "El movimiento ha sido actualizado con exito!");
            return "redirect:/movimiento/lista";
        }
        redirectAttributes.addFlashAttribute("error", "Error al actualizar un empleado");
        return "redirect:/movimiento/editar" + mov.getId();
    }

    /**
     * Metodo que realiza la eliminacion del movimiento por ID en BD
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/borrar/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (movimientosService.deleteMovimiento(id) == true){
            redirectAttributes.addFlashAttribute("success","Movimiento eliminado con exito!");
            return "redirect:/movimiento/lista";
        }
        redirectAttributes.addFlashAttribute("error", "Error al eliminar un movimiento");
        return "redirect:/movimiento/lista";
    }


}
