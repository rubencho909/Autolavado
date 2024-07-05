package com.proyecto.autolavado.service;

import com.proyecto.autolavado.modelos.Servicio;
import com.proyecto.autolavado.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    // Metodo que retorna todos los servicios
    public List<Servicio> getAllServicios(){
        List<Servicio> servicioList = new ArrayList<>();
        servicioRepository.findAll().forEach(servicio -> servicioList.add(servicio));
        return servicioList;
    }

    // Metodo que retorna un Servicio por ID
    public Servicio getServicioById(Integer id){
        return servicioRepository.findById(id).get();
    }

    // Metodo para guardar o actualizar Objetos de tipo Servicio
    public boolean saveOrUpdateServicio(Servicio servicio){
        Servicio ser = servicioRepository.save(servicio);
        if (servicioRepository.findById(ser.getId()) != null){
            return true;
        }
        return false;
    }

    //Metodo que me permite eliminar empresas registradas
    public boolean deleteServicio(Integer id) {
        servicioRepository.deleteById(id);
        if(servicioRepository.findById(id) != null){
            return true;
        }
        return false;
    }

}
