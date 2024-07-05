package com.proyecto.autolavado.repository;

import com.proyecto.autolavado.modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que realiza la comunicacion entre la Entidad y la Tabla en BD
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
