package com.proyecto.autolavado.service;

import com.proyecto.autolavado.modelos.MovimientoDinero;
import com.proyecto.autolavado.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {

    @Autowired
    MovimientosRepository movimientosRepository;

    // Servicio que retorna todos los movimientos de dinero
    public List<MovimientoDinero> getAllMovimientos(){
        List<MovimientoDinero> financierosList = new ArrayList<>();
        movimientosRepository.findAll().forEach(financiero -> financierosList.add(financiero));
        return financierosList;
    }

    // Servicio que retorna una Movimiento por ID
    public MovimientoDinero getMovimientoById(Integer id){
        return movimientosRepository.findById(id).get();
    }

    // Servicio para guardar o actualizar Objetos de tipo Movimiento
    public boolean saveOrUpdateMovimiento(MovimientoDinero financiero){
        MovimientoDinero fin = movimientosRepository.save(financiero);
        if(movimientosRepository.findById(fin.getId()) != null){
            return true;
        }
        return false;
    }

    // Servicio que me permite eliminar movimientos registradas
    public boolean deleteMovimiento(Integer id) {
        movimientosRepository.deleteById(id);
        if(movimientosRepository.findById(id) != null){
            return true;
        }
        return false;
    }

    // Servicio que obtiene los movimientos realizados por un empleado
    public ArrayList<MovimientoDinero> obtenerPorEmpleado(Integer id) {
        return movimientosRepository.findByEmpleado(id);
    }

    // Servicio para ver la suma de todos los montos
    public Long obtenerSumaMontos(){
        return movimientosRepository.SumarMonto();
    }

    // Servicio para ver la suma de los montos por empleado
    public Long MontosPorEmpleado(Integer id){
        return movimientosRepository.MontosPorEmpleado(id);
    }

    // servicio que nos deja conseguir el id de un empleado si tenemos su correo
    public Integer IdPorCorreo(String Correo){
        return movimientosRepository.IdPorCorreo(Correo);
    }
}
