package com.proyecto.autolavado.service;

import com.proyecto.autolavado.modelos.Empleado;
import com.proyecto.autolavado.modelos.Servicio;
import com.proyecto.autolavado.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    // Metodo que retorna todos los Empleados
    public List<Empleado> getAllEmpleados(){
        List<Empleado> empleadoList = new ArrayList<>();
        empleadoRepository.findAll().forEach(empleado -> empleadoList.add(empleado));
        return empleadoList;
    }

    // Metodo que retorna un empleado por ID
    public Empleado getEmpleadoById(Integer id){
        return empleadoRepository.findById(id).get();
    }

    // Metodo para guardar o actualizar Objetos de tipo Empleado
    public boolean saveOrUpdateEmpleado(Empleado empleado){
        Empleado emp = empleadoRepository.save(empleado);
        if (empleadoRepository.findById(emp.getId()) != null){
            return true;
        }
        return false;
    }

    //Metodo que me permite eliminar un empleado por su ID
    public boolean deleteEmpleado(Integer id) {
        empleadoRepository.deleteById(id);
        if(empleadoRepository.findById(id) != null){
            return true;
        }
        return false;
    }


}
