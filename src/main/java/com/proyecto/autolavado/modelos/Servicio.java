package com.proyecto.autolavado.modelos;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String codigo;
    private BigDecimal valor;
    private String valorFormat;

    public Servicio() {
    }

    public Servicio(String nombre, String codigo, BigDecimal valor, String valorFormat) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.valor = valor;
        this.valorFormat = valorFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getValorFormat() {
        return valorFormat;
    }

    public void setValorFormat(String valorFormat) {
        this.valorFormat = valorFormat;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", valor=" + valor +
                ", valorFormat='" + valorFormat + '\'' +
                '}';
    }
}
