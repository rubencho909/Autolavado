package com.proyecto.autolavado.modelos;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Movimientos")
public class MovimientoDinero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long monto;
    private String concepto;
    private String montoFormat;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado usuario;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    public MovimientoDinero() {
    }

    public MovimientoDinero(long monto, String concepto, String montoFormat, Date fecha, Empleado usuario, Servicio servicio) {
        this.monto = monto;
        this.concepto = concepto;
        this.montoFormat = montoFormat;
        this.fecha = fecha;
        this.usuario = usuario;
        this.servicio = servicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getMontoFormat() {
        return montoFormat;
    }

    public void setMontoFormat(String montoFormat) {
        this.montoFormat = montoFormat;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "MovimientoDinero{" +
                "id=" + id +
                ", monto=" + monto +
                ", concepto='" + concepto + '\'' +
                ", montoFormat='" + montoFormat + '\'' +
                ", fecha=" + fecha +
                ", usuario=" + usuario +
                ", servicio=" + servicio +
                '}';
    }
}
