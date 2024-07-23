package com.proyecto.autolavado.controller;

import com.proyecto.autolavado.modelos.Empleado;
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
     * @return
     */
    @GetMapping("/lista")
    public String verEmpleados(Model model){
        List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();
        model.addAttribute("titulo", "Lista de Empleados");
        model.addAttribute("emplist", listaEmpleados);
        return "/empleado/lista";
    }

    /**
     * Metodo que muestra el formulario para crear un nuevo empleado
     * @param model
     * @return
     */
    @GetMapping("/nuevo")
    public String nuevoEmpleado(Model model){
        Empleado emp = new Empleado();
        model.addAttribute("titulo", "Agregar Nuevo Empleado");
        model.addAttribute("emp", emp);
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
            redirectAttributes.addFlashAttribute("success", "Empleado creado con exito!");
            return "redirect:/empleado/lista";
        }
        redirectAttributes.addFlashAttribute("error", "Error al crear un empleado");
        return "redirect:/nuevo";
    }

    /**
     * Metodo que obtiene el empleado por su ID para ser editado
     * @param model
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/editar/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, RedirectAttributes redirectAttributes){
        Empleado emp = empleadoService.getEmpleadoById(id);
        model.addAttribute("titulo", "Editar Empleado");
        if(emp == null) {
            redirectAttributes.addFlashAttribute("error", "El ID del empleado no existe");
            return "redirect:/empleado/lista";
        }
        model.addAttribute("emp", emp);
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
            redirectAttributes.addFlashAttribute("success", "El empleado ha sido actualizado con exito!");
            return "redirect:/empleado/lista";
        }
        redirectAttributes.addFlashAttribute("error", "Error al actualizar un empleado");
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
            redirectAttributes.addFlashAttribute("success","Empleado eliminado con exito!");
            return "redirect:/empleado/lista";
        }
        redirectAttributes.addFlashAttribute("error", "Error al eliminar un empleado");
        return "redirect:/empleado/lista";
    }


}
