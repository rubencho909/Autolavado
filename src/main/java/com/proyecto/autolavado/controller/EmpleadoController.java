package com.proyecto.autolavado.controller;

import com.proyecto.autolavado.modelos.Empleado;
import com.proyecto.autolavado.modelos.Servicio;
import com.proyecto.autolavado.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    /**
     * Metodo que realiza la consulta de los empleados creados en BD
     * @param model
     * @param mensaje
     * @return
     */
    @GetMapping("/lista")
    public String verEmpleados(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();
        model.addAttribute("titulo", "Lista de Empleados");
        model.addAttribute("emplist", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "/empleado/lista";
    }

    /**
     * Metodo que muestra el formulario para crear un nuevo empleado
     * @param model
     * @param mensaje
     * @return
     */
    @GetMapping("/nuevo")
    public String nuevoEmpleado(Model model, @ModelAttribute("mensaje") String mensaje){
        Empleado emp = new Empleado();
        model.addAttribute("titulo", "Agregar Nuevo Empleado");
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje", mensaje);
        return "/empleado/nuevo";
    }

    /**
     * Metodo que realiza el registro del nuevo empleado en BD
     * @param emp
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/guardar")
    public String crearEmpleado(Empleado emp, RedirectAttributes redirectAttributes) {
        if (empleadoService.saveOrUpdateEmpleado(emp) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/empleado/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/nuevo";
    }

    /**
     * Metodo que obtiene el empleado por su ID para ser editado
     * @param model
     * @param id
     * @param mensaje
     * @return
     */
    @GetMapping("/editar/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empleado emp = empleadoService.getEmpleadoById(id);
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje", mensaje);
        return "/empleado/editar";
    }

    /**
     * Metodo que realiza la edicion del empleado consultado y registra los cambios en BD
     * @param emp
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/editar")
    public String updateEmpleado(@ModelAttribute("emp") Empleado emp, RedirectAttributes redirectAttributes){
        if (empleadoService.saveOrUpdateEmpleado(emp)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/empleado/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/empleado/editar" + emp.getId();
    }

    /**
     * Metodo que realiza la consulta del empleado por ID y muestra su informacion
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/detalle/{id}")
    public String detalleEmpleado(Model model, @PathVariable Integer id) throws Exception {
        Empleado empleado = null;
        if (id != null) {
            empleado = empleadoService.getEmpleadoById(id);
            model.addAttribute("titulo", "Formulario: Detalle Empleado");
        }
        model.addAttribute("empleado", empleado);
        return "/empleado/detalle";
    }

    /**
     * Metodo que realiza la eliminacion del empleado por ID en BD
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/borrar/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empleadoService.deleteEmpleado(id) == true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/empleado/lista";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/empleado/lista";
    }


}
